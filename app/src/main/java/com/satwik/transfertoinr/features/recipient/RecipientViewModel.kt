package com.satwik.transfertoinr.features.recipient

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satwik.transfertoinr.data.recipient.RecipientRepository
import com.satwik.transfertoinr.data.recipient.add_recipient_preconditions.ValidateAccountNumberUsecase
import com.satwik.transfertoinr.data.recipient.add_recipient_preconditions.ValidateBankUsecase
import com.satwik.transfertoinr.data.recipient.add_recipient_preconditions.ValidateIfscUsecase
import com.satwik.transfertoinr.data.recipient.add_recipient_preconditions.ValidateNameUsecase
import com.satwik.transfertoinr.data.recipient.add_recipient_preconditions.ValidateReEnteredAccountNumberUsecase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RecipientViewModel(
    private val recipientRepository: RecipientRepository,
    private val validateAccountNumberUsecase: ValidateAccountNumberUsecase,
    private val validateIfscCodeUsecase: ValidateIfscUsecase,
    private val validateBankUsecase: ValidateBankUsecase,
    private val validateNameUsecase: ValidateNameUsecase,
    private val validateReEnteredAccountNumberUsecase: ValidateReEnteredAccountNumberUsecase
):ViewModel() {

    private val _recipientsState = MutableStateFlow(RecipientsState())
    val recipientsState: StateFlow<RecipientsState> = _recipientsState

    private val _formState = mutableStateOf(AddRecipientFormState())
    val formState: State<AddRecipientFormState> = _formState

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    init {
        getAllRecipients()
    }

    fun addRecipient(name:String, accountNumber:String, ifscCode:String, bank:String){
        viewModelScope.launch {
            recipientRepository.addRecipient(
                name = name,
                accountNumber = accountNumber,
                ifscCode = ifscCode,
                bank = bank
            )
        }
    }

    fun deleteRecipientById(id: Int) {
        viewModelScope.launch {
            recipientRepository.deleteRecipientById(id)
            _recipientsState.value = _recipientsState.value.copy(
                recipients = _recipientsState.value.recipients.filter { it.id != id }
            )
        }
    }

    private fun getAllRecipients() {
        viewModelScope.launch {
            _recipientsState.value = RecipientsState(isLoading = true)
            try {
                recipientRepository.getAllRecipients().collect { newRecipients ->
                    val currentRecipients = _recipientsState.value.recipients
                    val updatedRecipients = currentRecipients + newRecipients
                    _recipientsState.value = _recipientsState.value.copy(
                        recipients = updatedRecipients,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _recipientsState.value = RecipientsState(error = e.message.toString())
            }
        }
    }



    fun onEvent(event: AddRecipientFormEvent){
        when(event){
            is AddRecipientFormEvent.AccountNumberChanged -> {
                _formState.value = _formState.value.copy(accountNumber = event.accountNumber)
            }
            is AddRecipientFormEvent.ReEnterAccountNumberChanged -> {
                _formState.value = _formState.value.copy(reEnterAccountNumber = event.reEnterAccountNumber)
            }
            is AddRecipientFormEvent.BankChanged -> {
                _formState.value = _formState.value.copy(bank = event.bank)
            }
            is AddRecipientFormEvent.IfscChanged -> {
                _formState.value = _formState.value.copy(ifsc = event.ifsc)
            }
            is AddRecipientFormEvent.NameChanged -> {
                _formState.value = _formState.value.copy(name = event.name)
            }
            AddRecipientFormEvent.Submit -> {
                submitData()
            }
        }
    }

    fun resetFormErrors(){
        _formState.value = _formState.value.copy(
            nameError = null,
            accountNumberError = null,
            reEnterAccountNumberError = null,
            bankError = null,
            ifscError = null,
        )
    }

    private fun submitData() {
        val reEnterAccountNumberResult = validateReEnteredAccountNumberUsecase.execute(_formState.value.reEnterAccountNumber, _formState.value.accountNumber)
        val accountNumberResult = validateAccountNumberUsecase.execute(_formState.value.accountNumber)
        val ifscCodeResult = validateIfscCodeUsecase.execute(_formState.value.ifsc)
        val bankResult = validateBankUsecase.execute(_formState.value.bank)
        val nameResult = validateNameUsecase.execute(_formState.value.name)

        val hasError = listOf(
            reEnterAccountNumberResult,
            accountNumberResult,
            ifscCodeResult,
            bankResult,
            nameResult
        ).any{!it.successfull}

        if(hasError){
            _formState.value = _formState.value.copy(
                reEnterAccountNumberError = reEnterAccountNumberResult.errorMessage,
            )
            _formState.value = _formState.value.copy(
                accountNumberError = accountNumberResult.errorMessage,
            )
            _formState.value = _formState.value.copy(
                ifscError = ifscCodeResult.errorMessage,
            )
            _formState.value = _formState.value.copy(
                bankError = bankResult.errorMessage,
            )
            _formState.value = _formState.value.copy(
                nameError = nameResult.errorMessage
            )
        }
        else{
            viewModelScope.launch {
                validationEventChannel.send(ValidationEvent.Success)
            }
        }
    }
}



sealed class ValidationEvent{
    data object Success:ValidationEvent()
}
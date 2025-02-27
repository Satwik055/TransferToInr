package com.satwik.transfertoinr.features.recipient

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satwik.transfertoinr.data.recipient.RecipientRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipientViewModel(
    private val recipientRepository: RecipientRepository
):ViewModel() {

    private val _recipientsState = MutableStateFlow(RecipientsState())
    val recipientsState: StateFlow<RecipientsState> = _recipientsState

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

    fun getAllRecipients() {
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
}
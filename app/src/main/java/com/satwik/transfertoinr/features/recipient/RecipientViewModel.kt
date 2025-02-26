package com.satwik.transfertoinr.features.recipient

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satwik.transfertoinr.data.recipient.RecipientRepository
import kotlinx.coroutines.launch

class RecipientViewModel(
    private val recipientRepository: RecipientRepository
):ViewModel() {

    private val _recipientsState = mutableStateOf(RecipientsState())
    val recipientsState: State<RecipientsState> = _recipientsState

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

    fun getAllRecipients(){
        viewModelScope.launch {
            _recipientsState.value = RecipientsState(isLoading = true)
            try {
                val recipients = recipientRepository.getAllRecipients()
                _recipientsState.value = RecipientsState(recipients = recipients)

            }
            catch (e:Exception){
                _recipientsState.value = RecipientsState(error = e.message.toString())
            }
        }
    }
}
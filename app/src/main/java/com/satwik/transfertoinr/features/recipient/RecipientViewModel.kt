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

    private fun getAllRecipients(){
        viewModelScope.launch {
            _recipientsState.value = RecipientsState(isLoading = true)
            try {
                val recipients = recipientRepository.getAllRecipients()
                println(recipients)
                _recipientsState.value = RecipientsState(recipients = recipients)

            }
            catch (e:Exception){
                _recipientsState.value = RecipientsState(error = e.message.toString())
            }
        }
    }
}
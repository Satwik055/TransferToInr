package com.ttf.transfertoinr.features.transfer.select_recipient_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ttf.transfertoinr.data.recipient.RecipientRepository
import com.ttf.transfertoinr.features.transfer.model.RecipientsStateTransferScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SelectRecipientViewModel(
    private val recipientRepository: RecipientRepository
    ):ViewModel() {

    private val _recipientsState = MutableStateFlow(RecipientsStateTransferScreen())
    val recipientsState: StateFlow<RecipientsStateTransferScreen> = _recipientsState

    init {
        getAllRecipients()
    }


    private fun getAllRecipients() {
        viewModelScope.launch {
            _recipientsState.value = RecipientsStateTransferScreen(isLoading = true)
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
                _recipientsState.value = RecipientsStateTransferScreen(error = e.message.toString())
            }
        }
    }
}
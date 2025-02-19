package com.satwik.transfertoinr.features.transfer

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satwik.transfertoinr.data.recipient.RecipientRepository
import com.satwik.transfertoinr.data.transfer.TransferRepository
import kotlinx.coroutines.launch

class TransferScreenViewModel(
    private val recipientRepository: RecipientRepository,
    private val transferRepository: TransferRepository
):ViewModel() {


    private val _recipientsState = mutableStateOf(RecipientsStateTransferScreen())
    val recipientsState: State<RecipientsStateTransferScreen> = _recipientsState


    init {
        getAllRecipients()
    }
    private fun getAllRecipients(){
        viewModelScope.launch {
            _recipientsState.value = RecipientsStateTransferScreen(isLoading = true)
            try {
                val recipients = recipientRepository.getAllRecipients()
                println(recipients)
                _recipientsState.value = RecipientsStateTransferScreen(recipients = recipients)

            }
            catch (e:Exception){
                _recipientsState.value = RecipientsStateTransferScreen(error = e.message.toString())
            }
        }
    }


    fun addTransaction(transactionCode:String, sent:Int, recieve:Int, currency:String, email:String){
        viewModelScope.launch {
            transferRepository.createTransfer(
                transactionCode = transactionCode,
                sent = sent,
                recieve = recieve,
                currency = currency,
            )
        }
    }
}
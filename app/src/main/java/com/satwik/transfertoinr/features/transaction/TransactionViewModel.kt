package com.satwik.transfertoinr.features.transaction

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satwik.transfertoinr.data.transaction.TransactionRepository
import com.satwik.transfertoinr.features.recipient.RecipientsState
import kotlinx.coroutines.launch

class TransactionViewModel(private val transactionRepository: TransactionRepository):ViewModel() {


    private val _transactionState = mutableStateOf(TransactionState())
    val transactionState: State<TransactionState> = _transactionState

    fun getAllTransaction(){
        viewModelScope.launch {

            _transactionState.value = TransactionState(isLoading = true)

            try{
                val transactions= transactionRepository.getAllTransaction()
                _transactionState.value = TransactionState(transaction = transactions)
            }
            catch (e:Exception){
                _transactionState.value = TransactionState(error = e.message.toString())
            }
        }
    }
}
package com.satwik.transfertoinr.features.transaction

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satwik.transfertoinr.core.model.CurrencyType
import com.satwik.transfertoinr.data.account.AccountRepository
import com.satwik.transfertoinr.data.transaction.TransactionRepository
import kotlinx.coroutines.launch

class TransactionViewModel(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository
):ViewModel() {


    private val _transactionState = mutableStateOf(TransactionState())
    val transactionState: State<TransactionState> = _transactionState

    private val _preferredCurrency = mutableStateOf(CurrencyType.EUR)
    val prefferedCurrency: State<CurrencyType> = _preferredCurrency

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

    fun getPreferredCurrency(){
        viewModelScope.launch {
            val currency = accountRepository.getUserInfo().preferred_currency
            _preferredCurrency.value  = currency
        }
    }
}
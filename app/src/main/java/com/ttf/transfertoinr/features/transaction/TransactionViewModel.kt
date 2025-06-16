package com.ttf.transfertoinr.features.transaction

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ttf.transfertoinr.core.model.CurrencyType
import com.ttf.transfertoinr.data.account.AccountRepository
import com.ttf.transfertoinr.data.transaction.TransactionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TransactionViewModel(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository
):ViewModel() {


    private val _transactionState = MutableStateFlow(TransactionState())
    val transactionState: StateFlow<TransactionState> = _transactionState

    private val _preferredCurrency = mutableStateOf(CurrencyType.EUR)
    val prefferedCurrency: State<CurrencyType> = _preferredCurrency

    init {
        getAllTransaction()
    }

    private fun getAllTransaction() {
        viewModelScope.launch {
            _transactionState.value = TransactionState(isLoading = true)
            try {
                transactionRepository.getAllTransaction().collect { newTransactions ->
                    val currentTransactions = _transactionState.value.transaction
                    val transactionMap = currentTransactions.associateBy { it.id }.toMutableMap()
                    newTransactions.forEach { newTransaction ->
                        transactionMap[newTransaction.id] = newTransaction
                    }
                    val updatedTransactions = transactionMap.values.toList()
                    _transactionState.value = _transactionState.value.copy(
                        transaction = updatedTransactions,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _transactionState.value = TransactionState(error = e.message.toString())
            }
        }
    }

    fun getPreferredCurrency(){
        viewModelScope.launch {
            val profileFlow = accountRepository.getProfile()
            profileFlow.collectLatest { profile->
                _preferredCurrency.value  = profile.preferred_currency
            }
        }
    }
}
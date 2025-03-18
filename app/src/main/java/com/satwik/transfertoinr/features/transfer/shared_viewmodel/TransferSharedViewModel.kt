package com.satwik.transfertoinr.features.transfer.shared_viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satwik.transfertoinr.core.model.Recipient
import com.satwik.transfertoinr.core.utils.generateTransactionCode
import com.satwik.transfertoinr.data.account.AccountRepository
import com.satwik.transfertoinr.data.exchange_rate.ExchangeRateRepository
import com.satwik.transfertoinr.data.transfer.TransferRepository
import com.satwik.transfertoinr.features.home.UserInfoStateHome
import com.satwik.transfertoinr.features.transfer.model.TransactionSummary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TransferSharedViewModel(
    private val transferRepository: TransferRepository,
    private val accountRepository: AccountRepository,
    private val exchangeRatesRepository: ExchangeRateRepository
):ViewModel() {

    private val _transactionSummary = MutableStateFlow(TransactionSummary())
    val transactionSummary: StateFlow<TransactionSummary> = _transactionSummary

    private val _transactionCode = mutableStateOf("")
    val transactionCode:State<String> = _transactionCode

    private val _userInfoState = MutableStateFlow(UserInfoStateHome())
    val userInfoState: StateFlow<UserInfoStateHome> = _userInfoState


    init {
        getUserInfo()
        initTransactionSummary()
    }

    private fun initTransactionSummary(){
        setCurrency()
        setTransactionCode()
        setRateFee()
    }

    private fun setTransactionCode(){
        _transactionCode.value = generateTransactionCode()
    }

    fun createTransfer(){
        viewModelScope.launch {
            transferRepository.createTransfer(
                transactionCode = transactionCode.value,
                sent = transactionSummary.value.send,
                currency = transactionSummary.value.currency,
                reason = transactionSummary.value.reason,
                recipientId = transactionSummary.value.recipient.id
            )
        }
    }

    fun setSendAmount(send:Int){
        _transactionSummary.value = _transactionSummary.value.copy(send = send)
    }

    fun setReceiveAmount(send:Int){
        _transactionSummary.value = _transactionSummary.value.copy(receive = send)
    }

    fun setReason(reason:String){
        _transactionSummary.value = _transactionSummary.value.copy(reason = reason)
    }

    fun setRecipient(recipient: Recipient){
        _transactionSummary.value = _transactionSummary.value.copy(recipient = recipient)
    }

    private fun setRateFee(){
        viewModelScope.launch {
            val profileFlow = accountRepository.getProfile()
            profileFlow.collectLatest { profile ->
                exchangeRatesRepository.getExchangeRates(profile.preferred_currency).collectLatest{rate->
                    _transactionSummary.value = _transactionSummary.value.copy(exchangeRate = rate.tti)
                    _transactionSummary.value = _transactionSummary.value.copy(fee = rate.tti_fees)
                }
            }
        }
    }

    private fun getUserInfo(){
        viewModelScope.launch {
            _userInfoState.value = UserInfoStateHome(isLoading = true)
            try {
                val profileFlow = accountRepository.getProfile()
                profileFlow.collectLatest { profile->
                    _userInfoState.value = UserInfoStateHome(profile = profile)
                }
            }
            catch (e:Exception){
                _userInfoState.value = UserInfoStateHome(error = e.message.toString())
            }
        }
    }

    private fun setCurrency(){
        viewModelScope.launch {
            val profileFlow = accountRepository.getProfile()
            profileFlow.collectLatest { profile ->
                _transactionSummary.value = _transactionSummary.value.copy(currency =profile.preferred_currency)
            }
        }
    }
}
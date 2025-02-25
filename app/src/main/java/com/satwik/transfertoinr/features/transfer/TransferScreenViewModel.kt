package com.satwik.transfertoinr.features.transfer

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satwik.transfertoinr.core.model.CurrencyType
import com.satwik.transfertoinr.data.account.AccountRepository
import com.satwik.transfertoinr.data.exchange_rate.ExchangeRateRepository
import com.satwik.transfertoinr.data.recipient.RecipientRepository
import com.satwik.transfertoinr.data.transfer.TransferRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TransferScreenViewModel(
    private val recipientRepository: RecipientRepository,
    private val transferRepository: TransferRepository,
    private val accountRepository: AccountRepository,
    private val exchangeRatesRepository: ExchangeRateRepository
):ViewModel() {

    private val _recipientsState = mutableStateOf(RecipientsStateTransferScreen())
    val recipientsState: State<RecipientsStateTransferScreen> = _recipientsState

    private val _ttiRate = MutableStateFlow(0.0)
    val ttiRate = _ttiRate.asStateFlow()

    private val _kycStatus = MutableStateFlow(false)
    val kycStatus = _kycStatus.asStateFlow()


    private val _preferredCurrency = MutableStateFlow(CurrencyType.EUR)
    val prefferedCurrency = _preferredCurrency.asStateFlow()


    fun getAllRecipients(){
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

    fun addTransaction(transactionCode:String, sent:Int, reason:String){
        viewModelScope.launch {
            val prefferedCurrency= accountRepository.getUserInfo().preferred_currency
            transferRepository.createTransfer(
                transactionCode = transactionCode,
                sent = sent,
                currency = prefferedCurrency,
                reason = reason
            )
        }
    }

    fun getTtiRate(){
        viewModelScope.launch {
            val prefferedCurrency = accountRepository.getUserInfo().preferred_currency
            val ttirate = exchangeRatesRepository.getExchangeRates(prefferedCurrency).tti
            _ttiRate.value = ttirate
        }
    }

    fun getPreferredCurrency() {
        viewModelScope.launch {
            val currency = accountRepository.getUserInfo().preferred_currency
            _preferredCurrency.value = currency
        }
    }

    fun getKycStatus(){
        viewModelScope.launch {
            val status = accountRepository.getUserInfo().kyc_status
            _kycStatus.value = status
        }
    }
}
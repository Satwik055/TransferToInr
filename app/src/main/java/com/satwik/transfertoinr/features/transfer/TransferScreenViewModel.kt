package com.satwik.transfertoinr.features.transfer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satwik.transfertoinr.core.model.CurrencyType
import com.satwik.transfertoinr.data.account.AccountRepository
import com.satwik.transfertoinr.data.exchange_rate.ExchangeRateRepository
import com.satwik.transfertoinr.data.recipient.RecipientRepository
import com.satwik.transfertoinr.data.transfer.TransferRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TransferScreenViewModel(
    private val recipientRepository: RecipientRepository,
    private val transferRepository: TransferRepository,
    private val accountRepository: AccountRepository,
    private val exchangeRatesRepository: ExchangeRateRepository
):ViewModel() {

    private val _recipientsState = MutableStateFlow(RecipientsStateTransferScreen())
    val recipientsState: StateFlow<RecipientsStateTransferScreen> = _recipientsState

    private val _ttiRate = MutableStateFlow(0.0)
    val ttiRate = _ttiRate.asStateFlow()

    private val _kycStatus = MutableStateFlow(false)
    val kycStatus = _kycStatus.asStateFlow()

    private val _preferredCurrency = MutableStateFlow(CurrencyType.EUR)
    val prefferedCurrency = _preferredCurrency.asStateFlow()


    init {
        getTtiRate()
        getAllRecipients()
        getPreferredCurrency()
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

    fun addTransaction(transactionCode:String, sent:Int, reason:String){
        viewModelScope.launch {
            val profileFlow = accountRepository.getProfile()
            profileFlow.collectLatest { profile->
                transferRepository.createTransfer(
                    transactionCode = transactionCode,
                    sent = sent,
                    currency = profile.preferred_currency,
                    reason = reason
                )
            }

        }
    }

    private fun getTtiRate(){
        viewModelScope.launch {
            val profileFlow = accountRepository.getProfile()
            profileFlow.collectLatest { profile->
                exchangeRatesRepository.getExchangeRates(profile.preferred_currency).collect{
                    _ttiRate.value = it.tti
                }
            }
        }
    }

    private fun getPreferredCurrency() {
        viewModelScope.launch {
            val profileFlow = accountRepository.getProfile()
            profileFlow.collectLatest { profile->
                _preferredCurrency.value = profile.preferred_currency
            }
        }
    }

    fun getKycStatus(){
        viewModelScope.launch {
            val profileFlow = accountRepository.getProfile()
            profileFlow.collectLatest { profile->
                _kycStatus.value = profile.kyc_status
            }
        }
    }
}
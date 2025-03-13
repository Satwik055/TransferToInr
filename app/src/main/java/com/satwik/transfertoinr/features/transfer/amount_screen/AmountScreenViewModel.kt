package com.satwik.transfertoinr.features.transfer.amount_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satwik.transfertoinr.data.account.AccountRepository
import com.satwik.transfertoinr.data.exchange_rate.ExchangeRateRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AmountScreenViewModel(
    private val accountRepository: AccountRepository,
    private val exchangeRatesRepository: ExchangeRateRepository
): ViewModel() {

    private val _ttiRate = MutableStateFlow(0.0)
    val ttiRate = _ttiRate.asStateFlow()

    private val _kycStatus = MutableStateFlow(false)
    val kycStatus = _kycStatus.asStateFlow()


    init {
        getTtiRate()
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

    fun getKycStatus(){
        viewModelScope.launch {
            val profileFlow = accountRepository.getProfile()
            profileFlow.collectLatest { profile->
                _kycStatus.value = profile.kyc_status
            }
        }
    }


}
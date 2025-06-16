package com.ttf.transfertoinr.features.transfer.amount_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ttf.transfertoinr.core.model.Result
import com.ttf.transfertoinr.data.account.AccountRepository
import com.ttf.transfertoinr.data.exchange_rate.ExchangeRateRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AmountScreenViewModel(
    private val accountRepository: AccountRepository,
    private val exchangeRatesRepository: ExchangeRateRepository
): ViewModel() {

    private val _ttiRateResult = MutableStateFlow(Result())
    val ttiRateResult: StateFlow<Result> = _ttiRateResult

    private val _kycStatus = MutableStateFlow(false)
    val kycStatus = _kycStatus.asStateFlow()

    init {
        getTtiRate()
        getKycStatus()
    }

    private fun getTtiRate(){
        viewModelScope.launch {
            _ttiRateResult.value = Result(isLoading = true)
            try {
                val profileFlow = accountRepository.getProfile()
                profileFlow.collectLatest { profile->
                    exchangeRatesRepository.getExchangeRates(profile.preferred_currency).collect{
                        _ttiRateResult.value = Result(successResult = it.tti)
                    }
                }
            }
            catch (e:Exception){
                _ttiRateResult.value = Result(error = e.message.toString())
            }
        }
    }

    private fun getKycStatus(){
        viewModelScope.launch {
            val profileFlow = accountRepository.getProfile()
            profileFlow.collectLatest { profile->
                _kycStatus.value = profile.kyc_status
            }
        }
    }


}
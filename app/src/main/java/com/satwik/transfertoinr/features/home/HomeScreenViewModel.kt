package com.satwik.transfertoinr.features.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satwik.transfertoinr.data.account.AccountRepository
import com.satwik.transfertoinr.data.exchange_rate.ExchangeRateRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val accountRepository: AccountRepository,
    private val exchangeRateRepository: ExchangeRateRepository
):ViewModel() {

    private val _userInfoState = MutableStateFlow(UserInfoStateHome())
    val userInfoState: StateFlow<UserInfoStateHome> = _userInfoState

    private val _exchangeRateState = MutableStateFlow(ExchangeRateState())
    val exchangeRateState: StateFlow<ExchangeRateState> = _exchangeRateState

    init {
        getUserInfo()
        getExchangeRates()
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

    private fun getExchangeRates(){
        viewModelScope.launch {
            _exchangeRateState.value = ExchangeRateState(isLoading = true)
            try{
                val profileFlow = accountRepository.getProfile()
                profileFlow.collectLatest { profile ->
                    exchangeRateRepository.getExchangeRates(profile.preferred_currency).collectLatest{rate->
                        _exchangeRateState.value = ExchangeRateState(rate = rate)
                    }
                }
            }
            catch (e:Exception){
                _exchangeRateState.value = ExchangeRateState(error = e.message.toString())
            }
        }
    }
}
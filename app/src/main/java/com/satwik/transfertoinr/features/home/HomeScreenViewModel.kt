package com.satwik.transfertoinr.features.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satwik.transfertoinr.data.account.AccountRepository
import com.satwik.transfertoinr.data.exchange_rate.ExchangeRateRepository
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val accountRepository: AccountRepository,
    private val exchangeRateRepository: ExchangeRateRepository
):ViewModel() {

    private val _userInfoState = mutableStateOf(UserInfoStateHome())
    val userInfoState: State<UserInfoStateHome> = _userInfoState

    private val _exchangeRateState = mutableStateOf(ExchangeRateState())
    val exchangeRateState: State<ExchangeRateState> = _exchangeRateState

    init {
        getUserInfo()
    }

    private fun getUserInfo(){
        viewModelScope.launch {
            _userInfoState.value = UserInfoStateHome(isLoading = true)
            try {
                val userInfo  = accountRepository.getUserInfo()
                _userInfoState.value = UserInfoStateHome(userInfo = userInfo)

            }
            catch (e:Exception){
                _userInfoState.value = UserInfoStateHome(error = e.message.toString())
            }
        }
    }

    fun getExchangeRates(){
        viewModelScope.launch {
            _exchangeRateState.value = ExchangeRateState(isLoading = true)
            try{
                val prefferedCurrency = accountRepository.getUserInfo().preffered_currency
                val rate = exchangeRateRepository.getExchangeRates(prefferedCurrency)
                _exchangeRateState.value = ExchangeRateState(rate = rate)
            }
            catch (e:Exception){
                _exchangeRateState.value = ExchangeRateState(error = e.message.toString())
            }
        }
    }
}
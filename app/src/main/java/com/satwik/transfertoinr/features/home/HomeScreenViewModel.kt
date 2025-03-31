package com.satwik.transfertoinr.features.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.satwik.transfertoinr.core.model.Result
import com.satwik.transfertoinr.data.account.AccountRepository
import com.satwik.transfertoinr.data.exchange_rate.ExchangeRateRepository
import com.satwik.transfertoinr.features.transfer.shared_viewmodel.TransferSharedViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.compose.navigation.koinNavViewModel

class HomeScreenViewModel(
    private val accountRepository: AccountRepository,
    private val exchangeRateRepository: ExchangeRateRepository
):ViewModel() {


    private val _userInfoState = MutableStateFlow(UserInfoStateHome())
    val userInfoState: StateFlow<UserInfoStateHome> = _userInfoState

    private val _carousellImageLinkResult = MutableStateFlow(Result())
    val carousellImageLinkResult: StateFlow<Result> = _carousellImageLinkResult


    private val _exchangeRateState = MutableStateFlow(ExchangeRateState())
    val exchangeRateState: StateFlow<ExchangeRateState> = _exchangeRateState

    init {
        getUserInfo()
        getCarousellImageLinks()
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

    private fun getCarousellImageLinks(){
        viewModelScope.launch {
            try {
                _carousellImageLinkResult.value = Result(isLoading = true)
                val urls = accountRepository.getCarousellImageLinks()
                _carousellImageLinkResult.value = Result(successResult = urls)
            }
            catch (e:Exception){
                _carousellImageLinkResult.value = Result(error = e.message.toString())
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
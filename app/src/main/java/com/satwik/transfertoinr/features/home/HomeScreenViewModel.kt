package com.satwik.transfertoinr.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satwik.transfertoinr.core.model.Result
import com.satwik.transfertoinr.data.account.AccountRepository
import com.satwik.transfertoinr.data.exchange_rate.ExchangeRateRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val accountRepository: AccountRepository,
    private val exchangeRateRepository: ExchangeRateRepository
):ViewModel() {

    private val _profileState = MutableStateFlow(Result())
    val profileState: StateFlow<Result> = _profileState

    private val _carousellImageLinkResult = MutableStateFlow(Result())
    val carousellImageLinkResult: StateFlow<Result> = _carousellImageLinkResult

    private val _exchangeRateState = MutableStateFlow(Result())
    val exchangeRateState: StateFlow<Result> = _exchangeRateState

    init {
        fetchProfile()
        fetchCarouselImageLinks()
        fetchExchangeRates()
    }

    private fun fetchProfile(){
        viewModelScope.launch {
            _profileState.value = Result(isLoading = true)
            try {
                val profileFlow = accountRepository.getProfile()
                profileFlow.collectLatest { profile->
                    _profileState.value = Result(success = true)
                    _profileState.value = _profileState.value.copy(successResult = profile)
                }
            }
            catch (e:Exception){
                _profileState.value = Result(error = e.message.toString())
            }
        }
    }

    private fun fetchCarouselImageLinks(){
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

    private fun fetchExchangeRates(){
        viewModelScope.launch {
            _exchangeRateState.value = Result(isLoading = true)
            try{
                val profileFlow = accountRepository.getProfile()
                profileFlow.collectLatest { profile ->
                    exchangeRateRepository.getExchangeRates(profile.preferred_currency).collectLatest{rate->
                        _exchangeRateState.value = Result(success = true)
                        _exchangeRateState.value = _exchangeRateState.value.copy(successResult = rate)
                    }
                }
            }
            catch (e:Exception){
                _exchangeRateState.value = Result(error = e.message.toString())
            }
        }
    }
}
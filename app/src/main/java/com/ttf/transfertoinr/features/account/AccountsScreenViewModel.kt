package com.ttf.transfertoinr.features.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ttf.transfertoinr.core.model.CurrencyType
import com.ttf.transfertoinr.data.account.AccountRepository
import com.ttf.transfertoinr.data.auth.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AccountsScreenViewModel(
    private val authRepository: AuthRepository,
    private val accountRepository: AccountRepository
):ViewModel() {
    private val _userInfoState = MutableStateFlow(UserInfoState())
    val userInfoState: StateFlow<UserInfoState> = _userInfoState

    init {
        fetchProfile()
    }

    fun logout(){
        viewModelScope.launch {
            authRepository.logout()
        }
    }

    private fun fetchProfile(){
        viewModelScope.launch {
            _userInfoState.value = UserInfoState(isLoading = true)
            try {
                val profileFlow = accountRepository.getProfile()
                profileFlow.collectLatest { profile->
                    _userInfoState.value = UserInfoState(profile = profile)
                }
            }
            catch (e:Exception){
                _userInfoState.value = UserInfoState(error = e.message.toString())
            }
        }
    }

    fun updatePreferredCurrency(currency: CurrencyType){
        viewModelScope.launch{
            val profileFlow = accountRepository.getProfile()
            val profile = profileFlow.first()
            accountRepository.updatePrefferedCurrency(profile.email, currency)
        }
    }
}


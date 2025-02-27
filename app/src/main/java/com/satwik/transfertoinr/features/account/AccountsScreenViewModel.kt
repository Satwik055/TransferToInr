package com.satwik.transfertoinr.features.account

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satwik.transfertoinr.core.model.CurrencyType
import com.satwik.transfertoinr.data.account.AccountRepository
import com.satwik.transfertoinr.data.auth.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AccountsScreenViewModel(
    private val authRepository: AuthRepository,
    private val accountRepository: AccountRepository
):ViewModel() {
    private val _userInfoState = MutableStateFlow(UserInfoState())
    val userInfoState: StateFlow<UserInfoState> = _userInfoState


    init {
        getUserInfo()
    }

    fun logout(){
        viewModelScope.launch {
            println("Logout clicked")
            authRepository.logout()
        }
    }

    fun getUserInfo(){
        viewModelScope.launch {
            _userInfoState.value = UserInfoState(isLoading = true)
            try {
                accountRepository.getProfile().collect{
                    _userInfoState.value = UserInfoState(profile = it)
                }
            }
            catch (e:Exception){
                _userInfoState.value = UserInfoState(error = e.message.toString())
            }
        }
    }

    fun updatePreferredCurrency(currency: CurrencyType){
        viewModelScope.launch {
            accountRepository.getProfile().collect{
                accountRepository.updatePrefferedCurrency(it.email, currency)
            }
        }
    }

}


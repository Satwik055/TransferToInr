package com.satwik.transfertoinr.features.account

import android.view.View
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satwik.transfertoinr.core.model.CurrencyType
import com.satwik.transfertoinr.data.account.AccountRepository
import com.satwik.transfertoinr.data.auth.AuthRepository
import com.satwik.transfertoinr.features.transfer.RecipientsStateTransferScreen
import com.sumsub.sns.core.theme.SNSTheme
import com.sumsub.sns.core.theme.SNSThemeColor
import com.sumsub.sns.core.theme.colors
import kotlinx.coroutines.launch

class AccountsScreenViewModel(
    private val authRepository: AuthRepository,
    private val accountRepository: AccountRepository
):ViewModel() {
    private val _userInfoState = mutableStateOf(UserInfoState())
    val userInfoState: State<UserInfoState> = _userInfoState

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
                val userInfo  = accountRepository.getUserInfo()
                _userInfoState.value = UserInfoState(userInfo = userInfo)

            }
            catch (e:Exception){
                _userInfoState.value = UserInfoState(error = e.message.toString())
            }
        }
    }

    fun updatePreferredCurrency(currency: CurrencyType){
        viewModelScope.launch {
            val email = accountRepository.getUserInfo().email
            accountRepository.updatePrefferedCurrency(email, currency)
        }
    }


//    private val customTheme = SNSTheme{
//        colors.alertTint = SNSThemeColor(Color())
//    }
}


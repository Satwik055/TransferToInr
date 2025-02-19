package com.satwik.transfertoinr.features.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satwik.transfertoinr.core.model.UserInfo
import com.satwik.transfertoinr.data.account.AccountRepository
import com.satwik.transfertoinr.features.account.UserInfoState
import kotlinx.coroutines.launch
import kotlin.reflect.jvm.internal.impl.utils.DFS.VisitedWithSet

class HomeScreenViewModel(private val accountRepository: AccountRepository):ViewModel() {

    private val _userInfoState = mutableStateOf(UserInfoStateHome())
    val userInfoState: State<UserInfoStateHome> = _userInfoState

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
}
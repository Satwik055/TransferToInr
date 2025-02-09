package com.satwik.transfertoinr.features.auth.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satwik.transfertoinr.data.auth.AuthRepository
import com.satwik.transfertoinr.data.auth.AuthRepositoryImpl
import kotlinx.coroutines.launch

class LoginScreenViewModel(private val authRepository: AuthRepository):ViewModel() {

    private val _loginScreenState = mutableStateOf(LoginScreenState())
    val loginScreenState: State<LoginScreenState> = _loginScreenState

    fun login(email:String, password:String){
        viewModelScope.launch {

            _loginScreenState.value = LoginScreenState(isLoading = true)

            try{
                authRepository.login(email, password)
                _loginScreenState.value = LoginScreenState(success = true)

            }
            catch (e:Exception){
                _loginScreenState.value = LoginScreenState(error = e.message.toString())

            }

        }
    }


}
package com.satwik.transfertoinr.features.auth.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satwik.transfertoinr.data.auth.AuthRepository
import com.satwik.transfertoinr.data.auth.AuthRepositoryImpl
import kotlinx.coroutines.launch

class SignupScreenViewModel(
    private val authRepository: AuthRepository
):ViewModel() {

    private val _signupScreenState = mutableStateOf(SignupScreenState())
    val signupScreenState: State<SignupScreenState> = _signupScreenState

    fun signup(email:String, password:String){
        viewModelScope.launch {

            _signupScreenState.value = SignupScreenState(isLoading = true)

            try{
                authRepository.signup(email, password)
                _signupScreenState.value = SignupScreenState(success = true)

            }
            catch (e:Exception){
                _signupScreenState.value = SignupScreenState(error = e.message.toString())
            }
        }
    }
}
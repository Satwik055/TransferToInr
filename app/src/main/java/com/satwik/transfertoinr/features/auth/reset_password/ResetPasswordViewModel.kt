package com.satwik.transfertoinr.features.auth.reset_password

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.util.trace
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satwik.transfertoinr.core.model.Result
import com.satwik.transfertoinr.data.auth.AuthRepository
import com.satwik.transfertoinr.features.auth.verify_email.VerificationResultState
import kotlinx.coroutines.launch

class ResetPasswordViewModel(
    private val authRepository: AuthRepository
):ViewModel() {

    private val _sendPasswordResetEmailResult = mutableStateOf(Result())
    val sendPasswordResetEmailResult: State<Result> = _sendPasswordResetEmailResult


    private val _changePasswordResult = mutableStateOf(Result())
    val changePasswordResult: State<Result> = _changePasswordResult

    fun sendPasswordResetEmail(email:String){
        viewModelScope.launch {
            _sendPasswordResetEmailResult.value = Result(isLoading = true)
            try {
                authRepository.sendPasswordResetEmail(email)
                _sendPasswordResetEmailResult.value = Result(success = true)

            }
            catch (e:Exception){
                _sendPasswordResetEmailResult.value = Result(error = e.message.toString())
            }
        }
    }

    fun changePassword(newPassword:String){
        viewModelScope.launch {
            try {
                _changePasswordResult.value = Result(isLoading = true)

                authRepository.changePassword(newPassword)
                _changePasswordResult.value = Result(success = true)
            }
            catch (e:Exception){
                _changePasswordResult.value = Result(error = e.message.toString())
            }

        }
    }
}
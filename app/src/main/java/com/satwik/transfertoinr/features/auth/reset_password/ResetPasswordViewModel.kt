package com.satwik.transfertoinr.features.auth.reset_password

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.util.trace
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satwik.transfertoinr.core.model.Result
import com.satwik.transfertoinr.data.auth.AuthRepository
import com.satwik.transfertoinr.features.auth.verify_email.VerificationResultState
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.exception.AuthRestException
import kotlinx.coroutines.launch

class ResetPasswordViewModel(
    private val authRepository: AuthRepository
):ViewModel() {

    private val _sendPasswordResetEmailResult = mutableStateOf(Result())
    val sendPasswordResetEmailResult: State<Result> = _sendPasswordResetEmailResult

    private val _verificationResult = mutableStateOf(VerificationResultState())
    val verificationResult: State<VerificationResultState> = _verificationResult

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

    fun sendPasswordResetEmailResultStateReset(){
        _sendPasswordResetEmailResult.value = Result()
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

    fun verifyResetOtp(otp:String, email:String){
        viewModelScope.launch {
            _verificationResult.value = VerificationResultState(isLoading = true)
            try {
                authRepository.verifyRegistrationOtp(otp, email)
                _verificationResult.value = VerificationResultState(success = true)
            }
            catch (e: AuthRestException){
                print(e)
                _verificationResult.value = VerificationResultState(error = e.errorDescription ?: "")
            }
            catch (e:Exception){
                print(e)
                Log.e("Verify OTP Error", e.stackTraceToString())
            }
        }
    }

    fun resendEmailOtp(email: String){
        viewModelScope.launch {
            authRepository.resendEmailOtp(email, OtpType.Email.SIGNUP)
        }
    }
}
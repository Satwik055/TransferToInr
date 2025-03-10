package com.satwik.transfertoinr.features.auth.verify_email

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satwik.transfertoinr.data.auth.AuthRepository
import com.satwik.transfertoinr.features.auth.signup.SignupScreenState
import io.github.jan.supabase.auth.exception.AuthRestException
import kotlinx.coroutines.launch

class EmailVerificationViewModel(private val authRepository: AuthRepository):ViewModel() {

    private val _verificationResult = mutableStateOf(VerificationResultState())
    val verificationResult: State<VerificationResultState> = _verificationResult

    fun verifyOtp(otp:String, email:String){
        viewModelScope.launch {
            _verificationResult.value = VerificationResultState(isLoading = true)
            try {
                authRepository.verifyOtp(otp, email)
                _verificationResult.value = VerificationResultState(success = true)
            }
            catch (e:AuthRestException){
                _verificationResult.value = VerificationResultState(error = e.errorDescription ?: "")
            }
            catch (e:Exception){
                Log.e("Verify OTP Error", e.stackTraceToString())
            }
        }
    }

    fun resendEmailOtp(email: String){
        viewModelScope.launch {
            authRepository.resendEmailOtp(email)
        }
    }
}
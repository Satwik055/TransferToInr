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

    fun verifyOtp(otp:String, email:String, onSuccess:()->Unit){
        viewModelScope.launch {
            _verificationResult.value = VerificationResultState(isLoading = true)
            try {
                authRepository.verifyOtp(otp, email)
                _verificationResult.value = VerificationResultState(success = true)
                onSuccess.invoke()
            }
            catch (e:AuthRestException){
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
            authRepository.resendEmailOtp(email)
        }
    }
}
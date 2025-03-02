package com.satwik.transfertoinr.data.auth.signup_preconditions

import android.util.Patterns
import com.satwik.transfertoinr.core.model.ValidationResult

class SignupValidateEmailUsecase{
    fun execute(email:String): ValidationResult {
        if(email.isBlank()){
            return ValidationResult(false, errorMessage = "Please enter an email")
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return ValidationResult(false, errorMessage = "Please enter valid email")
        }
        return ValidationResult(true)
    }
}
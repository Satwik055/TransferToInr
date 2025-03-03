package com.satwik.transfertoinr.data.auth.signup_preconditions

import com.satwik.transfertoinr.core.model.ValidationResult

class SignupValidateReEnterPasswordUsecase{
    fun execute(reEnteredPassword:String, previousPassword:String): ValidationResult {
        if(reEnteredPassword.isBlank()){
            return ValidationResult(false, errorMessage = "Please Re-Enter the password")
        }
        if(reEnteredPassword!=previousPassword){
            return ValidationResult(false, errorMessage = "Password does not match with previous password")
        }
        return ValidationResult(true)
    }
}
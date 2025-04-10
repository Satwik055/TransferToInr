package com.satwik.transfertoinr.data.auth.login_preconditions

import com.satwik.transfertoinr.core.model.ValidationResult

class LoginValidatePasswordUsecase{
    fun execute(password:String): ValidationResult {
        if(password.isBlank()){
            return ValidationResult(false, errorMessage = "Please enter a password")
        }
        return ValidationResult(true)
    }
}
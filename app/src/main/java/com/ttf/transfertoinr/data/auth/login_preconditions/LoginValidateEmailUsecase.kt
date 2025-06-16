package com.ttf.transfertoinr.data.auth.login_preconditions

import com.ttf.transfertoinr.core.model.ValidationResult

class LoginValidateEmailUsecase{

    fun execute(email:String): ValidationResult {
        if(email.isBlank()){
            return ValidationResult(false, errorMessage = "Please enter an email")
        }
        return ValidationResult(true)
    }
}
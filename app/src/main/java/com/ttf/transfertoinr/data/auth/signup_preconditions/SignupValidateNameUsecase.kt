package com.ttf.transfertoinr.data.auth.signup_preconditions

import com.ttf.transfertoinr.core.model.ValidationResult

class SignupValidateNameUsecase{
    fun execute(name:String): ValidationResult {
        if(name.isBlank()){
            return ValidationResult(false, errorMessage = "Please enter your name")
        }
        if(name.length>30){
            return ValidationResult(false, errorMessage = "name must be less than 30 characters")
        }
        return ValidationResult(true)
    }
}
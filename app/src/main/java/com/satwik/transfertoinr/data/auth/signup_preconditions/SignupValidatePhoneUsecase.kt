package com.satwik.transfertoinr.data.auth.signup_preconditions

import com.satwik.transfertoinr.core.model.ValidationResult

class SignupValidatePhoneUsecase{
    fun execute(phone:String): ValidationResult {
        if(phone.isBlank()){
            return ValidationResult(false, errorMessage = "Please enter your phone number")
        }
        if(phone.length>20){
            return ValidationResult(false, errorMessage = "phone must be less than 20 characters")
        }
        return ValidationResult(true)
    }
}
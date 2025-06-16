package com.satwik.transfertoinr.data.recipient.add_recipient_preconditions

import com.satwik.transfertoinr.core.model.ValidationResult

class ValidateIfscUsecase{
    fun execute(ifsc:String): ValidationResult {
        if(ifsc.isBlank()){
            return ValidationResult(false, errorMessage = "Please enter the ifsc code")
        }
        return ValidationResult(true)
    }
}
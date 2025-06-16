package com.ttf.transfertoinr.data.recipient.add_recipient_preconditions

import com.ttf.transfertoinr.core.model.ValidationResult

class ValidateNameUsecase {
    fun execute(name:String): ValidationResult {
        if(name.isBlank()){
            return ValidationResult(false, errorMessage = "Please enter the name")
        }
        return ValidationResult(true)
    }
}

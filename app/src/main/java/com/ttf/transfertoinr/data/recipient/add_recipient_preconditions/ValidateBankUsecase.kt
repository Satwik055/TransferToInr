package com.ttf.transfertoinr.data.recipient.add_recipient_preconditions

import com.ttf.transfertoinr.core.model.ValidationResult

class ValidateBankUsecase{
    fun execute(bank:String): ValidationResult {
        if(bank.isBlank()){
            return ValidationResult(false, errorMessage = "Please enter the bank")
        }
        return ValidationResult(true)
    }
}
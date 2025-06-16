package com.satwik.transfertoinr.data.recipient.add_recipient_preconditions

import com.satwik.transfertoinr.core.model.ValidationResult

class ValidateAccountNumberUsecase{
    fun execute(accountNumber:String): ValidationResult {
        if(accountNumber.isBlank()){
            return ValidationResult(false, errorMessage = "Please enter account number")
        }
        return ValidationResult(true)
    }
}
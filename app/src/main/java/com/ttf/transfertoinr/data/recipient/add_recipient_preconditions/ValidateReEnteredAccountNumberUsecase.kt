package com.ttf.transfertoinr.data.recipient.add_recipient_preconditions

import com.ttf.transfertoinr.core.model.ValidationResult

class ValidateReEnteredAccountNumberUsecase{
    fun execute(reEnteredAccountNumber:String, accountNumber:String): ValidationResult {
        if(reEnteredAccountNumber.isBlank()){
            return ValidationResult(false, errorMessage = "Please re-enter the account number")
        }
        if(accountNumber!=reEnteredAccountNumber){
            return ValidationResult(false, errorMessage = "Account number is not matching")
        }
        return ValidationResult(true)
    }
}
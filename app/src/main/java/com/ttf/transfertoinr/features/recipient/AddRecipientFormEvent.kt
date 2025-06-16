package com.ttf.transfertoinr.features.recipient

sealed class AddRecipientFormEvent {
    data class AccountNumberChanged(val accountNumber:String):AddRecipientFormEvent()
    data class ReEnterAccountNumberChanged(val reEnterAccountNumber:String):AddRecipientFormEvent()
    data class NameChanged(val name:String):AddRecipientFormEvent()
    data class IfscChanged(val ifsc:String):AddRecipientFormEvent()
    data class BankChanged(val bank:String):AddRecipientFormEvent()
    data object Submit:AddRecipientFormEvent()
}
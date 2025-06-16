package com.ttf.transfertoinr.features.recipient

data class AddRecipientFormState(
    val name:String = "",
    var nameError:String? = null,
    val ifsc:String = "",
    var ifscError:String? = null,
    val bank:String = "",
    var bankError:String? = null,
    val accountNumber:String = "",
    var accountNumberError:String? = null,
    val reEnterAccountNumber:String = "",
    var reEnterAccountNumberError:String? = null,
)
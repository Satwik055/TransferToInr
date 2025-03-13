package com.satwik.transfertoinr.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Transaction(
    val id:Int = 0,
    val transaction_code:String = "",
    val email:String = "",
    val sent:Int = 0,
    val receive:Int = 0,
    val currency:CurrencyType = CurrencyType.EUR,
    val status:TransactionStatus = TransactionStatus.PENDING,
    val date:String = ""
)


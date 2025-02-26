package com.satwik.transfertoinr.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Transaction(
    val transaction_code:String,
    val sent:Int,
    val receive:Int,
    val currency:CurrencyType,
    val status:TransactionStatus,
    val date:String
)


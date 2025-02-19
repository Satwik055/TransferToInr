package com.satwik.transfertoinr.core.model

import kotlinx.serialization.Serializable


@Serializable
data class Transaction(
    val transaction_code:String,
    val sent:Int,
    val receive:Int,
    val currency:String,
    val status:String,
    val date:String
)

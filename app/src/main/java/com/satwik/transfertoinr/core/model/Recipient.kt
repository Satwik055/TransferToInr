package com.satwik.transfertoinr.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Recipient(
    val name:String,
    val account_number:String,
    val ifsc_code:String,
    val bank:String,
    val email:String
)

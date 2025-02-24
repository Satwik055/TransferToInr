package com.satwik.transfertoinr.core.model

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    val ttf_user_id:Int = 0,
    val name:String = "",
    val email:String = "",
    val phone:String = "",
    val kyc_status:Boolean = false,
    val preffered_currency:CurrencyType = CurrencyType.EUR
)

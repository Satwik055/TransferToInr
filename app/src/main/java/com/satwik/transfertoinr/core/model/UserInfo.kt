package com.satwik.transfertoinr.core.model

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    val name:String = "",
    val email:String = "",
)

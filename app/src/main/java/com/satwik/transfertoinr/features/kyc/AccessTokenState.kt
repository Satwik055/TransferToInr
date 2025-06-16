package com.satwik.transfertoinr.features.kyc

data class AccessTokenState(
    val accessToken: String = "",
    val error: String = "",
    val isLoading:Boolean = false
)

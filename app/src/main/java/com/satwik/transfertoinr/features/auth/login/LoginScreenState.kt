package com.satwik.transfertoinr.features.auth.login

data class LoginScreenState(
    val success:Boolean=false,
    val error: String = "",
    val isLoading: Boolean = false
)

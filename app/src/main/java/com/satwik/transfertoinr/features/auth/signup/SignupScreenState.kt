package com.satwik.transfertoinr.features.auth.signup

data class SignupScreenState(
    val success:Boolean=false,
    val error: String = "",
    val isLoading: Boolean = false
)

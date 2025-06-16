package com.ttf.transfertoinr.features.auth.verify_email

data class VerificationResultState(
    val success:Boolean=false,
    val error: String = "",
    val isLoading: Boolean = false
)

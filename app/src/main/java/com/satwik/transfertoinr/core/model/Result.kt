package com.satwik.transfertoinr.core.model

data class Result(
    val success: Boolean = false,
    val error: String = "",
    val isLoading: Boolean = false,
)

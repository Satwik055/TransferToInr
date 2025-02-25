package com.satwik.transfertoinr.data.auth.signup_preconditions

data class ValidationResult(
    val successfull:Boolean,
    val errorMessage:String? = null
)
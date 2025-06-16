package com.satwik.transfertoinr.core.model

data class ValidationResult(
    val successfull:Boolean,
    val errorMessage:String? = null
)
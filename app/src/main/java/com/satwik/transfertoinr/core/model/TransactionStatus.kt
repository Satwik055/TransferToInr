package com.satwik.transfertoinr.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class TransactionStatus {
    @SerialName("PENDING")
    PENDING,

    @SerialName("FAILED")
    FAILED,

    @SerialName("SUCCESS")
    SUCCESS
}
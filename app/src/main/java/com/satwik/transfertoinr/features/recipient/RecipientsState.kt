package com.satwik.transfertoinr.features.recipient

import com.satwik.transfertoinr.core.model.Recipient

data class RecipientsState(
    val recipients: List<Recipient> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)

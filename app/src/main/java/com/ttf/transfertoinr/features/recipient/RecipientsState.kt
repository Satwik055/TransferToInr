package com.ttf.transfertoinr.features.recipient

import com.ttf.transfertoinr.core.model.Recipient

data class RecipientsState(
    val recipients: List<Recipient> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)

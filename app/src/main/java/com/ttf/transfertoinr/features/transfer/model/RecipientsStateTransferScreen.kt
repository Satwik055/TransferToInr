package com.ttf.transfertoinr.features.transfer.model

import com.ttf.transfertoinr.core.model.Recipient

data class RecipientsStateTransferScreen(
    val recipients: List<Recipient> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)

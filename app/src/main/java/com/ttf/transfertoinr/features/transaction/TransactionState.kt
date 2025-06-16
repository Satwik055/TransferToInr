package com.ttf.transfertoinr.features.transaction

import com.ttf.transfertoinr.core.model.Transaction

data class TransactionState(
    val transaction: List<Transaction> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)

package com.satwik.transfertoinr.features.transaction

import com.satwik.transfertoinr.core.model.Recipient
import com.satwik.transfertoinr.core.model.Transaction

data class TransactionState(
    val transaction: List<Transaction> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)

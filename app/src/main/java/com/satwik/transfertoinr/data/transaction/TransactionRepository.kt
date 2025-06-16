package com.satwik.transfertoinr.data.transaction

import com.satwik.transfertoinr.core.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun getAllTransaction(): Flow<List<Transaction>>
}
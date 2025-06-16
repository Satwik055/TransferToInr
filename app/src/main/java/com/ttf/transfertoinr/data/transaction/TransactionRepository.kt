package com.ttf.transfertoinr.data.transaction

import com.ttf.transfertoinr.core.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun getAllTransaction(): Flow<List<Transaction>>
}
package com.satwik.transfertoinr.data.transaction

import com.satwik.transfertoinr.core.model.Transaction

interface TransactionRepository {
    suspend fun getAllTransaction():List<Transaction>
}
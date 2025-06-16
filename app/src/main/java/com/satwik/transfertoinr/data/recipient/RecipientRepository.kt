package com.satwik.transfertoinr.data.recipient

import com.satwik.transfertoinr.core.model.Recipient
import com.satwik.transfertoinr.core.model.Result
import kotlinx.coroutines.flow.Flow

interface RecipientRepository {
    suspend fun addRecipient(name:String, accountNumber:String, ifscCode:String, bank:String, relation:String)

    suspend fun getAllRecipients():Flow<List<Recipient>>

    suspend fun deleteRecipientById(id:Int)
}
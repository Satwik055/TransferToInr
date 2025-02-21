package com.satwik.transfertoinr.data.recipient

import com.satwik.transfertoinr.core.model.Recipient

interface RecipientRepository {
    suspend fun addRecipient(name:String, accountNumber:String, ifscCode:String, bank:String)

    suspend fun getAllRecipients():List<Recipient>

    suspend fun deleteRecipientById(id:Int)
}
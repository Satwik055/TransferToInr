package com.ttf.transfertoinr.data.transfer

import com.ttf.transfertoinr.core.model.CurrencyType
import com.ttf.transfertoinr.core.model.Profile
import com.ttf.transfertoinr.core.model.Recipient
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage
import io.github.jan.supabase.storage.upload
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class TransferRepositoryImpl(private val client: SupabaseClient): TransferRepository {
    override suspend fun createTransfer(
        transactionCode: String,
        sent: Int,
        currency: CurrencyType,
        reason:String,
        recipientId: Int,
        screenshotLink:String
    ) {

        val email = client.auth.currentUserOrNull()?.email
        val profile = client.from("ttfuser")
            .select{filter {
                eq("email", email!!)
            }}
            .decodeSingle<Profile>()

        val response = client.postgrest.rpc(
            function = "add_transaction",
            parameters = buildJsonObject {
                put("p_transaction_code", transactionCode)
                put("p_sent", sent)
                put("p_currency", currency.name)
                put("p_email", email)
                put("p_reason", reason)
                put("p_sender_id", profile.ttf_user_id)
                put("p_recipient_id", recipientId)
                put("p_screenshot", screenshotLink)

            }
        )
    }

    override suspend fun uploadScreenshot(fileName: String, fileBytes: ByteArray) {
        val bucket = client.storage.from("transaction-screenshots")
        bucket.upload(fileName, fileBytes){upsert = true}
    }
}
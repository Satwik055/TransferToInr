package com.satwik.transfertoinr.data.transfer

import com.satwik.transfertoinr.core.model.CurrencyType
import com.satwik.transfertoinr.core.model.Profile
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class TransferRepositoryImpl(private val client: SupabaseClient): TransferRepository {
    override suspend fun createTransfer(
        transactionCode: String,
        sent: Int,
        currency: CurrencyType,
        reason:String
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
            }
        )
    }
}
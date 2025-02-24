package com.satwik.transfertoinr.data.transfer

import com.satwik.transfertoinr.core.model.CurrencyType
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class TransferRepositoryImpl(private val client: SupabaseClient): TransferRepository {
    override suspend fun createTransfer(
        transactionCode: String,
        sent: Int,
        currency: CurrencyType,
    ) {
        val email = client.auth.currentUserOrNull()?.email
        val response = client.postgrest.rpc(
            function = "add_transaction",
            parameters = buildJsonObject {
                put("p_transaction_code", transactionCode)
                put("p_sent", sent)
                put("p_currency", currency.name)
                put("p_email", email)
            }
        )
    }
}
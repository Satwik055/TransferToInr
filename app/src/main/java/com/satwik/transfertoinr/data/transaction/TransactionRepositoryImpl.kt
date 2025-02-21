package com.satwik.transfertoinr.data.transaction

import com.satwik.transfertoinr.core.model.Recipient
import com.satwik.transfertoinr.core.model.Transaction
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class TransactionRepositoryImpl(private val client: SupabaseClient):TransactionRepository {
    override suspend fun getAllTransaction(): List<Transaction> {

        val email = client.auth.currentUserOrNull()?.email

        val response = client.postgrest.rpc(
            function = "get_all_transactions",
            parameters = buildJsonObject {
                put("p_email", email)
            }
        )
        val jsonString = response.data
        val transactions: List<Transaction> = Json.decodeFromString(jsonString)

        if(transactions.isEmpty()){
            throw Exception("No Transactions Found")
        }
        else{
            return transactions
        }




    }
}
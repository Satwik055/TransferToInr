package com.ttf.transfertoinr.data.recipient

import com.ttf.transfertoinr.core.model.Recipient
import com.ttf.transfertoinr.core.model.Result
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.filter.FilterOperation
import io.github.jan.supabase.postgrest.query.filter.FilterOperator
import io.github.jan.supabase.realtime.selectAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class RecipientRepositoryImpl(private val client:SupabaseClient):RecipientRepository {
    override suspend fun addRecipient(name: String, accountNumber: String, ifscCode: String, bank:String, relation:String) {
        val email = client.auth.currentUserOrNull()?.email
        val response = client.postgrest.rpc(
            function = "add_recipient_by_email",
            parameters = buildJsonObject {
                put("p_name", name)
                put("p_account_number", accountNumber)
                put("p_ifsc_code", ifscCode)
                put("p_email", email)
                put("p_bank", bank)
                put("p_relation", relation)
            }
        )
    }

    @OptIn(SupabaseExperimental::class)
    override suspend fun getAllRecipients(): Flow<List<Recipient>> {
        val email = client.auth.currentUserOrNull()!!.email
        val flow: Flow<List<Recipient>> = client.from("recipient").selectAsFlow(
            primaryKey = Recipient::email,
            filter = FilterOperation("email", FilterOperator.EQ, email!!)
        )
        return flow
    }

    override suspend fun deleteRecipientById(id: Int) {
        val email = client.auth.currentUserOrNull()!!.email
        client.auth.resetPasswordForEmail(email!!)

        client.postgrest.rpc(
            function = "delete_recipient_by_id",
            parameters = buildJsonObject {
                put("p_id", id)
            }
        )
    }
}
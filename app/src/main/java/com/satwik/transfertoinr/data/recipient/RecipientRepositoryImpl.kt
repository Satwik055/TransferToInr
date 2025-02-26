package com.satwik.transfertoinr.data.recipient

import com.satwik.transfertoinr.core.model.Recipient
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class RecipientRepositoryImpl(private val client:SupabaseClient):RecipientRepository {
    override suspend fun addRecipient(name: String, accountNumber: String, ifscCode: String, bank:String) {
        val email = client.auth.currentUserOrNull()?.email
        val response = client.postgrest.rpc(
            function = "add_recipient_by_email",
            parameters = buildJsonObject {
                put("p_name", name)
                put("p_account_number", accountNumber)
                put("p_ifsc_code", ifscCode)
                put("p_email", email)
                put("p_bank", bank)

            }
        )
    }

    override suspend fun getAllRecipients(): List<Recipient> {

        val email = client.auth.currentUserOrNull()?.email

        val response = client.postgrest.rpc(
            function = "get_recipient_by_email",
            parameters = buildJsonObject {
                put("p_email", email)
            }
        )

        val jsonString = response.data
        val recipients: List<Recipient> = Json.decodeFromString(jsonString)

        if(recipients.isEmpty()){
            throw Exception("No Recipients Found")
        }
        else{
            return recipients
        }

    }

    override suspend fun deleteRecipientById(id: Int) {
        val response = client.postgrest.rpc(
            function = "delete_recipient_by_id",
            parameters = buildJsonObject {
                put("p_id", id)
            }
        )
    }
}
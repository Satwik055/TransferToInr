package com.satwik.transfertoinr.data.recipient

import com.satwik.transfertoinr.core.model.Recipient
import com.satwik.transfertoinr.core.model.UserInfo
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


//        val uid1 = client.auth.currentUserOrNull()?.id
//        val response1 = client.postgrest.rpc(
//            function = "addttfuser",
//            parameters = buildJsonObject {
//                put("p_uid", uid1)
//            }
//        )
//        val jsonString1 = response1.data
//        val userInfo: UserInfo = Json.decodeFromString(jsonString1)
//
//        println(userInfo)



        val email = client.auth.currentUserOrNull()?.email

        val response = client.postgrest.rpc(
            function = "get_recipient_by_email",
            parameters = buildJsonObject {
                put("p_email", email)
            }
        )
        val jsonString = response.data
        val recipients: List<Recipient> = Json.decodeFromString(jsonString)
        return recipients
    }
}
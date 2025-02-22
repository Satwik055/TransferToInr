package com.satwik.transfertoinr.data.account

import com.satwik.transfertoinr.core.model.Recipient
import com.satwik.transfertoinr.core.model.UserInfo
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class AccountRepositoryImpl(private val client: SupabaseClient):AccountRepository {
    override suspend fun getUserInfo():UserInfo {

        val foo = client.auth.sessionManager.loadSession()?.user?.email
        println("SHEESH: $foo")

        val email = client.auth.currentUserOrNull()?.email
        val response = client.postgrest.rpc(
            function = "get_user_details_by_email",
            parameters = buildJsonObject {
                put("p_email", email)
            }
        )
        val jsonString = response.data
        val userInfo= Json.decodeFromString<List<UserInfo>>(jsonString)
        return userInfo.first()
    }
}
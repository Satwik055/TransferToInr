package com.satwik.transfertoinr.data.auth

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class AuthRepositoryImpl(private val client: SupabaseClient) :AuthRepository {
    override suspend fun login(email: String, password: String) {
        client.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
    }

    override suspend fun signup(email: String, password: String, name: String, phone: String) {
        val user = client.auth.signUpWith(Email) {
            this.email = email
            this.password = password
        }

            val response = client.postgrest.rpc(
                function = "addttfuser",
                parameters = buildJsonObject {
                    put("p_name", name)
                    put("p_email", email)
                    put("p_phone", phone)
                    put("p_uid",user?.id)
                }
            )
        }


    override suspend fun logout() {
        println("Logout Initiated")
        client.auth.clearSession()
    }
}
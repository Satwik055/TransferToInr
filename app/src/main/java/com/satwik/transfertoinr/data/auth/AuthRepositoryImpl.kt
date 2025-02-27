package com.satwik.transfertoinr.data.auth

import com.satwik.transfertoinr.core.model.CurrencyType
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class AuthRepositoryImpl(private val client: SupabaseClient) :AuthRepository {
    override suspend fun login(email: String, password: String) {

        try{
            client.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
        }
        catch (authException:AuthRestException){
            throw Exception(authException.errorCode?.name ?: "")
        }
    }

    override suspend fun signup(email: String, password: String, name: String, phone: String) {

        try{
            client.auth.signUpWith(Email) {
                this.email = email
                this.password = password
                this.data = buildJsonObject {
                    put("name", name)
                    put("email", email)
                    put("phone", phone)
                    put("kyc_status", false)
                    put("preferred_currency", CurrencyType.EUR.name)
                }
            }
        }
        catch (authException:AuthRestException){
            throw Exception(authException.errorCode?.name ?: "")
        }
            client.postgrest.rpc(
                function = "addttfuser",
                parameters = buildJsonObject {
                    put("p_name", name)
                    put("p_email", email)
                    put("p_phone", phone)
                }
            )
        }


    override suspend fun logout() {
        println("Logout Initiated")
        client.auth.clearSession()
    }
}
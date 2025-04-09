package com.satwik.transfertoinr.data.auth

import com.google.firebase.messaging.FirebaseMessaging
import com.satwik.transfertoinr.core.model.CurrencyType
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.tasks.await
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
            val token = FirebaseMessaging.getInstance().token.await()
            client.postgrest.rpc(
                function = "addttfuser",
                parameters = buildJsonObject {
                    put("p_name", name)
                    put("p_email", email)
                    put("p_phone", phone)
                    put("p_fcm_token", token)
                }
            )
        }
        catch (authException:AuthRestException){
            throw Exception(authException.errorCode?.name ?: "")
        }
        catch (e:Exception){
            throw Exception(e.message)
        }
    }

    override suspend fun logout() {
        println("Logout Initiated")
        client.auth.clearSession()
    }


    override suspend fun updateFcmToken(token: String){
        val email = client.auth.currentUserOrNull()?.email
        println("Email: $email")

        try {
            client.from("ttfuser")
                .update(
                    mapOf("fcm_token" to token)
                ){filter {
                    eq("email", email!!)
                }}
            println("FCM token updated successfully!")
        }
        catch(e: Exception) {
            println("Error updating FCM token: ${e.message}")
        }
    }

    override suspend fun verifyOtp(otp: String, email: String){
        client.auth.verifyEmailOtp(type = OtpType.Email.EMAIL, email = email, token = otp)
    }

    override suspend fun resendEmailOtp(email:String) {
        client.auth.resendEmail(type = OtpType.Email.SIGNUP, email = email)
    }

    override suspend fun sendPasswordResetEmail(email: String) {
        client.auth.resetPasswordForEmail(email = email)
    }

    override suspend fun changePassword(newPassword: String) {
        client.auth.updateUser { password = newPassword }
    }
}
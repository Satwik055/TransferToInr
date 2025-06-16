package com.ttf.transfertoinr.data.auth

import io.github.jan.supabase.auth.OtpType

interface AuthRepository {
    suspend fun login(email: String, password: String)
    suspend fun signup(email: String, password: String, name: String, phone: String)
    suspend fun updateFcmToken(token: String)
    suspend fun verifyRegistrationOtp(otp: String, email: String)
    suspend fun verifyResetOtp(otp:String, email: String)
    suspend fun resendEmailOtp(email: String, type:OtpType.Email)
    suspend fun sendPasswordResetEmail(email: String)
    suspend fun changePassword(newPassword:String)
    suspend fun logout()
}
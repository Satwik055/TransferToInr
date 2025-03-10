package com.satwik.transfertoinr.data.auth

interface AuthRepository {
    suspend fun login(email: String, password: String)
    suspend fun signup(email: String, password: String, name: String, phone: String)
    suspend fun updateFcmToken(token: String)
    suspend fun verifyOtp(otp: String, email: String)
    suspend fun resendEmailOtp(email: String)
    suspend fun logout()
}
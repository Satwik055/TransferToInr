package com.satwik.transfertoinr.data.auth

interface AuthRepository {
    suspend fun login(email: String, password: String)
    suspend fun signup(email: String, password: String)
    suspend fun logout()
}
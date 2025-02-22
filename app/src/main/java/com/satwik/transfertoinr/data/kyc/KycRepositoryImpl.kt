package com.satwik.transfertoinr.data.kyc

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

class KycRepositoryImpl: KycRepository {
    override suspend fun getAccessToken(email:String, phone:String, userId:String, levelName:String, ttlInSecs:Int): String {
        val client = OkHttpClient()

        val mediaType = "application/json".toMediaTypeOrNull()
        val body = RequestBody.create(mediaType, "{\"applicantIdentifiers\":{\"email\":\"satwikkumar055@gmail.com\",\"phone\":\"8210218995\"},\"ttlInSecs\":600000,\"userId\":\"245\",\"levelName\":\"id-and-liveness\"}")
        val request = Request.Builder()
            .url("https://api.sumsub.com/resources/accessTokens/sdk")
            .post(body)
            .addHeader("content-type", "application/json")
            .addHeader("X-App-Token", "sbx:uqHBhyIBRf4JdIdBihEGSrBh.hGO3lzYchO6tILFNLk6e6TucxQqTM1lF")
            .build()

        val response = client.newCall(request).execute()

        return response.body?.string() ?: ""
    }

}
package com.satwik.transfertoinr.data.kyc

import com.satwik.transfertoinr.core.utils.SumsubSecrets
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.nio.charset.StandardCharsets
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class KycRepositoryImpl(private val client: SupabaseClient): KycRepository {

    override suspend fun getAccessToken(email:String, phone:String, userId:String): String {
        return withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val levelName = "id-and-liveness"
            val ttlInSecs = 3600
            val time = System.currentTimeMillis() / 1000
            val appToken = SumsubSecrets.Test.APP_TOKEN
            val secret = SumsubSecrets.Test.SECRET_KEY
            val body = "{\"applicantIdentifiers\":{\"email\":\"$email\",\"phone\":\"$phone\"},\"ttlInSecs\":$ttlInSecs,\"userId\":\"$userId\",\"levelName\":\"$levelName\"}"

            val signature = generateSignature(ts = time, key = secret, body = body)

            val request = Request.Builder()
                .url("https://api.sumsub.com/resources/accessTokens/sdk")
                .post(body.toRequestBody("application/json".toMediaTypeOrNull()))
                .addHeader("content-type", "application/json")
                .addHeader("X-App-Token", appToken)
                .addHeader("X-App-Access-Sig", signature)
                .addHeader("X-App-Access-Ts", time.toString())
                .build()

            val response = client.newCall(request).execute()

            if(response.isSuccessful){
                val responseBody = client.newCall(request).execute().body?.string() ?: ""
                val accessTokenResponse = Json.decodeFromString<AccessTokenResponse>(responseBody)
                return@withContext accessTokenResponse.token
            }
            else{
                throw Exception("Error occurred with code: ${response.code}, and message: ${response.message}")
            }
        }
    }

    override fun generateSignature(
        ts: Long,
        key: String,
        body:String
    ): String {
        val path = "/resources/accessTokens/sdk"
        val hmacSha256 = Mac.getInstance("HmacSHA256")
        val secretKeySpec = SecretKeySpec(key.toByteArray(StandardCharsets.UTF_8), "HmacSHA256")
        hmacSha256.init(secretKeySpec)
        hmacSha256.update((ts.toString() + "POST" + path).toByteArray(StandardCharsets.UTF_8))
        val bytes = body.toByteArray(StandardCharsets.UTF_8).let { hmacSha256.doFinal(it) } ?: hmacSha256.doFinal()
        return bytes.joinToString("") { "%02x".format(it) }
    }

    override suspend fun updateKycStatus(status: Boolean, id:Int) {
        val response = client.postgrest.rpc(
            function = "update_kyc_status",
            parameters = buildJsonObject {
                put("p_ttf_user_id", id)
                put("p_kyc_status", status)
            }
        )
    }
}

@Serializable
data class AccessTokenResponse(
    val token: String,
    val userId: String
)
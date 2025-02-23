package com.satwik.transfertoinr.features.kyc

import android.os.Build
import androidx.annotation.RequiresApi
import de.authada.org.bouncycastle.util.encoders.Hex
import io.ktor.http.HttpMethod
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

fun main(){
    println(getAccessToken("23", "id-and-liveness", 600, "asd", "3534345"))
}

fun getAccessToken(userId:String, levelName:String, ttlInSecs:Int, email: String, phone: String): String {
    val client = OkHttpClient()
    val time = System.currentTimeMillis() / 1000
    val appToken = "sbx:WN4xuyf3SQmqE0zwbJHH7dUk.GyNV2VpyFEED8NWId7eoH7L17t7kPNX2"
    val secret = "szwbAQ6nc1n3F3PkhjuN8dNpAWriaqbw"
    val body = "{\"applicantIdentifiers\":{\"email\":\"$email\",\"phone\":\"$phone\"},\"ttlInSecs\":$ttlInSecs,\"userId\":\"$userId\",\"levelName\":\"$levelName\"}"

    val signature = generateSignature(
        ts = time,
        key = secret,
        body = body
    )

    val request = Request.Builder()
        .url("https://api.sumsub.com/resources/accessTokens/sdk")
        .post(body.toRequestBody("application/json".toMediaTypeOrNull()))
        .addHeader("content-type", "application/json")
        .addHeader("X-App-Token", appToken)
        .addHeader("X-App-Access-Sig", signature)
        .addHeader("X-App-Access-Ts", time.toString())
        .build()

    val response = client.newCall(request).execute().body?.string() ?: ""
    val responseBody = Json.decodeFromString<AccessTokenResponse>(response)
    return responseBody.token
}


fun generateSignature(ts: Long, key:String, body:String): String {
    val path = "/resources/accessTokens/sdk"
    val hmacSha256 = Mac.getInstance("HmacSHA256")
    val secretKeySpec = SecretKeySpec(key.toByteArray(StandardCharsets.UTF_8), "HmacSHA256")
    hmacSha256.init(secretKeySpec)
    hmacSha256.update((ts.toString() + "POST" + path).toByteArray(StandardCharsets.UTF_8))
    val bytes = body.toByteArray(StandardCharsets.UTF_8).let { hmacSha256.doFinal(it) } ?: hmacSha256.doFinal()
    return bytes.joinToString("") { "%02x".format(it) }
}


@Serializable
data class AccessTokenResponse(
    val token: String,
    val userId: String
)
package com.satwik.transfertoinr.features.kyc

import android.os.Build
import androidx.annotation.RequiresApi
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.Base64
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@RequiresApi(Build.VERSION_CODES.O)
fun main(){
//    foo()

    println(getAccessToken("satwikkumar055@gmail.com", "821021995", "21", "id-and-liveness", 60000))
}

@RequiresApi(Build.VERSION_CODES.O)
fun getAccessToken(email:String, phone:String, userId:String, levelName:String, ttlInSecs:Int): String {
    val client = OkHttpClient()
    val time = System.currentTimeMillis()/1000

    val mediaType = "application/json".toMediaTypeOrNull()
    val body =
        "{\"applicantIdentifiers\":{\"email\":\"$email\",\"phone\":\"$phone\"},\"ttlInSecs\":$ttlInSecs,\"userId\":\"$userId\",\"levelName\":\"$levelName\"}".toRequestBody(
            mediaType
        )


    // Example usage
    val secret = "R1XeS0zCmNkDUHRPwKyhtpGiL87sYXCi"
//    val data = "$time+POST/resources/accessTokens?userId=$userId&levelName=$levelName&ttlInSecs=$ttlInSecs&applicantIdentifiers.email=$email&applicantIdentifiers.phone=$phone"
    val data = "$time+POST/resources/accessTokens?userId=$userId&levelName=$levelName&ttlInSecs=$ttlInSecs"
    val signature = generateSignature(secret, data)

    val request = Request.Builder()
        .url("https://api.sumsub.com/resources/accessTokens/sdk")
        .post(body)
        .addHeader("content-type", "application/json")
        .addHeader("X-App-Token", "sbx:o0ZJDRaqp3Cm6AmKxFNj7xBl.xli866gudNgzrPfTju3stM3SQkSKHJRy")
        .addHeader("X-App-Access-Sig", signature)
        .addHeader("X-App-Access-Ts", "$time")
        .build()

    val response = client.newCall(request).execute()

    return response.body?.string() ?: ""
}


fun foo(){
    val client = OkHttpClient()

    val time = System.currentTimeMillis()

    val mediaType = "application/json".toMediaTypeOrNull()
    val body = RequestBody.create(mediaType, "{\"applicantIdentifiers\":{\"email\":\"string\",\"phone\":\"string\"},\"ttlInSecs\":600,\"userId\":\"string\",\"levelName\":\"id-and-liveness\"}")
    val request = Request.Builder()
        .url("https://api.sumsub.com/resources/accessTokens/sdk")
        .post(body)
        .addHeader("content-type", "application/json")
        .addHeader("X-App-Token", "sbx:lSnT7ikVjtXs2kW2TMIiXC9u.0SU0nbnL6LF4jt6SniiiXqyGNRa6URI2")
        .addHeader("X-App-Access-Sig", "1607551635POST/resources/accessTokens?userId=cfd20712-24a2-4c7d-9ab0-146f3c142335&levelName=basic-kyc-level&ttlInSecs=600")
        .addHeader("X-App-Access-Ts", "sbx:lSnT7ikVjtXs2kW2TMIiXC9u.0SU0nbnL6LF4jt6SniiiXqyGNRa6URI2")
        .build()

    val response = client.newCall(request).execute()
    println(response)
}


@RequiresApi(Build.VERSION_CODES.O)
fun generateSignature(secret: String, data: String): String {
    val hmac = Mac.getInstance("HmacSHA256")
    val secretKey = SecretKeySpec(secret.toByteArray(), "HmacSHA256")
    hmac.init(secretKey)
    val hash = hmac.doFinal(data.toByteArray())
    return Base64.getEncoder().encodeToString(hash)
}


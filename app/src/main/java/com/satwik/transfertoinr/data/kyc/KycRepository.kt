package com.satwik.transfertoinr.data.kyc

interface KycRepository {

    suspend fun getAccessToken(email:String, phone:String, userId:String, levelName:String, ttlInSecs:Int):String
}
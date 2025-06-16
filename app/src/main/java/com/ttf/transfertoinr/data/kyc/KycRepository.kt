package com.ttf.transfertoinr.data.kyc

interface KycRepository {

    suspend fun getAccessToken(email:String, phone:String, userId:String):String

    fun generateSignature(ts: Long, key:String, body:String):String

    suspend fun updateKycStatus(status:Boolean, id:Int)
}
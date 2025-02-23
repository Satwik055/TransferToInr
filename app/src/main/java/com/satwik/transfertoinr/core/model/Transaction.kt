package com.satwik.transfertoinr.core.model

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.datetime.Instant
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@Serializable
enum class TransactionStatus {
    @SerialName("PENDING")
    PENDING,

    @SerialName("FAILED")
    FAILED,

    @SerialName("SUCCESS")
    SUCCESS
}

@Serializable
data class Transaction(
    val transaction_code:String,
    val sent:Int,
    val receive:Int,
    val currency:String,
    val status:TransactionStatus,
    val date:String
)


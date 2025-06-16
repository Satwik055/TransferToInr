package com.ttf.transfertoinr.core.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun formatTimestamp(timeStamp:String):String{
    val isoTimestamp = timeStamp.replace(" ", "T")
    val parsedTimestamp = OffsetDateTime.parse(isoTimestamp)
    val formatter = DateTimeFormatter.ofPattern("dd MMM yy")
    val formattedTimestamp = parsedTimestamp.format(formatter)
    return formattedTimestamp
}
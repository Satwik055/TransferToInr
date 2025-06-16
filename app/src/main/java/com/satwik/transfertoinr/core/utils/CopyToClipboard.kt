package com.satwik.transfertoinr.core.utils

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager

fun copyToClipboard(activity: Activity, text: String){
    val clipboardManager = activity.getSystemService(Activity.CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = ClipData.newPlainText("Copied Text", text)
    clipboardManager.setPrimaryClip(clipData)
}
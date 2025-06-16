package com.ttf.transfertoinr.data.transfer

import com.ttf.transfertoinr.core.model.CurrencyType

interface TransferRepository {
    suspend fun createTransfer(transactionCode:String, sent:Int, currency:CurrencyType, reason:String, recipientId:Int, screenshotLink:String)
    suspend fun uploadScreenshot(fileName:String, fileBytes:ByteArray)
}
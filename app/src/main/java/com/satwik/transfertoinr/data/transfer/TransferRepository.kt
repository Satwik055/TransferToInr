package com.satwik.transfertoinr.data.transfer

import com.satwik.transfertoinr.core.model.CurrencyType

interface TransferRepository {
    suspend fun createTransfer(transactionCode:String, sent:Int, currency:CurrencyType, reason:String, recipientId:Int, screenshotLink:String)
    suspend fun uploadScreenshot(fileName:String, fileBytes:ByteArray)
}
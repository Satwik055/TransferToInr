package com.satwik.transfertoinr.data.transfer

interface TransferRepository {
    suspend fun createTransfer(transactionCode:String, sent:Int, recieve:Int, currency:String)
}
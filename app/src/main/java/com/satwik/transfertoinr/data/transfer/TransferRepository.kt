package com.satwik.transfertoinr.data.transfer

import com.satwik.transfertoinr.core.model.CurrencyType

interface TransferRepository {
    suspend fun createTransfer(transactionCode:String, sent:Int, currency:CurrencyType)
}
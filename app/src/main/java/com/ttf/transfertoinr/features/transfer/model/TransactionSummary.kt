package com.ttf.transfertoinr.features.transfer.model

import com.ttf.transfertoinr.core.model.CurrencyType
import com.ttf.transfertoinr.core.model.Recipient

data class TransactionSummary (
    val send:Int = 0,
    val receive:Int = 0,
    val reason:String = "",
    val exchangeRate:Double = 0.0,
    val fee:Double = 0.0,
    val currency:CurrencyType = CurrencyType.EUR,
    val recipient: Recipient = Recipient(),
    val screenshotLink:String = ""
)
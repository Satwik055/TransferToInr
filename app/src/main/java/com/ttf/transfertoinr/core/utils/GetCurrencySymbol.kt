package com.ttf.transfertoinr.core.utils

import com.ttf.transfertoinr.core.model.CurrencyType

fun getCurrencySymbol(currency: CurrencyType): String {
    return when (currency) {
        CurrencyType.EUR -> "€"
        CurrencyType.USD -> "$"
        CurrencyType.GBP -> "£"
        CurrencyType.AUD -> "A$"
        CurrencyType.CAD -> "C$"
        CurrencyType.BANK -> "BANK"
    }
}


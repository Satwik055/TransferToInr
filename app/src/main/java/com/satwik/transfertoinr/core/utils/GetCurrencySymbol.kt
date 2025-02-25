package com.satwik.transfertoinr.core.utils

import com.satwik.transfertoinr.core.model.CurrencyType

fun getCurrencySymbol(currency: CurrencyType): String {
    return when (currency) {
        CurrencyType.EUR -> "€"
        CurrencyType.USD -> "$"
        CurrencyType.GBP -> "£"
        CurrencyType.AUD -> "A$"
        CurrencyType.CAD -> "C$"
    }
}


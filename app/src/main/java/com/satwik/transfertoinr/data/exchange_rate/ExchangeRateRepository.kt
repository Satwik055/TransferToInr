package com.satwik.transfertoinr.data.exchange_rate

import com.satwik.transfertoinr.core.model.CurrencyType
import com.satwik.transfertoinr.core.model.ExchangeRate

interface ExchangeRateRepository {
    suspend fun getExchangeRates(currencyName: CurrencyType): ExchangeRate
}
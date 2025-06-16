package com.ttf.transfertoinr.data.exchange_rate

import com.ttf.transfertoinr.core.model.CurrencyType
import com.ttf.transfertoinr.core.model.ExchangeRate
import kotlinx.coroutines.flow.Flow

interface ExchangeRateRepository {
    suspend fun getExchangeRates(currency: CurrencyType): Flow<ExchangeRate>
}
package com.satwik.transfertoinr.data.exchange_rate

import com.satwik.transfertoinr.core.model.CurrencyType
import com.satwik.transfertoinr.core.model.ExchangeRate
import kotlinx.coroutines.flow.Flow

interface ExchangeRateRepository {
    suspend fun getExchangeRates(currency: CurrencyType): Flow<ExchangeRate>
}
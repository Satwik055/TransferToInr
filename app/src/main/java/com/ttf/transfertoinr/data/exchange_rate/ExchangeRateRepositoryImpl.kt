package com.ttf.transfertoinr.data.exchange_rate

import com.ttf.transfertoinr.core.model.CurrencyType
import com.ttf.transfertoinr.core.model.ExchangeRate
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.realtime.selectSingleValueAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put


class ExchangeRateRepositoryImpl(private val client:SupabaseClient):ExchangeRateRepository {
    @OptIn(SupabaseExperimental::class)
    override suspend fun getExchangeRates(currency:CurrencyType): Flow<ExchangeRate> {
        val flow: Flow<ExchangeRate> = client.from("exchange_rate").selectSingleValueAsFlow(ExchangeRate::currency_name){
            eq("currency_name", currency.name)
        }
        return flow
    }
}
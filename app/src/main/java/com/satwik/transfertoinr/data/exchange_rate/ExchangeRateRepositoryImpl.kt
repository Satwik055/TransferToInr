package com.satwik.transfertoinr.data.exchange_rate

import com.satwik.transfertoinr.core.model.CurrencyType
import com.satwik.transfertoinr.core.model.ExchangeRate
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put


class ExchangeRateRepositoryImpl(private val client:SupabaseClient):ExchangeRateRepository {
    override suspend fun getExchangeRates(currencyName:CurrencyType): ExchangeRate {
        val response = client.postgrest.rpc(
            function = "get_exchange_rate",
            parameters = buildJsonObject {
                put("p_currency_name", currencyName.name)
            }
        )
        val jsonString = response.data
        val exchangeRates = Json.decodeFromString<List<ExchangeRate>>(jsonString)

        if(exchangeRates.isEmpty()){
            throw Exception("Currency does not exist")
        }
        else{
            println(exchangeRates)
            return exchangeRates.first()
        }
    }
}
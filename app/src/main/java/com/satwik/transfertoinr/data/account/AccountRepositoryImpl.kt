package com.satwik.transfertoinr.data.account

import com.satwik.transfertoinr.core.model.ExchangeRate
import com.satwik.transfertoinr.core.model.CurrencyType
import com.satwik.transfertoinr.core.model.Profile
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.realtime.selectSingleValueAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class AccountRepositoryImpl(private val client: SupabaseClient):AccountRepository {
    @OptIn(SupabaseExperimental::class)
    override suspend fun getProfile(): Flow<Profile> {
        val email = client.auth.currentUserOrNull()?.email
        val flow: Flow<Profile> = supabaseClient.from("ttfuser").selectSingleValueAsFlow(Profile::email){
            eq("email", email!!)
        }
        return flow
    }

    override suspend fun getExchangeRates(currency:CurrencyType): ExchangeRate {
        val response = client.postgrest.rpc(
            function = "get_exchange_rate",
            parameters = buildJsonObject {
                put("p_currency_name", currency.name)
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

    override suspend fun updatePrefferedCurrency(email:String, currency: CurrencyType) {
        client.postgrest.rpc(
            function = "update_preferred_currency",
            parameters = buildJsonObject {
                put("p_email", email)
                put("p_currency", currency.name)
            }
        )
    }
}




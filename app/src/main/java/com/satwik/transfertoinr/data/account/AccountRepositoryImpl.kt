package com.satwik.transfertoinr.data.account

import com.satwik.transfertoinr.core.model.ExchangeRate
import com.satwik.transfertoinr.core.model.CurrencyType
import com.satwik.transfertoinr.core.model.UserInfo
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class AccountRepositoryImpl(private val client: SupabaseClient):AccountRepository {
    override suspend fun getUserInfo():UserInfo {

        val foo = client.auth.sessionManager.loadSession()?.user?.email
        println("SHEESH: $foo")

        val email = client.auth.currentUserOrNull()?.email
        val response = client.postgrest.rpc(
            function = "get_user_details_by_email",
            parameters = buildJsonObject {
                put("p_email", email)
            }
        )
        val jsonString = response.data
        val userInfo= Json.decodeFromString<List<UserInfo>>(jsonString)
        return userInfo.first()
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
}
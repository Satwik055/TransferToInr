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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.put

class AccountRepositoryImpl(private val client: SupabaseClient):AccountRepository {
    @OptIn(SupabaseExperimental::class)
    override suspend fun getProfile(): Profile {
        val email = client.auth.currentUserOrNull()?.email
        client.from("ttfuser").selectSingleValueAsFlow(Profile::email) {
            eq("email", email!!)
        }
            .onEach { profile ->
                client.auth.updateUser {
                    data {
                        put("name", profile.name)
                        put("email", profile.email)
                        put("phone", profile.phone)
                        put("kyc_status", profile.kyc_status)
                        put("preferred_currency", profile.preferred_currency.name)
                    }
                }
            }
        val metadata = client.auth.currentUserOrNull()?.userMetadata
        val profile = metadata?.let { Json.decodeFromJsonElement<Profile>(it) }
        return profile!!
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




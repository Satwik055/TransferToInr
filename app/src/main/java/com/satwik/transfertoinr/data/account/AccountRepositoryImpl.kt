package com.satwik.transfertoinr.data.account

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.satwik.transfertoinr.core.model.ExchangeRate
import com.satwik.transfertoinr.core.model.CurrencyType
import com.satwik.transfertoinr.core.model.Profile
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.handleDeeplinks
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.exception.PostgrestRestException
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.realtime.selectSingleValueAsFlow
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retry
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.put

class AccountRepositoryImpl(private val client: SupabaseClient):AccountRepository {

    @OptIn(SupabaseExperimental::class)
    override suspend fun getProfile(): Flow<Profile> {
        val email = client.auth.currentUserOrNull()?.email
        val flow = client.from("ttfuser").selectSingleValueAsFlow(Profile::email) {
            eq("email", email!!)
        }
        return flow
    }

    override suspend fun getCarousellImageLinks(): List<String> {
        val files = client.storage
            .from("carousel-images")
            .list()
        val urls = files.map { file ->
            client.storage
                .from("carousel-images")
                .publicUrl(file.name)
        }
        return urls
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




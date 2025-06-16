package com.ttf.transfertoinr.data.transaction

import com.ttf.transfertoinr.core.model.Transaction
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.filter.FilterOperation
import io.github.jan.supabase.postgrest.query.filter.FilterOperator
import io.github.jan.supabase.realtime.selectAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class TransactionRepositoryImpl(private val client: SupabaseClient):TransactionRepository {

    @OptIn(SupabaseExperimental::class)
    override suspend fun getAllTransaction(): Flow<List<Transaction>> {
        val email = client.auth.currentUserOrNull()!!.email
        val flow: Flow<List<Transaction>> = client.from("transaction").selectAsFlow(
            primaryKey = Transaction::email,
            filter = FilterOperation("email", FilterOperator.EQ, email!!)
        )
        return flow
    }
}
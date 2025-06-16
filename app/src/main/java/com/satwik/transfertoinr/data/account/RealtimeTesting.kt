package com.satwik.transfertoinr.data.account

import com.satwik.transfertoinr.core.model.CurrencyType
import com.satwik.transfertoinr.core.model.ExchangeRate
import com.satwik.transfertoinr.core.model.Recipient
import com.satwik.transfertoinr.core.model.Transaction
import com.satwik.transfertoinr.core.model.Profile
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.FilterOperation
import io.github.jan.supabase.postgrest.query.filter.FilterOperator
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.realtime.selectAsFlow
import io.github.jan.supabase.realtime.selectSingleValueAsFlow
import io.github.jan.supabase.storage.Storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


val supabaseClient  = createSupabaseClient(
    supabaseUrl = "https://fqsnwalptczelvhiwohd.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZxc253YWxwdGN6ZWx2aGl3b2hkIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Mzg2NzI4MTYsImV4cCI6MjA1NDI0ODgxNn0.TzD0KcPnEJz0DvLYxUmK68PeDuNy47sU0jRlyhAls-I"
) {
    install(Postgrest)
    install(Realtime){}
    install(Storage)
}

@OptIn(SupabaseExperimental::class)
fun getExchangeRates(supabaseClient: SupabaseClient, currency: CurrencyType): Flow<ExchangeRate> {
    val flow: Flow<ExchangeRate> = supabaseClient.from("exchange_rate").selectSingleValueAsFlow(ExchangeRate::currency_name){
        eq("currency_name", currency.name)
    }
    return flow
}

@OptIn(SupabaseExperimental::class)
fun getAllTransaction(supabaseClient: SupabaseClient, email:String): Flow<List<Transaction>> {
    val flow: Flow<List<Transaction>> = supabaseClient.from("transaction").selectAsFlow(
        primaryKey = Transaction::email,
        filter = FilterOperation("email", FilterOperator.EQ, email)
    )
    return flow
}


@OptIn(SupabaseExperimental::class)
fun getAllRecipients(supabaseClient: SupabaseClient, email:String): Flow<List<Recipient>> {
    val flow: Flow<List<Recipient>> = supabaseClient.from("recipient").selectAsFlow(
        primaryKey = Recipient::email,
        filter = FilterOperation("email", FilterOperator.EQ, email)
    )
    return flow
}

@OptIn(SupabaseExperimental::class)
fun getProfile(email:String): Flow<Profile> {
    val flow: Flow<Profile> = supabaseClient.from("ttfuser").selectSingleValueAsFlow(Profile::email){
        eq("email", email)
    }
    return flow
}

fun main() = runBlocking{
    getProfile("satwikkumar055@gmail.com").collect{
        println(it)
    }
}

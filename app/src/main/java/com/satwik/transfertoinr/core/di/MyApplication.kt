package com.satwik.transfertoinr.core.di

import android.app.Application
import com.google.firebase.messaging.FirebaseMessaging
import com.satwik.transfertoinr.data.account.accountModule
import com.satwik.transfertoinr.data.auth.authModule
import com.satwik.transfertoinr.data.exchange_rate.exchangeRateModule
import com.satwik.transfertoinr.data.kyc.kycModule
import com.satwik.transfertoinr.data.recipient.recipientModule
import com.satwik.transfertoinr.data.transaction.transactionModule
import com.satwik.transfertoinr.data.transfer.transferModule
import io.github.jan.supabase.SupabaseClient
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication:Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(
                supabaseModule,
                authModule,
                recipientModule,
                transferModule,
                transactionModule,
                accountModule,
                kycModule,
                exchangeRateModule
            )
        }
    }
}
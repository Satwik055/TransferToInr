package com.ttf.transfertoinr.core.di

import android.app.Application
import com.google.firebase.messaging.FirebaseMessaging
import com.ttf.transfertoinr.data.account.accountModule
import com.ttf.transfertoinr.data.auth.authModule
import com.ttf.transfertoinr.data.exchange_rate.exchangeRateModule
import com.ttf.transfertoinr.data.kyc.kycModule
import com.ttf.transfertoinr.data.recipient.recipientModule
import com.ttf.transfertoinr.data.transaction.transactionModule
import com.ttf.transfertoinr.data.transfer.transferModule
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
                transactionModule,
                transferModule,
                accountModule,
                kycModule,
                exchangeRateModule
            )
        }
    }
}
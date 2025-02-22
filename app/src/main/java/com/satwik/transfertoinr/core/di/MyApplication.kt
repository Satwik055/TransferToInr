package com.satwik.transfertoinr.core.di

import android.app.Application
import com.satwik.transfertoinr.data.account.accountModule
import com.satwik.transfertoinr.data.auth.authModule
import com.satwik.transfertoinr.data.kyc.kycModule
import com.satwik.transfertoinr.data.recipient.recipientModule
import com.satwik.transfertoinr.data.transaction.transactionModule
import com.satwik.transfertoinr.data.transfer.transferModule
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
                kycModule
            )
        }
    }
}
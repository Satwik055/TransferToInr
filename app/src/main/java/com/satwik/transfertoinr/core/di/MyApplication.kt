package com.satwik.transfertoinr.core.di

import android.app.Application
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.satwik.transfertoinr.data.account.accountModule
import com.satwik.transfertoinr.data.auth.authModule
import com.satwik.transfertoinr.data.exchange_rate.exchangeRateModule
import com.satwik.transfertoinr.data.kyc.kycModule
import com.satwik.transfertoinr.data.recipient.recipientModule
import com.satwik.transfertoinr.data.transaction.transactionModule
import com.satwik.transfertoinr.data.transfer.transferModule
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

        runBlocking{
            retrieveFcmToken()
        }

    }


    private suspend fun retrieveFcmToken(){
        try {
            val token = FirebaseMessaging.getInstance().token.await()
            println("FCM TOKEN:$token")
        }
        catch (e:Exception){
            println("Error occurred:${e.message}")
        }
    }

//    private fun retrieveFcmToken(){
//        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
//            if (!task.isSuccessful) {
//                println("Fetching FCM registration token failed, ${task.exception}")
//                return@OnCompleteListener
//            }
//            val token = task.result
//        })
//    }


}
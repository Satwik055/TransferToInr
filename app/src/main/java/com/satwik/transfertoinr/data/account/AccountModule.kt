package com.satwik.transfertoinr.data.account

import com.google.firebase.messaging.FirebaseMessagingService
import com.satwik.transfertoinr.core.utils.FirebasePushMessagingService
import com.satwik.transfertoinr.features.account.AccountsScreenViewModel
import com.satwik.transfertoinr.features.home.HomeScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val accountModule = module {
    single<AccountRepository> {
        AccountRepositoryImpl(get())
    }

    single {
        FirebasePushMessagingService(get())
    }
    viewModel {
        AccountsScreenViewModel(get(), get())
    }

    viewModel {
        HomeScreenViewModel(get(), get())
    }
}



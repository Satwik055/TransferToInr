package com.satwik.transfertoinr.data.account

import com.satwik.transfertoinr.data.auth.AuthRepository
import com.satwik.transfertoinr.data.auth.AuthRepositoryImpl
import com.satwik.transfertoinr.features.account.AccountsScreenViewModel
import com.satwik.transfertoinr.features.home.HomeScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val accountModule = module {
    single<AccountRepository> {
        AccountRepositoryImpl(get())
    }

    viewModel {
        AccountsScreenViewModel(get(), get())
    }

    viewModel {
        HomeScreenViewModel(get(), get())
    }
}



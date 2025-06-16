package com.ttf.transfertoinr.data.account

import com.ttf.transfertoinr.features.account.AccountsScreenViewModel
import com.ttf.transfertoinr.features.home.HomeScreenViewModel
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



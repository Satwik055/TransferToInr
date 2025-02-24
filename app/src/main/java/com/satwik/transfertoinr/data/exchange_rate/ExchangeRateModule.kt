package com.satwik.transfertoinr.data.exchange_rate

import com.satwik.transfertoinr.data.account.AccountRepository
import com.satwik.transfertoinr.data.account.AccountRepositoryImpl
import com.satwik.transfertoinr.features.account.AccountsScreenViewModel
import com.satwik.transfertoinr.features.home.HomeScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val exchangeRateModule = module {
    single<ExchangeRateRepository> {
        ExchangeRateRepositoryImpl(get())
    }
}


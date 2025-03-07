package com.satwik.transfertoinr.data.exchange_rate

import org.koin.dsl.module

val exchangeRateModule = module {
    single<ExchangeRateRepository> {
        ExchangeRateRepositoryImpl(get())
    }
}


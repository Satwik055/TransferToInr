package com.satwik.transfertoinr.data.kyc

import com.satwik.transfertoinr.features.kyc.KycScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val kycModule = module {
    single<KycRepository> {
        KycRepositoryImpl(get())
    }

    viewModel {
        KycScreenViewModel(get(), get())
    }
}
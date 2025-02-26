package com.satwik.transfertoinr.data.transfer

import com.satwik.transfertoinr.features.transfer.TransferScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val transferModule = module {
    single<TransferRepository> {
        TransferRepositoryImpl(get())
    }

    viewModel {
        TransferScreenViewModel(get(), get(), get(), get())
    }
}
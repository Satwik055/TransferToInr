package com.satwik.transfertoinr.data.transfer

import com.satwik.transfertoinr.features.transfer.amount_screen.AmountScreenViewModel
import com.satwik.transfertoinr.features.transfer.shared_viewmodel.TransferSharedViewModel
import com.satwik.transfertoinr.features.transfer.select_recipient_screen.SelectRecipientViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val transferModule = module {
    single<TransferRepository> {
        TransferRepositoryImpl(get())
    }

    viewModel {
        TransferSharedViewModel(get(), get(), get())
    }

    viewModel {
        AmountScreenViewModel(get(), get())
    }

    viewModel {
        SelectRecipientViewModel(get())
    }
}
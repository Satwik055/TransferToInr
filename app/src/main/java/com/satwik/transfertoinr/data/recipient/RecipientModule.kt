package com.satwik.transfertoinr.data.recipient

import com.satwik.transfertoinr.features.recipient.RecipientViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val recipientModule = module {
    single<RecipientRepository> {
        RecipientRepositoryImpl(get())
    }

    viewModel {
        RecipientViewModel(get())
    }
}
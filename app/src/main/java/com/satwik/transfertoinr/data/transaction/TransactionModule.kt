package com.satwik.transfertoinr.data.transaction

import com.satwik.transfertoinr.data.recipient.RecipientRepository
import com.satwik.transfertoinr.data.recipient.RecipientRepositoryImpl
import com.satwik.transfertoinr.features.recipient.RecipientViewModel
import com.satwik.transfertoinr.features.transaction.TransactionViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val transactionModule = module {
    single<TransactionRepository> {
        TransactionRepositoryImpl(get())
    }

    viewModel {
        TransactionViewModel(get(), get())
    }
}
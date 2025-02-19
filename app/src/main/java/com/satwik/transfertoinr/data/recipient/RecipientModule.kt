package com.satwik.transfertoinr.data.recipient

import com.satwik.transfertoinr.core.main.MainActivityViewModel
import com.satwik.transfertoinr.data.auth.AuthRepository
import com.satwik.transfertoinr.data.auth.AuthRepositoryImpl
import com.satwik.transfertoinr.features.account.AccountsScreenViewModel
import com.satwik.transfertoinr.features.auth.login.LoginScreenViewModel
import com.satwik.transfertoinr.features.auth.signup.SignupScreenViewModel
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
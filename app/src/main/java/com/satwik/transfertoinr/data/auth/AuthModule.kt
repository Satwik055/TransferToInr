package com.satwik.transfertoinr.data.auth

import com.satwik.transfertoinr.core.main.MainActivityViewModel
import com.satwik.transfertoinr.features.account.AccountsScreenViewModel
import com.satwik.transfertoinr.features.auth.login.LoginScreenViewModel
import com.satwik.transfertoinr.features.auth.signup.SignupScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    single<AuthRepository> {
        AuthRepositoryImpl(get())
    }

    viewModel {
        SignupScreenViewModel(get())
    }

    viewModel {
        LoginScreenViewModel(get())
    }

    viewModel {
        MainActivityViewModel(get())
    }

    viewModel {
        AccountsScreenViewModel(get())
    }


}
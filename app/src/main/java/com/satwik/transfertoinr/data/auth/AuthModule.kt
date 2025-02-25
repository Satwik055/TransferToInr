package com.satwik.transfertoinr.data.auth

import com.satwik.transfertoinr.core.main.MainActivityViewModel
import com.satwik.transfertoinr.data.auth.signup_preconditions.ValidateEmailUsecase
import com.satwik.transfertoinr.data.auth.signup_preconditions.ValidateNameUsecase
import com.satwik.transfertoinr.data.auth.signup_preconditions.ValidatePasswordUsecase
import com.satwik.transfertoinr.data.auth.signup_preconditions.ValidatePhoneUsecase
import com.satwik.transfertoinr.features.account.AccountsScreenViewModel
import com.satwik.transfertoinr.features.auth.login.LoginScreenViewModel
import com.satwik.transfertoinr.features.auth.signup.SignupScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    single<AuthRepository> {
        AuthRepositoryImpl(get())
    }

    single {
        ValidateNameUsecase()
    }
    single {
        ValidateEmailUsecase()
    }
    single {
        ValidatePasswordUsecase()
    }
    single {
        ValidatePhoneUsecase()
    }

    viewModel {
        SignupScreenViewModel(get(), get(), get(), get(), get())
    }

    viewModel {
        LoginScreenViewModel(get())
    }

    viewModel {
        MainActivityViewModel(get())
    }

}
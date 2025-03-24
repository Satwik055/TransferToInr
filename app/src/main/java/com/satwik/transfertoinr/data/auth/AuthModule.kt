package com.satwik.transfertoinr.data.auth

import com.satwik.transfertoinr.core.di.MyApplication
import com.satwik.transfertoinr.core.main.MainActivityViewModel
import com.satwik.transfertoinr.data.auth.login_preconditions.LoginValidateEmailUsecase
import com.satwik.transfertoinr.data.auth.login_preconditions.LoginValidatePasswordUsecase
import com.satwik.transfertoinr.data.auth.signup_preconditions.SignupValidateEmailUsecase
import com.satwik.transfertoinr.data.auth.signup_preconditions.SignupValidateNameUsecase
import com.satwik.transfertoinr.data.auth.signup_preconditions.SignupValidatePasswordUsecase
import com.satwik.transfertoinr.data.auth.signup_preconditions.SignupValidatePhoneUsecase
import com.satwik.transfertoinr.features.auth.login.LoginScreenViewModel
import com.satwik.transfertoinr.features.auth.reset_password.ResetPasswordViewModel
import com.satwik.transfertoinr.features.auth.signup.SignupScreenViewModel
import com.satwik.transfertoinr.features.auth.verify_email.EmailVerificationViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    single<AuthRepository> {
        AuthRepositoryImpl(get())
    }

    single {
        SignupValidateNameUsecase()
    }
    single {
        SignupValidateEmailUsecase()
    }
    single {
        SignupValidatePasswordUsecase()
    }
    single {
        SignupValidatePhoneUsecase()
    }
    single {
        LoginValidateEmailUsecase()
    }
    single {
        LoginValidatePasswordUsecase()
    }


    viewModel {
        SignupScreenViewModel(get(), get(), get(), get(), get())
    }

    viewModel {
        LoginScreenViewModel(get(), get(), get())
    }

    viewModel {
        MainActivityViewModel(get())
    }
    viewModel {
        EmailVerificationViewModel(get())
    }
    viewModel {
        ResetPasswordViewModel(get())
    }
}
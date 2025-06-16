package com.ttf.transfertoinr.data.recipient

import com.ttf.transfertoinr.data.recipient.add_recipient_preconditions.ValidateAccountNumberUsecase
import com.ttf.transfertoinr.data.recipient.add_recipient_preconditions.ValidateBankUsecase
import com.ttf.transfertoinr.data.recipient.add_recipient_preconditions.ValidateIfscUsecase
import com.ttf.transfertoinr.data.recipient.add_recipient_preconditions.ValidateNameUsecase
import com.ttf.transfertoinr.data.recipient.add_recipient_preconditions.ValidateReEnteredAccountNumberUsecase
import com.ttf.transfertoinr.features.recipient.RecipientViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val recipientModule = module {
    single<RecipientRepository> {
        RecipientRepositoryImpl(get())
    }

    single {
        ValidateReEnteredAccountNumberUsecase()
    }
    single {
        ValidateBankUsecase()
    }
    single {
        ValidateNameUsecase()
    }
    single {
        ValidateAccountNumberUsecase()
    }
    single {
        ValidateIfscUsecase()
    }

    viewModel {
        RecipientViewModel(get(), get(), get(), get(), get(), get())
    }
}
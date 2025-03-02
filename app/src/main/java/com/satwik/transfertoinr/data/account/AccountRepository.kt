package com.satwik.transfertoinr.data.account

import com.satwik.transfertoinr.core.model.ExchangeRate
import com.satwik.transfertoinr.core.model.CurrencyType
import com.satwik.transfertoinr.core.model.Profile
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    suspend fun getProfile():Profile
    suspend fun updatePrefferedCurrency(email:String, currency: CurrencyType)
}
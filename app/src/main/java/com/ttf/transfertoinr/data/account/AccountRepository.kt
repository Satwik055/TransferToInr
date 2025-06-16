package com.ttf.transfertoinr.data.account

import androidx.browser.trusted.Token
import com.ttf.transfertoinr.core.model.ExchangeRate
import com.ttf.transfertoinr.core.model.CurrencyType
import com.ttf.transfertoinr.core.model.Profile
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    suspend fun getProfile():Flow<Profile>
    suspend fun getCarousellImageLinks():List<String>
    suspend fun updatePrefferedCurrency(email:String, currency: CurrencyType)
}
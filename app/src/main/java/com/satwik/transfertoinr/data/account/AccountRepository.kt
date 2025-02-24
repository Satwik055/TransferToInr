package com.satwik.transfertoinr.data.account

import com.satwik.transfertoinr.core.model.ExchangeRate
import com.satwik.transfertoinr.core.model.CurrencyType
import com.satwik.transfertoinr.core.model.UserInfo

interface AccountRepository {
    suspend fun getUserInfo():UserInfo
    suspend fun getExchangeRates(currency:CurrencyType):ExchangeRate
}
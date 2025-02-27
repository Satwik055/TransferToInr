package com.satwik.transfertoinr.features.home

import com.satwik.transfertoinr.core.model.ExchangeRate

data class ExchangeRateState(
    val rate:ExchangeRate = ExchangeRate(),
    val isLoading:Boolean = false,
    val error:String = "",
)

package com.ttf.transfertoinr.features.home

import com.ttf.transfertoinr.core.model.ExchangeRate

data class ExchangeRateState(
    val rate:ExchangeRate = ExchangeRate(),
    val isLoading:Boolean = false,
    val error:String = "",
)

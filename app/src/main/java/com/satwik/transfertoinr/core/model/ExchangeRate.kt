package com.satwik.transfertoinr.core.model

import kotlinx.serialization.Serializable


@Serializable
data class ExchangeRate(
    val id:Int = 0,
    val currency_name: CurrencyType = CurrencyType.EUR,
    val tti: Double = 0.0,
    val tti_fees: Double = 0.0,
    val stripe:Double = 0.0,
    val stripe_fees:Double = 0.0,
    val wise:Double = 0.0,
    val wise_fees:Double = 0.0,
    val paypal:Double = 0.0,
    val paypal_fees:Double = 0.0,
    val skrill:Double = 0.0,
    val skrill_fees:Double = 0.0,
    val bank:Double = 0.0,
    val bank_fees:Double = 0.0
)

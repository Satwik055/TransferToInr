package com.ttf.transfertoinr.core.utils

import java.math.BigDecimal
import java.math.RoundingMode

fun roundToTwoDecimalPlaces(number: Double): Double {
    return BigDecimal(number).setScale(2, RoundingMode.HALF_UP).toDouble()
}
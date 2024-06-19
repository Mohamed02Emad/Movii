package com.mo.movie.android.core.utils.extentions

import java.math.BigDecimal
import java.math.RoundingMode

fun Double.round(digits: Int) = BigDecimal(this).setScale(digits, RoundingMode.HALF_UP).toDouble()
fun Float.round(digits: Int) = BigDecimal(this.toDouble()).setScale(digits, RoundingMode.HALF_UP).toFloat()

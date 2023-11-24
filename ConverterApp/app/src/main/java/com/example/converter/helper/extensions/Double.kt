package com.example.converter.helper.extensions

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun Double.toStringWithFormatNumber(): String {
    val format = DecimalFormat("#,###.0000")
    return format.format(this)
}
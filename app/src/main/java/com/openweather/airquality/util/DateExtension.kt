package com.openweather.airquality.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.convertDate(): String? {
    return try {
        val dateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.US)
        val dateParse = Date(this * 1000)
        dateFormat.format(dateParse)
    } catch (e: Exception) {
        e.message
    }
}

fun Long.convertTime(): String? {
    return try {
        val dateFormat = SimpleDateFormat("hh:mm aa", Locale.US)
        val dateParse = Date(this * 1000)
        dateFormat.format(dateParse)
    } catch (e: Exception) {
        e.message
    }
}

fun Long.convertDateAndTime(): String? {
    return try {
        val dateFormat = SimpleDateFormat("dd-MMM-yyyy hh:mm aa", Locale.US)
        val dateParse = Date(this * 1000)
        dateFormat.format(dateParse)
    } catch (e: Exception) {
        e.message
    }
}

package com.openweather.airquality.util

import android.graphics.Color
import com.openweather.airquality.util.AirQualityIndex.*

object CommonUtils {

    fun getAirQualityText(index: Int?): String {
        return when (index) {
            Good.value -> {
                Good.name
            }

            Fair.value -> {
                Fair.name
            }

            Moderate.value -> {
                Moderate.name
            }

            Poor.value -> {
                Poor.name
            }

            VeryPoor.value -> {
                VeryPoor.name
            }

            else -> {
                "NA"
            }
        }
    }

    fun getAirQualityColor(index: Int?): Int {
        return when (index) {
            Good.value -> {
                Color.parseColor("#008450")
            }

            Fair.value -> {
                Color.parseColor("#008450")
            }

            Moderate.value -> {
                Color.parseColor("#efb700")
            }

            Poor.value -> {
                Color.parseColor("#B81d13")
            }

            VeryPoor.value -> {
                Color.parseColor("#B81d13")
            }

            else -> {
                0
            }
        }
    }
}

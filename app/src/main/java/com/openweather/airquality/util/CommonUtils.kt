package com.openweather.airquality.util

import android.graphics.Color

object CommonUtils {

    fun getAirQualityText(index: Int?): String {
        return when (index) {
            AirQualityIndex.Good.value -> {
                AirQualityIndex.Good.name
            }

            AirQualityIndex.Fair.value -> {
                AirQualityIndex.Fair.name
            }

            AirQualityIndex.Moderate.value -> {
                AirQualityIndex.Moderate.name
            }

            AirQualityIndex.Poor.value -> {
                AirQualityIndex.Poor.name
            }

            AirQualityIndex.VeryPoor.value -> {
                AirQualityIndex.VeryPoor.name
            }

            else -> {
                "NA"
            }
        }
    }

    fun getAirQualityColor(index: Int?): Int {
        return when (index) {
            AirQualityIndex.Good.value -> {
                Color.parseColor("#008450")
            }

            AirQualityIndex.Fair.value -> {
                Color.parseColor("#008450")
            }

            AirQualityIndex.Moderate.value -> {
                Color.parseColor("#efb700")
            }

            AirQualityIndex.Poor.value -> {
                Color.parseColor("#B81d13")
            }

            AirQualityIndex.VeryPoor.value -> {
                Color.parseColor("#B81d13")
            }

            else -> {
                0
            }
        }
    }
}

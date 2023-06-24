package com.openweather.airquality.data.response

import com.google.gson.annotations.SerializedName

data class MainData(
    @SerializedName("aqi") var aqi: Int? = null,
)

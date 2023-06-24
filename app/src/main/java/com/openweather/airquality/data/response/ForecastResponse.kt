package com.openweather.airquality.data.response

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("coord")
    val coord: Coord,
    @SerializedName("list")
    val list: List<ListData>,
)

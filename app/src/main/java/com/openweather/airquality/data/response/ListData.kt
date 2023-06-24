package com.openweather.airquality.data.response

import com.google.gson.annotations.SerializedName

data class ListData(
    @SerializedName("main") var main: MainData? = MainData(),
    @SerializedName("components") var components: Components? = Components(),
    @SerializedName("dt") var dt: Int? = null,
)

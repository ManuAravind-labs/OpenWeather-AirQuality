package com.openweather.airquality.domain.model

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

open class BaseEntity {
    open val viewType: Int = -1
}

@Parcelize
data class ForcastEntity(
    var aqi: Int? = null,
    var co: Double? = null,
    var no: Double? = null,
    var no2: Double? = null,
    var o3: Double? = null,
    var so2: Double? = null,
    var pm25: Double? = null,
    var pm10: Double? = null,
    var nh3: Double? = null,
    var dt: Int? = null,
) : BaseEntity(), Parcelable {
    @IgnoredOnParcel
    override val viewType: Int = 2
}

data class HeaderEntity(
    val date: String? = null,
) : BaseEntity() {
    override val viewType: Int = 1
}

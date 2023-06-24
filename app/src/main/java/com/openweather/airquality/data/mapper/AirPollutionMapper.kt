package com.openweather.airquality.data.mapper

import com.openweather.airquality.data.response.ListData
import com.openweather.airquality.domain.common.DomainMapper
import com.openweather.airquality.domain.model.ForcastEntity

class AirPollutionMapper : DomainMapper<ListData, ForcastEntity> {

    /**
     *
     * Mapping to Domain Model from Response Object
     *
     */
    override fun mapToDomainModel(model: ListData): ForcastEntity {
        return ForcastEntity(
            aqi = model.main?.aqi,
            co = model.components?.co,
            no = model.components?.no,
            no2 = model.components?.no2,
            o3 = model.components?.o3,
            so2 = model.components?.so2,
            pm25 = model.components?.pm25,
            pm10 = model.components?.pm10,
            nh3 = model.components?.nh3,
            dt = model.dt,
        )
    }

    fun toDomainList(initial: List<ListData>): List<ForcastEntity> {
        return initial.map { mapToDomainModel(it) }
    }
}

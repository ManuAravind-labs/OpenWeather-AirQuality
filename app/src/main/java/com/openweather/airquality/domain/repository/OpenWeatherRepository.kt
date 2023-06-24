package com.openweather.airquality.domain.repository

import com.openweather.airquality.domain.common.NetworkState
import com.openweather.airquality.domain.model.BaseEntity
import com.openweather.airquality.domain.model.ForcastEntity
import kotlinx.coroutines.flow.Flow

interface OpenWeatherRepository {

    suspend fun forecastAirPollution(): Flow<NetworkState<List<BaseEntity>>>

    suspend fun currentAirPollution(): Flow<NetworkState<List<ForcastEntity>>>
}

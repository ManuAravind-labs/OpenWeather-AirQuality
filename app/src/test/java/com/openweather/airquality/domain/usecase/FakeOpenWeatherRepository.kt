package com.openweather.airquality.domain.usecase

import com.openweather.airquality.domain.common.NetworkState
import com.openweather.airquality.domain.model.BaseEntity
import com.openweather.airquality.domain.model.ForcastEntity
import com.openweather.airquality.domain.model.HeaderEntity
import com.openweather.airquality.domain.repository.OpenWeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeOpenWeatherRepository : OpenWeatherRepository {

    private val headerEntity = HeaderEntity("29-May-2023")
    private val forecastEntity = ForcastEntity(
        aqi = 2, co = 270.37, no = 5.59, no2 = 10.88, o3 = 48.64, so2 = 5.78,
        pm25 = 13.46, pm10 = 16.12, nh3 = 3.55, dt = 1685345494,
    )
    private val forecastEntity1 = ForcastEntity(
        aqi = 3, co = 570.37, no = 5.23, no2 = 5.88, o3 = 18.64, so2 = 9.78,
        pm25 = 3.46, pm10 = 10.12, nh3 = 9.55, dt = 1685345404,
    )
    private val listOfForecastEntity =
        listOf(headerEntity, forecastEntity, forecastEntity1)

    private val listOfCurrentForecastEntity = listOf(forecastEntity)

    override suspend fun forecastAirPollution(): Flow<NetworkState<List<BaseEntity>>> {
        return flow {
            emit(NetworkState.Success(listOfForecastEntity))
        }
    }

    override suspend fun currentAirPollution(): Flow<NetworkState<List<ForcastEntity>>> {
        return flow {
            emit(NetworkState.Success(listOfCurrentForecastEntity))
        }
    }
}

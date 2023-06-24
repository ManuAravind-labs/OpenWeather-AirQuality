package com.openweather.airquality.data.repository

import com.openweather.airquality.BuildConfig
import com.openweather.airquality.data.constants.Constants
import com.openweather.airquality.data.mapper.AirPollutionMapper
import com.openweather.airquality.data.remote.ApiService
import com.openweather.airquality.domain.common.NetworkState
import com.openweather.airquality.domain.model.BaseEntity
import com.openweather.airquality.domain.model.ForcastEntity
import com.openweather.airquality.domain.model.HeaderEntity
import com.openweather.airquality.domain.repository.OpenWeatherRepository
import com.openweather.airquality.util.convertDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OpenWeatherRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: AirPollutionMapper,
) : OpenWeatherRepository {

    override suspend fun forecastAirPollution(): Flow<NetworkState<List<BaseEntity>>> {
        return flow {
            val response = apiService.forecastAirPollution(
                Constants.MUNICH_LATITUDE,
                Constants.MUNICH_LONGITUDE,
                BuildConfig.API_KEY,
            )
            if (response.isSuccessful) {
                val body = response.body()
                body?.list?.let {
                    val data = mapper.toDomainList(it)
                    val ourData = groupForecastByDate(data)
                    emit(NetworkState.Success(ourData))
                }
            } else {
                emit(NetworkState.Error(response.message()))
            }
        }
    }

    /***
     * Grouping forecast list by date
     */
    private fun groupForecastByDate(data: List<ForcastEntity>?): List<BaseEntity> {
        val list = ArrayList<BaseEntity>()
        val grouping = data?.groupBy({ it.dt?.toLong()?.convertDate() }) { it }
        grouping?.forEach { it ->
            list.add(HeaderEntity(it.key))
            it.value.forEach {
                list.add(it)
            }
        }
        return list
    }

    override suspend fun currentAirPollution(): Flow<NetworkState<List<ForcastEntity>>> {
        return flow {
            val response = apiService.currentAirPollution(
                Constants.MUNICH_LATITUDE,
                Constants.MUNICH_LONGITUDE,
                BuildConfig.API_KEY,
            )
            if (response.isSuccessful) {
                val body = response.body()
                body?.list?.let {
                    emit(NetworkState.Success(mapper.toDomainList(it)))
                }
            } else {
                emit(NetworkState.Error(response.message()))
            }
        }
    }
}

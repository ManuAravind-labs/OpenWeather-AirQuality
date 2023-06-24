package com.openweather.airquality.data.remote

import com.openweather.airquality.data.response.ForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("air_pollution/forecast?")
    suspend fun forecastAirPollution(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String,
    ): Response<ForecastResponse>

    @GET("air_pollution?")
    suspend fun currentAirPollution(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String,
    ): Response<ForecastResponse>
}

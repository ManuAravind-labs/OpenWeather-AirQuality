package com.openweather.airquality.domain.usecase

import com.openweather.airquality.domain.model.ForcastEntity
import com.openweather.airquality.domain.model.HeaderEntity
import com.openweather.airquality.domain.repository.OpenWeatherRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetForecastAirPollutionUseCaseTest {
    private lateinit var getForecastAirPollutionUseCase: GetForecastAirPollutionUseCase
    private lateinit var fakeOpenWeatherRepository: OpenWeatherRepository

    @Before
    fun setUp() {
        fakeOpenWeatherRepository = FakeOpenWeatherRepository()
        getForecastAirPollutionUseCase = GetForecastAirPollutionUseCase(fakeOpenWeatherRepository)
    }

    @Test
    fun `Get forecast air-pollution list, correct air-pollution list return`(): Unit = runBlocking {
        val forecast = getForecastAirPollutionUseCase.execute().first().data
        val header = forecast?.get(0) as HeaderEntity
        assertTrue(header.date?.equals("29-May-2023") == true)
        val forecastEntity = forecast[1] as ForcastEntity
        assertTrue(forecastEntity.dt?.equals(1685345494) == true)
        assertTrue(forecastEntity.aqi?.equals(2) == true)
        assertTrue(forecastEntity.no?.equals(5.59) == true)
        assertTrue(forecastEntity.co?.equals(270.37) == true)
        assertTrue(forecastEntity.no2?.equals(10.88) == true)
    }
}

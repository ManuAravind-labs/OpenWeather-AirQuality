package com.openweather.airquality.domain.usecase

import com.openweather.airquality.domain.repository.OpenWeatherRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetCurrentAirPollutionUseCaseTest {
    private lateinit var getCurrentAirPollutionUseCase: GetCurrentAirPollutionUseCase
    private lateinit var fakeOpenWeatherRepository: OpenWeatherRepository

    @Before
    fun setUp() {
        fakeOpenWeatherRepository = FakeOpenWeatherRepository()
        getCurrentAirPollutionUseCase = GetCurrentAirPollutionUseCase(fakeOpenWeatherRepository)
    }

    @Test
    fun `Get current air-pollution list, correct air-poluttion list return`(): Unit = runBlocking {
        val current = getCurrentAirPollutionUseCase.execute().first().data
        assertTrue(current?.get(0)?.aqi?.equals(2) == true)
    }
}

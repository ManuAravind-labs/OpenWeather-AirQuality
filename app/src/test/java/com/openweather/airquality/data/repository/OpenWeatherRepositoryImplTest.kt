package com.openweather.airquality.data.repository

import com.openweather.airquality.data.mapper.AirPollutionMapper
import com.openweather.airquality.data.remote.ApiService
import com.openweather.airquality.domain.model.BaseEntity
import com.openweather.airquality.domain.model.ForcastEntity
import com.openweather.airquality.domain.model.HeaderEntity
import com.openweather.airquality.utils.TestDataGenerator
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class OpenWeatherRepositoryImplTest {
    private lateinit var openWeatherRepository: OpenWeatherRepositoryImpl

    @MockK
    lateinit var apiService: ApiService

    @MockK
    lateinit var mapper: AirPollutionMapper

    private val testModelsGenerator: TestDataGenerator = TestDataGenerator()

    private val forecastEntity = ForcastEntity(
        aqi = 2, co = 270.37, no = 5.59, no2 = 10.88, o3 = 48.64,
        so2 = 5.78, pm25 = 13.46, pm10 = 16.12, nh3 = 3.55, dt = 1685345494,
    )
    private val mockedMappedList = ArrayList<ForcastEntity>()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        mockedMappedList.add(forecastEntity)
        openWeatherRepository = OpenWeatherRepositoryImpl(apiService, mapper)
    }

    @Test
    fun `Given current Air pollution when calling currentAirPollution method then assert expected output successfully`() {
        val expectedOutput = mockedMappedList
        val mockedResponse = testModelsGenerator.getCurrentAirpollutionListResponse()
        // Given
        coEvery {
            apiService.currentAirPollution(
                any(),
                any(),
                any(),
            )
        } returns Response.success(mockedResponse)

        coEvery {
            mapper.toDomainList(
                any(),
            )
        } returns mockedMappedList

        runBlocking {
            // When
            val response = openWeatherRepository.currentAirPollution()
            // Then
            assertEquals(expectedOutput, response.first().data)
        }
    }

    @Test
    fun `Given current Air pollution with emptyList when calling currentAirPollution method then assert expectedoutput successfully`() {
        val mockedMappedList = ArrayList<ForcastEntity>()
        val expectedOutput = mockedMappedList
        val mockedResponse = testModelsGenerator.getCurrentAirpollutionListResponse()

        coEvery {
            apiService.currentAirPollution(
                any(),
                any(),
                any(),
            )
        } returns Response.success(mockedResponse)

        coEvery {
            mapper.toDomainList(
                any(),
            )
        } returns mockedMappedList

        runTest {
            val response = openWeatherRepository.currentAirPollution()
            println(response)
            assertEquals(expectedOutput, response.first().data)
        }
    }

    @Test
    fun `Given forecast Air pollution when calling forecastAirPollution method then assert expectedoutput successfully`() {
        val mockedResponse = testModelsGenerator.getForeCastAirpollutionListResponse()
        // Given
        coEvery {
            apiService.forecastAirPollution(
                any(),
                any(),
                any(),
            )
        } returns Response.success(mockedResponse)

        coEvery {
            mapper.toDomainList(
                any(),
            )
        } returns listOfForecastList

        runBlocking {
            // When
            val response = openWeatherRepository.forecastAirPollution()
            // Then
            assertEquals(expectedForecastList, response.first().data)
        }
    }

    private val headerEntity = HeaderEntity(date = "29-May-2023")

    private val forecastEntity1 = ForcastEntity(
        aqi = 3, co = 240.33, no = 1.01, no2 = 7.11, o3 = 105.86,
        so2 = 2.83, pm25 = 7.7, pm10 = 8.73, nh3 = 2.69, dt = 1685376000,
    )

    private val forecastEntity2 = ForcastEntity(
        aqi = 2, co = 267.03, no = 1.77, no2 = 14.22, o3 = 87.26,
        so2 = 4.05, pm25 = 8.57, pm10 = 9.75, nh3 = 3.74, dt = 1685379600,
    )

    private val forecastEntity3 = ForcastEntity(
        aqi = 2, co = 290.39, no = 1.37, no2 = 21.08, o3 = 70.81,
        so2 = 5.01, pm25 = 9.71, pm10 = 11.08, nh3 = 4.62, dt = 1685383200,
    )

    private val listOfForecastList: List<ForcastEntity> =
        listOf(forecastEntity1, forecastEntity2, forecastEntity3)

    private val expectedForecastList: List<BaseEntity> =
        listOf(headerEntity, forecastEntity1, forecastEntity2, forecastEntity3)
}

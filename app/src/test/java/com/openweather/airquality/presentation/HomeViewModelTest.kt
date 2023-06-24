package com.openweather.airquality.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.openweather.airquality.domain.common.NetworkState
import com.openweather.airquality.domain.model.BaseEntity
import com.openweather.airquality.domain.model.ForcastEntity
import com.openweather.airquality.domain.model.HeaderEntity
import com.openweather.airquality.domain.usecase.GetCurrentAirPollutionUseCase
import com.openweather.airquality.domain.usecase.GetForecastAirPollutionUseCase
import com.openweather.airquality.domain.utils.AppError
import com.openweather.airquality.presentation.list.HomeViewModel
import com.openweather.airquality.presentation.list.viewstate.CurrentAirPollutionUIState
import com.openweather.airquality.presentation.list.viewstate.ForecastAirPollutionUIState
import com.openweather.airquality.util.NetworkHandler
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    // Subject under test
    private lateinit var underTest: HomeViewModel

    // Use a fake UseCase to be injected into the viewModel
    @MockK
    private lateinit var getForecastAirPollutionUseCase: GetForecastAirPollutionUseCase

    @MockK
    private lateinit var getCurrentAirPollutionUsecase: GetCurrentAirPollutionUseCase

    @MockK
    private lateinit var networkHandler: NetworkHandler

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainDispatcherRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        underTest = HomeViewModel(
            getForecastAirPollutionUseCase,
            getCurrentAirPollutionUsecase,
            networkHandler,
        )
        currentAirPollutionList.add(forecastEntity)
    }

    private val currentAirPollutionList = ArrayList<ForcastEntity>()
    private val forecastEntity = ForcastEntity(
        aqi = 2, co = 270.37, no = 5.59, no2 = 10.88, o3 = 48.64, so2 = 5.78,
        pm25 = 13.46, pm10 = 16.12, nh3 = 3.55, dt = 1685345494,
    )

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

    private val expectedForecastList: List<BaseEntity> =
        listOf(headerEntity, forecastEntity1, forecastEntity2, forecastEntity3)

    @Test
    fun `Given network state true and getCurrentAirPollutionUsecase execute method with successlist, when calling currentAirPollutionInfo then assert expected output`() {
        // given
        coEvery { networkHandler.isNetworkAvailable() } returns true
        coEvery { getCurrentAirPollutionUsecase.execute() } returns flow {
            emit(NetworkState.Success(currentAirPollutionList))
        }
        // when
        underTest.currentAirPollutionInfo()
        // then
        runTest {
            val actualOutput = underTest.uiCurrentAirPollutionState.value
            val expectedOutput = CurrentAirPollutionUIState.Success(currentAirPollutionList)
            assertEquals(expectedOutput, actualOutput)
        }
    }

    @Test
    fun `Given network state false when calling currentAirPollutionInfo method then assert network error state as output`() {
        // given
        coEvery { networkHandler.isNetworkAvailable() } returns false

        // when
        underTest.currentAirPollutionInfo()
        // then
        runTest {
            val actualOutput = underTest.uiCurrentAirPollutionState.value
            val expectedOutput = CurrentAirPollutionUIState.Error(AppError.NoInternetFailure)
            assertEquals(expectedOutput, actualOutput)
        }
    }

    @Test
    fun `Given network state true and getForecastAirPollutionUseCase execute method with successlist, when calling forecastAirPollutionInfo then assert expected output`() {
        // given
        coEvery { networkHandler.isNetworkAvailable() } returns true
        coEvery { getForecastAirPollutionUseCase.execute() } returns flow {
            emit(NetworkState.Success(expectedForecastList))
        }
        // when
        underTest.forecastAirPollutionInfo()
        // then
        runTest {
            val actualOutput = underTest.uiForeCastAirPollutionState.value
            val expectedOutput = ForecastAirPollutionUIState.Success(expectedForecastList)
            assertEquals(expectedOutput, actualOutput)
        }
    }

    @Test
    fun `Given network state false when calling forecastAirPollutionInfo method then assert network error state as output`() {
        // given
        coEvery { networkHandler.isNetworkAvailable() } returns false

        // when
        underTest.forecastAirPollutionInfo()
        // then
        runTest {
            val actualOutput = underTest.uiForeCastAirPollutionState.value
            val expectedOutput = ForecastAirPollutionUIState.Error(AppError.NoInternetFailure)
            assertEquals(expectedOutput, actualOutput)
        }
    }

    @Test
    fun `Given network state true and getCurrentAirPollutionUsecase execute method with emptylist, when calling currentAirPollutionInfo then assert expected output`() {
        // given
        coEvery { networkHandler.isNetworkAvailable() } returns true
        coEvery { getCurrentAirPollutionUsecase.execute() } returns flow {
            emit(NetworkState.Success(emptyList()))
        }
        // when
        runTest {
            underTest.currentAirPollutionInfo()
            // then
            assertEquals(
                CurrentAirPollutionUIState.Success(emptyList()),
                underTest.uiCurrentAirPollutionState.value,
            )
        }
    }

    @After
    fun tearDown() {
    }
}

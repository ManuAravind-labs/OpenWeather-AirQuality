package com.openweather.airquality.presentation.list

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openweather.airquality.domain.common.NetworkState
import com.openweather.airquality.domain.usecase.GetCurrentAirPollutionUseCase
import com.openweather.airquality.domain.usecase.GetForecastAirPollutionUseCase
import com.openweather.airquality.domain.utils.AppError
import com.openweather.airquality.presentation.list.viewstate.CurrentAirPollutionUIState
import com.openweather.airquality.presentation.list.viewstate.ForecastAirPollutionUIState
import com.openweather.airquality.util.NetworkHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getForecastAirPollutionUseCase: GetForecastAirPollutionUseCase,
    private val getCurrentAirPollutionUseCase: GetCurrentAirPollutionUseCase,
    private val networkHandler: NetworkHandler,
) : ViewModel() {

    private val _uiForeCastAirPollutionState = MutableStateFlow<ForecastAirPollutionUIState>(
        ForecastAirPollutionUIState.Init,
    )
    val uiForeCastAirPollutionState: StateFlow<ForecastAirPollutionUIState> =
        _uiForeCastAirPollutionState.asStateFlow()

    private val _uiCurrentAirPollutionState = MutableStateFlow<CurrentAirPollutionUIState>(
        CurrentAirPollutionUIState.Init,
    )
    val uiCurrentAirPollutionState: StateFlow<CurrentAirPollutionUIState> =
        _uiCurrentAirPollutionState.asStateFlow()

    init {
        forecastAirPollutionInfo()
        currentAirPollutionInfo()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun currentAirPollutionInfo() {
        viewModelScope.launch {
            if (networkHandler.isNetworkAvailable()) {
                getCurrentAirPollutionUseCase.execute()
                    .onStart {
                        _uiCurrentAirPollutionState.emit(CurrentAirPollutionUIState.IsLoading(true))
                    }
                    .catch {
                        _uiCurrentAirPollutionState.emit(CurrentAirPollutionUIState.IsLoading(false))
                        _uiCurrentAirPollutionState.emit(CurrentAirPollutionUIState.Error(AppError.GeneralFailure))
                    }
                    .collect {
                        _uiCurrentAirPollutionState.emit(CurrentAirPollutionUIState.IsLoading(false))
                        when (it) {
                            is NetworkState.Success -> {
                                _uiCurrentAirPollutionState.emit(
                                    CurrentAirPollutionUIState.Success(
                                        it.data,
                                    ),
                                )
                            }

                            is NetworkState.Error -> {
                                _uiCurrentAirPollutionState.emit(
                                    CurrentAirPollutionUIState.Error(
                                        AppError.ServerFailure(it.message),
                                    ),
                                )
                            }
                        }
                    }
            } else {
                _uiCurrentAirPollutionState.emit(CurrentAirPollutionUIState.Error(AppError.NoInternetFailure))
            }
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun forecastAirPollutionInfo() {
        viewModelScope.launch {
            if (networkHandler.isNetworkAvailable()) {
                getForecastAirPollutionUseCase.execute()
                    .onStart {
                        _uiForeCastAirPollutionState.emit(ForecastAirPollutionUIState.IsLoading(true))
                    }
                    .catch {
                        _uiForeCastAirPollutionState.emit(
                            ForecastAirPollutionUIState.IsLoading(
                                false,
                            ),
                        )
                        _uiForeCastAirPollutionState.emit(ForecastAirPollutionUIState.Error(AppError.GeneralFailure))
                    }
                    .collect {
                        _uiForeCastAirPollutionState.emit(
                            ForecastAirPollutionUIState.IsLoading(
                                false,
                            ),
                        )
                        when (it) {
                            is NetworkState.Success -> {
                                _uiForeCastAirPollutionState.emit(
                                    ForecastAirPollutionUIState.Success(
                                        it.data,
                                    ),
                                )
                            }

                            is NetworkState.Error -> {
                                _uiForeCastAirPollutionState.emit(
                                    ForecastAirPollutionUIState.Error(
                                        AppError.ServerFailure(it.message),
                                    ),
                                )
                            }
                        }
                    }
            } else {
                _uiForeCastAirPollutionState.emit(
                    ForecastAirPollutionUIState.Error(
                        AppError.NoInternetFailure,
                    ),
                )
            }
        }
    }
}

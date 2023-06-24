package com.openweather.airquality.presentation.list.viewstate

import com.openweather.airquality.domain.model.BaseEntity
import com.openweather.airquality.domain.utils.AppError

sealed class ForecastAirPollutionUIState {
    object Init : ForecastAirPollutionUIState()
    data class IsLoading(val isLoading: Boolean) : ForecastAirPollutionUIState()
    data class Success(val forecast: List<BaseEntity>?) : ForecastAirPollutionUIState()
    data class Error(val error: AppError) : ForecastAirPollutionUIState()
}



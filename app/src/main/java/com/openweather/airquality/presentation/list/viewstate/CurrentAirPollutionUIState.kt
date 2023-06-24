package com.openweather.airquality.presentation.list.viewstate

import com.openweather.airquality.domain.model.ForcastEntity
import com.openweather.airquality.domain.utils.AppError

sealed class CurrentAirPollutionUIState {
    object Init : CurrentAirPollutionUIState()
    data class IsLoading(val isLoading: Boolean) : CurrentAirPollutionUIState()
    data class Success(val current: List<ForcastEntity>?) : CurrentAirPollutionUIState()
    data class Error(val error: AppError) : CurrentAirPollutionUIState()
}

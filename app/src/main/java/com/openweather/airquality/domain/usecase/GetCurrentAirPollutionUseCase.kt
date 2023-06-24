package com.openweather.airquality.domain.usecase

import com.openweather.airquality.domain.common.NetworkState
import com.openweather.airquality.domain.model.ForcastEntity
import com.openweather.airquality.domain.repository.OpenWeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentAirPollutionUseCase @Inject constructor(
    private val appRepository: OpenWeatherRepository,
) {
    suspend fun execute(): Flow<NetworkState<List<ForcastEntity>>> {
        return appRepository.currentAirPollution()
    }
}

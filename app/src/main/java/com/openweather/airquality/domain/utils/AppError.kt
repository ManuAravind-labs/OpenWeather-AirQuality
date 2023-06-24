package com.openweather.airquality.domain.utils

sealed class AppError {
    object GeneralFailure : AppError()
    class ServerFailure(var message: String? = null) : AppError()
    object NoInternetFailure : AppError()
}

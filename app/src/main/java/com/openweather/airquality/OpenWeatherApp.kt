package com.openweather.airquality

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class OpenWeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}

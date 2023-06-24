package com.openweather.airquality.utils

import com.google.gson.Gson
import com.openweather.airquality.data.response.Coord
import com.openweather.airquality.data.response.ForecastResponse
import java.io.InputStreamReader

class TestDataGenerator {

    private var forecastList: ForecastResponse? = null

    fun getCurrentAirpollutionListResponse(): ForecastResponse? {
        val data = getJson("current_air_pollution.json")
        val gson = Gson()
        forecastList = gson.fromJson(data, ForecastResponse::class.java)
        return forecastList
    }

    fun getForeCastAirpollutionListResponse(): ForecastResponse? {
        val data = getJson("forecast_air_pollution.json")
        val gson = Gson()
        forecastList = gson.fromJson(data, ForecastResponse::class.java)
        return forecastList
    }

    fun getForeCastEmptyResponse(): ForecastResponse? {
        return ForecastResponse(Coord(), emptyList())
    }

    /**
     * Read json from resource
     */
    private fun getJson(path: String): String {
        val reader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(path))
        val content = reader.readText()
        reader.close()
        return content
    }
}


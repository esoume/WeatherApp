package com.esoume.coding.weatherapp.domain.repository.forecast

import com.esoume.coding.weatherapp.domain.util.Resource
import com.esoume.coding.weatherapp.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
}
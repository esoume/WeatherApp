package com.esoume.coding.weatherapp.data.remote.forecast.dto

import com.squareup.moshi.Json

data class WeatherDto(
    @field:Json(name= "latiture")
    val latiture: Double,
    @field:Json(name= "longitude")
    val longitude: Double,
    @field:Json(name= "current_weather")
    val currentWeather: CurrentWeatherDto,
    @field:Json(name = "hourly")
    val weatherData: WeatherDataDto
)

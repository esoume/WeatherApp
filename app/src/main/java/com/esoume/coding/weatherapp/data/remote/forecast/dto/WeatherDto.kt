package com.esoume.coding.weatherapp.data.remote.forecast.dto

import com.squareup.moshi.Json

data class WeatherDto(
    @field:Json(name= "current_weather")
    val currentWeather: CurrentWeatherDto,
    @field:Json(name = "hourly")
    val weatherData: WeatherDataDto
)

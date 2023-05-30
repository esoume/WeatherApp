package com.esoume.coding.weatherapp.domain.weather

import java.time.LocalDateTime

data class CurrentWeatherInfo(
    val city: String = "",
    val temperature: Double,
    val windSpeed: Double,
    val windDirection: Double,
    val weatherType: WeatherType,
    val is_day: Int,
    val time: LocalDateTime,
)

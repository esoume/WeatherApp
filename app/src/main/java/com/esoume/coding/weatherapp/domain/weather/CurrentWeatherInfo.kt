package com.esoume.coding.weatherapp.domain.weather

import java.time.LocalDateTime

data class CurrentWeatherInfo(
    val temperature: Double,
    val windspeed: Double,
    val winddirection: Double,
    val weatherType: WeatherType,
    val is_day: Int,
    val time: LocalDateTime,
)

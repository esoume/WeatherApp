package com.esoume.coding.weatherapp.presentation.state.widget

import kotlinx.serialization.Serializable

@Serializable
data class WeatherWidgetInfo(
    val city: String = "",
    val temperature: Double = 0.0,
    val windSpeed: Double = 0.0,
    val windDirection: Double = 0.0,
    val weatherCode: Int = 0,
    val is_day: Int = 1,
    val time: String = "",
)

package com.esoume.coding.weatherapp.presentation.state.forecast

import com.esoume.coding.weatherapp.domain.weather.WeatherInfo

data class WeatherState(
    val city: String ="",
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
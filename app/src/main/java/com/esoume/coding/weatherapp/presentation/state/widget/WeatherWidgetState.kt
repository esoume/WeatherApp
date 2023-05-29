package com.esoume.coding.weatherapp.presentation.state.widget

data class WeatherWidgetState(
    val data: WeatherWidgetInfo,
    val isLoading: Boolean = false,
    val error: String = ""
)

package com.esoume.coding.weatherapp.domain.weather

data class WeatherInfo(
    val city: String ="",
    val latiture: Double = 0.0,
    val longitude: Double = 0.0,
    val weatherDataPerDay: Map<Int, List<WeatherData>>,
    val currentWeatherData: WeatherData?,
    val currentWeather: CurrentWeatherInfo
)
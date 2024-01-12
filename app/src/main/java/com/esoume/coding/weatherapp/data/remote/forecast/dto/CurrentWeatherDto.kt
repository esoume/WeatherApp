package com.esoume.coding.weatherapp.data.remote.forecast.dto

import com.squareup.moshi.Json

data class CurrentWeatherDto(
    @field:Json(name = "temperature")
    val temperature: Double,

    @field:Json(name = "windspeed")
    val windspeed: Double,

    @field:Json(name = "winddirection")
    val winddirection: Double,

    @field:Json(name = "weathercode")
    val weathercode: Int,

    @field:Json(name = "is_day")
    val isDay: Int,

    @field:Json(name = "time")
    val time: String,
)

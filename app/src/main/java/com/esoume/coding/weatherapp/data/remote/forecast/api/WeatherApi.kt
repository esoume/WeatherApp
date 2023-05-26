package com.esoume.coding.weatherapp.data.remote.forecast.api

import com.esoume.coding.weatherapp.data.remote.forecast.dto.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("v1/forecast?current_weather=true&hourly=temperature_2m,weathercode,relativehumidity_2m,windspeed_10m,pressure_msl")
    suspend fun getWeatherData(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double
    ): WeatherDto
}
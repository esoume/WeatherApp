package com.esoume.coding.weatherapp.data.repository.forecast

import com.esoume.coding.weatherapp.data.mappers.toWeatherInfo
import com.esoume.coding.weatherapp.data.remote.forecast.api.WeatherApi
import com.esoume.coding.weatherapp.domain.repository.WeatherRepository
import com.esoume.coding.weatherapp.domain.util.Resource
import com.esoume.coding.weatherapp.domain.weather.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
): WeatherRepository {

    override suspend fun getWeatherData(
        lat: Double,
        long: Double
    ): Resource<WeatherInfo>{
        return try{
            Resource.Success(
                data = api.getWeatherData(
                    lat = lat,
                    long = long
                ).toWeatherInfo()
            )
        }catch (e: Exception){
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}
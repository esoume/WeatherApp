package com.esoume.coding.weatherapp.data.mappers

import com.esoume.coding.weatherapp.data.remote.forecast.dto.CurrentWeatherDto
import com.esoume.coding.weatherapp.data.remote.forecast.dto.WeatherDataDto
import com.esoume.coding.weatherapp.data.remote.forecast.dto.WeatherDto
import com.esoume.coding.weatherapp.domain.weather.CurrentWeatherInfo
import com.esoume.coding.weatherapp.domain.weather.WeatherData
import com.esoume.coding.weatherapp.domain.weather.WeatherInfo
import com.esoume.coding.weatherapp.domain.weather.WeatherType
import com.esoume.coding.weatherapp.presentation.state.widget.WeatherWidgetInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData
)

fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, time ->
        val temperature = temperatures[index]
        val weatherCode = weatherCodes[index]
        val windSpeed = windSpeeds[index]
        val pressure = pressures[index]
        val humidity = humidities[index]

        val indexedWeatherData = IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature,
                pressure = pressure,
                windSpeed = windSpeed,
                humidity = humidity,
                weatherType = WeatherType.fromWMO(weatherCode)
            )
        )
        indexedWeatherData
    }.groupBy {
        it.index / 24
    }.mapValues { it ->
        it.value.map { it.data }
    }
}

fun CurrentWeatherDto.toCurrentWeatherInfo(): CurrentWeatherInfo {
    return CurrentWeatherInfo(
        temperature = temperature,
        windSpeed = windspeed,
        windDirection = winddirection,
        weatherType = WeatherType.fromWMO(weathercode),
        isDay = isDay,
        time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME)
    )
}

fun CurrentWeatherInfo.toWeatherWidgetInfo(): WeatherWidgetInfo{
    return WeatherWidgetInfo(
        city = city,
        temperature = temperature,
        windSpeed = windSpeed,
        windDirection = windDirection,
        time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")),
        isDay = isDay,
        weatherCode = WeatherType.toWMO(weatherType)
    )
}

fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val weatherDataMap = weatherData.toWeatherDataMap()
    val now = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = if(now.minute < 30) now.hour else now.hour + 1
        it.time.hour == hour
    }

    return WeatherInfo(
        latiture = latiture,
        longitude = longitude,
        weatherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData,
        currentWeather = currentWeather.toCurrentWeatherInfo()
    )
}
package com.esoume.coding.weatherapp.presentation.viewmodels.forecast

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esoume.coding.weatherapp.domain.location.LocationTracker
import com.esoume.coding.weatherapp.domain.repository.forecast.WeatherRepository
import com.esoume.coding.weatherapp.domain.util.Resource
import com.esoume.coding.weatherapp.presentation.state.forecast.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class WeatherViewModel @Inject constructor(
    private val context: Context,
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker
): ViewModel() {

    // Expose screen Weather UI state and initialize to loading for splashscreen
    private val _uiState = MutableStateFlow(WeatherState(isLoading = true))
    // Backing property to avoid state updates from other classes
    val uiState: StateFlow<WeatherState> = _uiState.asStateFlow()

    //
    private val _isRefreshing = MutableStateFlow(false)
    val isRefresh = _isRefreshing.asStateFlow()

    fun refresh(){
        viewModelScope.launch {
            _isRefreshing.update { true }
            async(IO) {
                getCurrentLocationWeather()
            }.await()
            // Set _isRefreshing to false to indicate the refresh is complete
            _isRefreshing.emit(false)
        }
    }

    fun loadWeatherInfo() {
        viewModelScope.launch {
            getCurrentLocationWeather()
        }
    }

    private suspend fun getCurrentLocationWeather(){
        _uiState.update {currentState ->
            currentState.copy(
                isLoading = true,
                error = null
            )
        }
        locationTracker.getCurrentLocation()?.let { location ->

            val city:String = getCityName(
                context = context,
                latitude = location.latitude,
                longitude = location.longitude
            )
            when (val result = repository.getWeatherData(
                location.latitude,
                location.longitude)
            ) {
                is Resource.Success -> {

                    _uiState.update { currentState ->
                        currentState.copy(
                            city = city,
                            weatherInfo = result.data,
                            isLoading = false,
                            error = null
                        )

                    }
                }
                is Resource.Error -> {
                    _uiState.update { weatherState ->
                        weatherState.copy(
                            city = city,
                            weatherInfo = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }
        }
    }

    private fun getCityName(
        context: Context,
        latitude: Double,
        longitude: Double
    ):String {
        val geoCoder = Geocoder(context, Locale.getDefault())
        val addresses = geoCoder.getFromLocation(latitude, longitude, 1)
        addresses?.size.let {
            val locality = addresses!![0].locality
            val getAddressLine0 = addresses[0].getAddressLine(0)
            if (getAddressLine0 != null) {
                val strs = getAddressLine0.split(",").toTypedArray()
                if (strs.count() == 1) {
                    if (locality != null && locality != "") {
                        return "$locality, ${strs[0]}"
                    }
                    return strs[0]
                }
                for (str in strs) {
                    var tempStr = str.replace("postalCode", "")
                    if (tempStr != str) {
                        tempStr = tempStr.trim()
                        if (locality != null && locality != "" && locality != tempStr) {
                            return "$locality, $tempStr"
                        }
                        return tempStr
                    }
                }
            }
            if (locality != null) {
                return locality
            }
            return ""
        }
    }
}
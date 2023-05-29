package com.esoume.coding.weatherapp.presentation.viewmodels.forecast

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
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
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

    suspend fun getCurrentLocationWeather(){
        _uiState.update {currentState ->
            currentState.copy(
                isLoading = true,
                error = null
            )
        }
        locationTracker.getCurrentLocation()?.let { location ->
            val result = repository.getWeatherData(location.latitude, location.longitude)

            when (result) {
                is Resource.Success -> {
                    _uiState.update { currentState ->
                        currentState.copy(
                            weatherInfo = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                }
                is Resource.Error -> {
                    _uiState.update { weatherState ->
                        weatherState.copy(
                            weatherInfo = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }
        }
    }
}
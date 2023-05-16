package com.esoume.coding.weatherapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esoume.coding.weatherapp.domain.location.LocationTracker
import com.esoume.coding.weatherapp.domain.repository.WeatherRepository
import com.esoume.coding.weatherapp.domain.util.Resource
import com.esoume.coding.weatherapp.presentation.state.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun loadWeatherInfo() {
        viewModelScope.launch {
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
}
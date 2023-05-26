package com.esoume.coding.weatherapp.presentation.viewmodels.splash

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esoume.coding.weatherapp.data.repository.onboarding.RepositoryOnboardingImpl
import com.esoume.coding.weatherapp.presentation.navigation.Screen
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashScreenViewModel @Inject constructor(
    private val repository: RepositoryOnboardingImpl
): ViewModel(){

    private val _startDestination: MutableState<String> = mutableStateOf(Screen.Welcome.route)
    val startDestination: State<String> = _startDestination

    init{
        start()
    }

    private fun start() {
        viewModelScope.launch {
            repository.readOnboardingState().collect { completed ->
                if (completed) {
                    _startDestination.value = Screen.Home.route
                } else {
                    _startDestination.value = Screen.Welcome.route
                }
            }
        }
    }

    fun getDestination(): String{
        var destination: String = Screen.Welcome.route
        viewModelScope.launch {
             repository.readOnboardingState().collect { completed ->
                if (completed) {
                    destination = Screen.Home.route
                }
            }
        }
        return destination
    }
}
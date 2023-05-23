package com.esoume.coding.weatherapp.presentation.navigation

sealed class Screen(val route: String){
    object Welcome: Screen(route = "welcome_route")
    object Home: Screen(route = "home_route")
}

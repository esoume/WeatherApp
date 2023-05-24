package com.esoume.coding.weatherapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.esoume.coding.weatherapp.presentation.screen.onboarding.HomePage
import com.esoume.coding.weatherapp.presentation.screen.onboarding.WelcomePage
import com.esoume.coding.weatherapp.presentation.state.WeatherState

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String,
    weatherUiState: WeatherState,
    refreshing: Boolean,
    splashScreen : SplashScreen,
    onRefresh: () -> Unit
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(route = Screen.Welcome.route) {
            WelcomePage(navController = navController)
        }

        composable(route = Screen.Home.route) {
            HomePage(
                navController = navController,
                weatherUiState = weatherUiState,
                refreshing = refreshing,
                splashScreen = splashScreen,
                onRefresh = { onRefresh() }
            )
        }
    }

}
package com.esoume.coding.weatherapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.esoume.coding.weatherapp.presentation.screen.onboarding.HomePage
import com.esoume.coding.weatherapp.presentation.screen.onboarding.WelcomePage

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(route = Screen.Welcome.route) {
            WelcomePage(navController = navController)
        }

        composable(route = Screen.Home.route) {
            HomePage(navController = navController)
        }
    }

}
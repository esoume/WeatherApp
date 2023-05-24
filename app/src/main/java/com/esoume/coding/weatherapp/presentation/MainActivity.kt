package com.esoume.coding.weatherapp.presentation

import android.Manifest
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.esoume.coding.weatherapp.presentation.navigation.SetupNavGraph
import com.esoume.coding.weatherapp.presentation.theme.WeatherAppTheme
import com.esoume.coding.weatherapp.presentation.viewmodels.forecast.WeatherViewModel
import com.esoume.coding.weatherapp.presentation.viewmodels.splash.SplashScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()
    private val splashScreenViewModel: SplashScreenViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            permissions.forEach { (key, granted) ->
                println("MainActivity : permission key = $key value = $granted ")
                if (!granted)
                    finish()
            }
            viewModel.loadWeatherInfo()
        }
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
        setContent {
            val action: String? = intent?.action
            val data: Uri? = intent?.data;
            println("MainActivity : action = $action \n uri = ${data?.toString()}")

            WeatherAppTheme {
                val navController = rememberNavController()
                splashScreenViewModel.start()
                // start onBoarding Page
                SetupNavGraph(
                    navController = navController,
                    splashScreen = splashScreen,
                    startDestination = splashScreenViewModel.startDestination.value,
                    weatherUiState = viewModel.uiState.collectAsState().value,
                    refreshing = viewModel.isRefresh.collectAsState().value,
                    onRefresh = {viewModel::refresh}
                )
            }
        }
    }
}
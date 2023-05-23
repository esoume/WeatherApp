package com.esoume.coding.weatherapp.presentation

import android.Manifest
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.esoume.coding.weatherapp.presentation.navigation.SetupNavGraph
import com.esoume.coding.weatherapp.presentation.screen.WeatherCard
import com.esoume.coding.weatherapp.presentation.screen.WeatherForecast
import com.esoume.coding.weatherapp.presentation.theme.DarkBlue
import com.esoume.coding.weatherapp.presentation.theme.DeepBlue
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
                if (!granted) finish()
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
                val screen = splashScreenViewModel.startDestination.value
                val navController = rememberNavController()
                // start onBoarding Page
                SetupNavGraph(navController = navController, startDestination = screen)

                val weatherUiState = viewModel.uiState.collectAsState()
                val refreshing = viewModel.isRefresh.collectAsState()
                val pullRefreshState = rememberPullRefreshState(
                    refreshing = refreshing.value,
                    onRefresh = { viewModel.refresh() }
                )

                splashScreen.setKeepOnScreenCondition {
                    weatherUiState.value.isLoading
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .pullRefresh(pullRefreshState)
                        .verticalScroll(rememberScrollState())
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(DarkBlue)
                    ) {

                        WeatherCard(
                            state = weatherUiState.value,
                            backgroundColor = DeepBlue
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        WeatherForecast(state = weatherUiState.value)
                    }
                    PullRefreshIndicator(
                        refreshing = refreshing.value,
                        state = pullRefreshState,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )

                    if (weatherUiState.value.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    weatherUiState.value.error?.let { error ->
                        Text(
                            text = error,
                            color = Color.Red,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}
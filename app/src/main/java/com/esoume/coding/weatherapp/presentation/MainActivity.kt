package com.esoume.coding.weatherapp.presentation

import android.Manifest
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.ui.platform.LocalContext
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.navigation.compose.rememberNavController
import com.esoume.coding.weatherapp.data.mappers.toWeatherWidgetInfo
import com.esoume.coding.weatherapp.presentation.navigation.SetupNavGraph
import com.esoume.coding.weatherapp.presentation.screen.appwidget.WeatherAppWidget
import com.esoume.coding.weatherapp.presentation.state.widget.WeatherInfoStateDefinition
import com.esoume.coding.weatherapp.presentation.state.widget.WeatherWidgetInfo
import com.esoume.coding.weatherapp.presentation.theme.WeatherAppTheme
import com.esoume.coding.weatherapp.presentation.viewmodels.forecast.WeatherViewModel
import com.esoume.coding.weatherapp.presentation.viewmodels.splash.SplashScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var splashScreenViewModel: SplashScreenViewModel

    private val viewModel: WeatherViewModel by viewModels()

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

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

            WeatherAppTheme {
                val navController = rememberNavController()
                val state = viewModel.uiState.collectAsState().value
                val city = state.city
                val stateWidget: WeatherWidgetInfo =
                    state.weatherInfo?.currentWeather?.copy(city = city)?.let {
                        it.toWeatherWidgetInfo()
                    } ?: WeatherWidgetInfo()

                StartAppWidget(state = stateWidget)
                SetupNavGraph(
                    navController = navController,
                    splashScreen = splashScreen,
                    startDestination = splashScreenViewModel.getDestination(),
                    weatherUiState = viewModel.uiState.collectAsState().value,
                    refreshing = viewModel.isRefresh.collectAsState().value,
                    onRefresh = { viewModel::refresh }
                )
            }
        }
    }
}

@Composable
private fun StartAppWidget(
    context: Context = LocalContext.current,
    state: WeatherWidgetInfo
) {

    LaunchedEffect(key1 = state) {
        MainScope().launch {
            val manager = GlanceAppWidgetManager(context = context)
            val glanceIds = manager.getGlanceIds(WeatherAppWidget::class.java)
            glanceIds.forEach { glanceId ->
                updateAppWidgetState(
                    context = context,
                    glanceId = glanceId,
                    definition = WeatherInfoStateDefinition,
                    updateState = { state }
                )
            }
            WeatherAppWidget.updateAll(context)
        }
    }
}
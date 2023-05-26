package com.esoume.coding.weatherapp.presentation.screen.appwidget

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.*
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.layout.*
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.esoume.coding.weatherapp.R
import com.esoume.coding.weatherapp.presentation.screen.WeatherDataDisplay
import com.esoume.coding.weatherapp.presentation.state.WeatherState
import com.esoume.coding.weatherapp.presentation.theme.DarkBlue
import com.esoume.coding.weatherapp.presentation.viewmodels.forecast.WeatherViewModel
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

object WeatherAppWidget : GlanceAppWidget() {

    @Composable
    override fun Content() {

        val viewModel: WeatherViewModel = hiltViewModel()
        val state: WeatherState = viewModel.uiState.collectAsState().value

        state.weatherInfo?.currentWeather?.let { currentWeatherInfo ->
            Box(
                modifier = GlanceModifier.background(DarkBlue).padding(16.dp)
            ) {
                Column(
                    modifier = GlanceModifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = GlanceModifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Today ${
                                currentWeatherInfo.time.format(
                                    DateTimeFormatter.ofPattern("HH:mm")
                                )
                            }",
                            //modifier = GlanceModifier.(Alignment.End),
                            style = TextStyle(
                                fontWeight = FontWeight.Medium,
                                color = ColorProvider(Color.White),
                                fontSize = 26.sp
                            )
                        )

                        Button(
                            text = "Open",
                            onClick = actionRunCallback(WeatherActionCallback::class.java)
                        )
                    }

                    Spacer(modifier = GlanceModifier.height(16.dp))
                    Image(
                        provider = ImageProvider(currentWeatherInfo.weatherType.iconRes),
                        contentDescription = null,
                        modifier = GlanceModifier.width(200.dp)
                    )
                    Spacer(modifier = GlanceModifier.height(16.dp))
                    Text(
                        text = "${currentWeatherInfo.temperature}°C",
                        style = TextStyle(
                            fontWeight = FontWeight.Medium,
                            color = ColorProvider(Color.White),
                            fontSize = 50.sp
                        )
                    )
                    Spacer(modifier = GlanceModifier.height(16.dp))
                    Text(
                        text = currentWeatherInfo.weatherType.weatherDesc,
                        style = TextStyle(
                            fontWeight = FontWeight.Medium,
                            color = ColorProvider(Color.White),
                            fontSize = 20.sp
                        )
                    )
                    Spacer(modifier = GlanceModifier.height(32.dp))
                    Row(
                        modifier = GlanceModifier.fillMaxWidth().padding(5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        state.weatherInfo.currentWeatherData?.let { currentWeatherData ->
                            WeatherDataDisplay(
                                value = currentWeatherData.pressure.roundToInt(),
                                unit = "hpa",
                                icon = ImageVector.vectorResource(id = R.drawable.ic_pressure),
                                iconTint = Color.White
                            )
                            WeatherDataDisplay(
                                value = currentWeatherData.humidity.roundToInt(),
                                unit = "%",
                                icon = ImageVector.vectorResource(id = R.drawable.ic_drop),
                                iconTint = Color.White
                            )
                            WeatherDataDisplay(
                                value = currentWeatherData.windSpeed.roundToInt(),
                                unit = "km/h",
                                icon = ImageVector.vectorResource(id = R.drawable.ic_wind),
                                iconTint = Color.White
                            )
                        }
                    }
                }
            }
        }

    }
}
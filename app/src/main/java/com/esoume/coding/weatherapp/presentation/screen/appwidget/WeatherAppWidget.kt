package com.esoume.coding.weatherapp.presentation.screen.appwidget

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.*
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.layout.*
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.esoume.coding.weatherapp.domain.weather.WeatherType
import com.esoume.coding.weatherapp.presentation.MainActivity
import com.esoume.coding.weatherapp.presentation.state.widget.WeatherInfoStateDefinition
import com.esoume.coding.weatherapp.presentation.state.widget.WeatherWidgetInfo

object WeatherAppWidget : GlanceAppWidget() {

    override val stateDefinition = WeatherInfoStateDefinition

    @Composable
    override fun Content() {

        val actionStartApp = actionStartActivity(MainActivity::class.java)
        val actionRefresh = actionRunCallback(WeatherActionCallback::class.java)
        val state = currentState<WeatherWidgetInfo>()

        state.let { weatherWidgetInfo ->
            Box(
                modifier = GlanceModifier.background(Color.Transparent).padding(5.dp)
            ) {
                Column(
                    modifier = GlanceModifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = GlanceModifier.fillMaxWidth()
                    ) {
                        Text(
                            text = weatherWidgetInfo.city,
                            style = TextStyle(
                                fontWeight = FontWeight.Medium,
                                color = ColorProvider(Color.White),
                                fontSize = 10.sp
                            )
                        )
                        Spacer(modifier = GlanceModifier.width(10.dp))
                        Text(
                            text = "Today ${weatherWidgetInfo.time}",
                            style = TextStyle(
                                fontWeight = FontWeight.Medium,
                                color = ColorProvider(Color.White),
                                fontSize = 10.sp
                            )
                        )
                    }
                    Spacer(modifier = GlanceModifier.height(5.dp))
                    Text(
                        text = "${weatherWidgetInfo.temperature}°C",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            color = ColorProvider(Color.White),
                            fontSize = 20.sp
                        ),
                        maxLines = 1,
                        modifier = GlanceModifier
                            .clickable(onClick = actionStartApp)
                    )
                    Spacer(modifier = GlanceModifier.height(5.dp))
                    Image(
                        provider = ImageProvider(WeatherType.fromWMO(weatherWidgetInfo.weatherCode).iconRes),
                        contentDescription = null,
                        modifier = GlanceModifier
                            .width(50.dp)
                            .height(50.dp)
                            .clickable(onClick = actionStartApp)
                    )
                    Spacer(modifier = GlanceModifier.height(5.dp))
                    Button(
                        text = "Refresh",
                        modifier = GlanceModifier
                            .wrapContentWidth()
                            .wrapContentHeight(),
                        onClick = actionRefresh
                    )
                }
            }

        }
    }
}
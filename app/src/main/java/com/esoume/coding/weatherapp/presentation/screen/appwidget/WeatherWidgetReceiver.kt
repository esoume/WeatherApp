package com.esoume.coding.weatherapp.presentation.screen.appwidget

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

class WeatherWidgetReceiver: GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = WeatherAppWidget
}
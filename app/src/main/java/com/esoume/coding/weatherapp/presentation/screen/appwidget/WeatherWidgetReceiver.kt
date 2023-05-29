package com.esoume.coding.weatherapp.presentation.screen.appwidget

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

class WeatherWidgetReceiver: GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = WeatherAppWidget

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        WeatherLocationWorker.enqueue(context)
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        WeatherLocationWorker.cancel(context)
    }
}
package com.esoume.coding.weatherapp.presentation.screen.appwidget

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback

class WeatherActionCallback : ActionCallback {

    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        WeatherAppWidget.update(context, glanceId)
    }
}
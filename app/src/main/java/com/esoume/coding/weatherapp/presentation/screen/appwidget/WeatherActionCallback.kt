package com.esoume.coding.weatherapp.presentation.screen.appwidget

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.work.*
import com.esoume.coding.weatherapp.data.mappers.toWeatherWidgetInfo
import com.esoume.coding.weatherapp.domain.location.LocationTracker
import com.esoume.coding.weatherapp.domain.repository.forecast.WeatherRepository
import com.esoume.coding.weatherapp.domain.util.Resource
import com.esoume.coding.weatherapp.presentation.state.widget.WeatherInfoStateDefinition
import com.esoume.coding.weatherapp.presentation.state.widget.WeatherWidgetInfo
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WeatherActionCallback : ActionCallback {

    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        WeatherLocationWorker.enqueue(
            context = context,
            force = true
        )
    }
}

class WeatherLocationWorker(
    private val context: Context,
    workerParameters: WorkerParameters
): CoroutineWorker(context, workerParameters) {

    @Inject
    lateinit var repository: WeatherRepository

    @Inject
    lateinit var locationTracker: LocationTracker

    override suspend fun doWork(): Result {
        val manager = GlanceAppWidgetManager(context)
        val glanceIds = manager.getGlanceIds(WeatherAppWidget::class.java)

        return try {
            locationTracker.getCurrentLocation()?.let { location ->

                when (val result = repository.getWeatherData(
                    location.latitude,
                    location.longitude)
                ) {
                    is Resource.Success -> {
                        setWidgetState(
                            glanceIds = glanceIds,
                            newState = result.data?.currentWeather?.toWeatherWidgetInfo()
                                ?: WeatherWidgetInfo()
                        )
                    }
                    is Resource.Error -> {
                        setWidgetState(
                            glanceIds = glanceIds,
                            newState = WeatherWidgetInfo()
                        )
                    }
                }
            }
            enqueue(context)
            Result.success()
        }catch (e: Exception) {
            setWidgetState(
                glanceIds,
                WeatherWidgetInfo()
            )
            Result.failure()
        }
    }

    private fun setWidgetState(
        glanceIds: List<GlanceId>,
        newState: WeatherWidgetInfo) {
        MainScope().launch {
            glanceIds.forEach { glanceId ->
                updateAppWidgetState(
                    context = context,
                    definition = WeatherInfoStateDefinition,
                    glanceId = glanceId,
                    updateState = { newState }
                )
            }
            WeatherAppWidget.updateAll(context)
        }
    }

    companion object{

        private val uniqueWorkName = WeatherLocationWorker::class.java.simpleName

        fun enqueue(context: Context, force: Boolean = false) {
            val manager = WorkManager.getInstance(context)
            val requestBuilder = OneTimeWorkRequestBuilder<WeatherLocationWorker>()
                .setInitialDelay(1, TimeUnit.MINUTES)
            var workPolicy = ExistingWorkPolicy.KEEP

            // Replace any enqueued work and expedite the request
            if (force) {
                workPolicy = ExistingWorkPolicy.REPLACE
            }

            manager.enqueueUniqueWork(
                uniqueWorkName,
                workPolicy,
                requestBuilder.build()
            )
        }

        fun cancel(context: Context) {
            WorkManager.getInstance(context).cancelUniqueWork(uniqueWorkName)
        }
    }
}
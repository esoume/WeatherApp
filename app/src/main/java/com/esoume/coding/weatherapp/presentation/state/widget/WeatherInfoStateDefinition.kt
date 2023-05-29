package com.esoume.coding.weatherapp.presentation.state.widget

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.dataStoreFile
import androidx.glance.state.GlanceStateDefinition
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.File
import java.io.InputStream
import java.io.OutputStream

object WeatherInfoStateDefinition: GlanceStateDefinition<WeatherWidgetInfo> {

    private const val DATA_STORE_FILENAME = "weatherWidgetInfo"

    private val Context.datastore by dataStore<WeatherWidgetInfo>(DATA_STORE_FILENAME, WeatherWidgetInfoSerializer)

    override suspend fun getDataStore(
        context: Context,
        fileKey: String
    ): DataStore<WeatherWidgetInfo> {
        return context.datastore
    }

    override fun getLocation(context: Context, fileKey: String): File {
        return context.dataStoreFile(DATA_STORE_FILENAME)
    }

    object WeatherWidgetInfoSerializer : Serializer<WeatherWidgetInfo> {
        override val defaultValue = WeatherWidgetInfo(
            temperature = 0.0,
            windSpeed = 0.0,
            windDirection = 0.0,
            is_day = 1,
            time = "1h00",
            weatherCode = 0
        )

        override suspend fun readFrom(input: InputStream): WeatherWidgetInfo = try {
            Json.decodeFromString(
                WeatherWidgetInfo.serializer(), input.readBytes().decodeToString())
        } catch (exception: SerializationException) {
            throw CorruptionException("Could not read location data: ${exception.message}")
        }

        override suspend fun writeTo(t: WeatherWidgetInfo, output: OutputStream) {
            output.use {
                output.write(
                    Json.encodeToString(WeatherWidgetInfo.serializer(), t)
                        .encodeToByteArray()
                )
            }
        }
    }
}
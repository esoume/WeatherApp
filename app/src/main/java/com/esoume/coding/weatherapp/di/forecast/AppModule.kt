package com.esoume.coding.weatherapp.di.forecast

import android.app.Application
import android.content.Context
import com.esoume.coding.weatherapp.data.remote.forecast.api.WeatherApi
import com.esoume.coding.weatherapp.domain.util.LocationInfo
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(app: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(app)
    }

    @Provides
    @Singleton
    fun provideLocationInfo(
        context: Context
    ): LocationInfo = LocationInfo(context)
}
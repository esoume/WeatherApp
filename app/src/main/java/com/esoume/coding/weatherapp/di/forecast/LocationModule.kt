package com.esoume.coding.weatherapp.di.forecast

import com.esoume.coding.weatherapp.data.location.DefaultLocationTracker
import com.esoume.coding.weatherapp.domain.location.LocationTracker
import dagger.Binds
import dagger.Module
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Binds
    @Singleton
    abstract fun bindLocationTracker(defaultLocationTracker: DefaultLocationTracker): LocationTracker

}
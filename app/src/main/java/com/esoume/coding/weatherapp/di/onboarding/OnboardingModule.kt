package com.esoume.coding.weatherapp.di.onboarding

import android.content.Context
import com.esoume.coding.weatherapp.data.repository.onboarding.RepositoryOnboardingImpl
import com.esoume.coding.weatherapp.domain.repository.onboarding.RepositoryOnboarding
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton


@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class OnBoardingModule {

    @Binds
    @Singleton
    abstract fun provideContext(
        @ApplicationContext context: Context
    ): Context

    @Binds
    @Singleton
    abstract fun bindRepositoryOnBoarding(
        repositoryOnBoardingImpl: RepositoryOnboardingImpl
    ): RepositoryOnboarding
}
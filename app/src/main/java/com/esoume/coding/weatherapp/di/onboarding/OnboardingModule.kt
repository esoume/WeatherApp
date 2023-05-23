package com.esoume.coding.weatherapp.di.onboarding

import android.content.Context
import com.esoume.coding.weatherapp.data.repository.onboarding.RepositoryOnboardingImpl
import com.esoume.coding.weatherapp.domain.repository.onboarding.RepositoryOnboarding
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OnBoardingModule {

    @Provides
    @Singleton
    fun provideRepositoryOnBoarding(
        @ApplicationContext context: Context
    ): RepositoryOnboarding = RepositoryOnboardingImpl(context = context)
}
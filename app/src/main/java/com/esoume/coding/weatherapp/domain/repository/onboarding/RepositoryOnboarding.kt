package com.esoume.coding.weatherapp.domain.repository.onboarding

interface RepositoryOnboarding {

    suspend fun saveOnBoardingState(completed: Boolean)
}
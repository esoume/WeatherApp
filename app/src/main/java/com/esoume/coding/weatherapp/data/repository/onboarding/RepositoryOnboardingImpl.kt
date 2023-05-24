package com.esoume.coding.weatherapp.data.repository.onboarding

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.esoume.coding.weatherapp.domain.repository.onboarding.RepositoryOnboarding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore by preferencesDataStore(name = "on_boarding_pref")

class RepositoryOnboardingImpl @Inject constructor(context: Context) : RepositoryOnboarding {

    private object PreferencesKey {
        val onBoardingKey = booleanPreferencesKey(name = "on_boarding_completed")
    }

    private val dataStore = context.dataStore

    override suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.onBoardingKey] = completed
        }
    }

    fun readOnboardingState(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[PreferencesKey.onBoardingKey] ?: false
        }
    }
}
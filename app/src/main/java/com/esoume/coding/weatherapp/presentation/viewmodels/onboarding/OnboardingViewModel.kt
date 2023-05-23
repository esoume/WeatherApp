package com.esoume.coding.weatherapp.presentation.viewmodels.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esoume.coding.weatherapp.data.repository.onboarding.RepositoryOnboardingImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val repository: RepositoryOnboardingImpl
): ViewModel(){

    fun saveOnOnBoardingState(completed: Boolean){
        viewModelScope.launch { repository.saveOnBoardingState(completed) }
    }
}
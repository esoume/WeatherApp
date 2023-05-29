package com.esoume.coding.weatherapp.presentation.screen.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen
import androidx.navigation.NavHostController
import com.esoume.coding.weatherapp.presentation.screen.forecast.WeatherCard
import com.esoume.coding.weatherapp.presentation.screen.forecast.WeatherForecast
import com.esoume.coding.weatherapp.presentation.state.forecast.WeatherState
import com.esoume.coding.weatherapp.presentation.theme.DarkBlue
import com.esoume.coding.weatherapp.presentation.theme.DeepBlue

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomePage(
    navController: NavHostController,
    weatherUiState: WeatherState,
    refreshing: Boolean,
    splashScreen : SplashScreen,
    onRefresh: () -> Unit
){
    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = { onRefresh() }
    )

    splashScreen.setKeepOnScreenCondition {
        weatherUiState.isLoading
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
            .pullRefresh(pullRefreshState)
            .verticalScroll(rememberScrollState())
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            WeatherCard(
                state = weatherUiState,
                backgroundColor = DeepBlue
            )
            Spacer(modifier = Modifier.height(16.dp))
            WeatherForecast(state = weatherUiState)
        }
        PullRefreshIndicator(
            refreshing = refreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )

        if (weatherUiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        weatherUiState.error?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

}
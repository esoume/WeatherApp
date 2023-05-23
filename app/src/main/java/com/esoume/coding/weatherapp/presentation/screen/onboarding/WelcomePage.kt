package com.esoume.coding.weatherapp.presentation.screen.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.esoume.coding.weatherapp.domain.util.OnboardingPage
import com.esoume.coding.weatherapp.presentation.navigation.Screen
import com.esoume.coding.weatherapp.presentation.viewmodels.onboarding.OnboardingViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WelcomePage(
    navController: NavHostController,
    viewModel: OnboardingViewModel = hiltViewModel()
) {

    val pages = listOf(
        OnboardingPage.FirstPage,
        OnboardingPage.SecondPage,
        OnboardingPage.ThirdPage
    )

    val pagerState: PagerState = rememberPagerState()
    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            modifier = Modifier.weight(10f),
            pageCount = pages.size,
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) { position ->
            PagerScreen(onboardingPage = pages[position])
        }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            pageCount = pages.size
        )
        FinishButton(
            modifier = Modifier.weight(1f),
            pagerState = pagerState
        ) {
            viewModel.saveOnOnBoardingState(completed = true)
            navController.popBackStack()
            navController.navigate(Screen.Home.route)
        }

    }
}

@Composable
@Preview(showBackground = true)
fun FirstOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onboardingPage = OnboardingPage.FirstPage)
    }
}

@Composable
@Preview(showBackground = true)
fun SecondOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onboardingPage = OnboardingPage.SecondPage)
    }
}

@Preview(showBackground = true)
@Composable
fun ThirdOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onboardingPage = OnboardingPage.ThirdPage)
    }
}

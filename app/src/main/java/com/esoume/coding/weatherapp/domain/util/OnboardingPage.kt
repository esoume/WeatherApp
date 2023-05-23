package com.esoume.coding.weatherapp.domain.util

import androidx.annotation.DrawableRes
import com.esoume.coding.weatherapp.R

sealed class OnboardingPage(
    @DrawableRes val icon: Int,
    val title: String,
    val description: String
){

    object FirstPage: OnboardingPage(
        icon = R.drawable.baseline_groups_24,
        title = "Meeting",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod." +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod."
    )

    object SecondPage: OnboardingPage(
        icon = R.drawable.round_front_hand_24,
        title = "Coordination",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod." +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod." +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod."
    )

    object ThirdPage: OnboardingPage(
        icon = R.drawable.baseline_how_to_reg_24,
        title = "Dialogue",
        description = "Lorem ipsum dolor sit amet, conjecture disciplining elite, sed do eiusmod. " +
                "Lorem ipsum dolor sit amet, consecrate adipiscing elite, sed do eiusmod." +
                "Lorem ipsum dolor sit amet, consecrate adipiscing elite, sed do eiusmod." +
                "Lorem ipsum dolor sit amet, consecrate adipiscing elit, sed do eiusmod."
    )
}

package com.esoume.coding.weatherapp.presentation.screen.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.esoume.coding.weatherapp.domain.util.OnboardingPage

@Composable
fun PagerScreen(onboardingPage: OnboardingPage) {

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.5f),
            painter = painterResource(id = onboardingPage.icon),
            contentDescription = "Pager Image"
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = onboardingPage.title,
            fontSize = MaterialTheme.typography.h4.fontSize,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        Text(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 40.dp)
                .padding(top = 20.dp),
            fontSize = MaterialTheme.typography.body1.fontSize,
            fontWeight = FontWeight.Medium,
            text = onboardingPage.description,
            textAlign = TextAlign.Center
        )
    }
}
package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.KopiPrimaryButton
import com.example.ui.theme.CaramelGold
import com.example.ui.theme.CoffeeContainer
import com.example.ui.theme.EspressoTextPrimary
import com.example.ui.theme.EspressoTextSecondary
import com.example.ui.theme.WarmCreamBackground

@Composable
fun SplashScreen(
    onStartNowClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(WarmCreamBackground)
            .padding(24.dp)
            .testTag("splash_screen")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Circular Logo as shown in wireframe
            Box(
                modifier = Modifier
                    .size(190.dp)
                    .shadow(12.dp, CircleShape)
                    .clip(CircleShape)
                    .background(CoffeeContainer),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.kopi_welcome_hero_1793484784753),
                    contentDescription = "Logo Kopi dan Nyemil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(36.dp))

            Text(
                text = "Kopi dan Nyemil",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 32.sp,
                    color = EspressoTextPrimary,
                    letterSpacing = 0.5.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.testTag("app_title_text")
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Get a taste of your wish",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 17.sp,
                    color = EspressoTextSecondary,
                    fontWeight = FontWeight.Normal
                ),
                textAlign = TextAlign.Center
            )
        }

        // Start Now Button anchored at bottom
        KopiPrimaryButton(
            text = "Start now",
            onClick = onStartNowClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            testTag = "start_now_button"
        )
    }
}

package com.ttf.transfertoinr.features.transfer.success_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ttf.transfertoinr.R
import com.ttf.transfertoinr.core.designsystem.theme.JungleGreen
import com.ttf.transfertoinr.core.designsystem.theme.fontFamily
import com.ttf.transfertoinr.core.main.ScreenMain
import kotlinx.coroutines.delay

@Composable
fun SuccessScreen(navController: NavController) {

    LaunchedEffect(Unit) {
        delay(3000)
        navController.navigate(ScreenMain)
    }

    val style = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        color = JungleGreen
    )

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.checkanimation))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1
    )



    Box (modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
            LottieAnimation(
                composition = composition,
                progress = progress,
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(text = "Transaction started successfully", style = style)
        }

    }

}
package com.satwik.transfertoinr.features.auth.reset_password

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.satwik.transfertoinr.R
import com.satwik.transfertoinr.core.designsystem.components.TTFButton
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily
import com.satwik.transfertoinr.core.main.ScreenLogin

@Composable
fun ResetPasswordSuccessScreen(navController: NavController) {
    Box (modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)){

        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.checkanimation))
        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = 1
        )
        val style = TextStyle(fontFamily = fontFamily, fontSize = 13.sp, fontWeight = FontWeight.Normal, color = JungleGreen)

        Column (modifier = Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally){
            LottieAnimation(
                composition = composition,
                progress = progress,
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Password has been changed successfully", style = style)
        }

        TTFButton(text = "Continue to login", onClick = { navController.navigate(ScreenLogin) }, modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 20.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ResetPasswordSuccessScreenPreview(modifier: Modifier = Modifier) {
    ResetPasswordSuccessScreen(navController = rememberNavController())

}
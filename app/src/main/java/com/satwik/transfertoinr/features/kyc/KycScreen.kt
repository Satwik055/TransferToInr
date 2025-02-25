package com.satwik.transfertoinr.features.kyc

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.satwik.transfertoinr.R
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.White
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily
import com.sumsub.sns.core.SNSMobileSDK
import com.sumsub.sns.core.data.listener.SNSStateChangedHandler
import com.sumsub.sns.core.data.listener.TokenExpirationHandler
import com.sumsub.sns.core.data.model.SNSSDKState
import com.sumsub.sns.core.theme.SNSTheme
import com.sumsub.sns.core.theme.SNSThemeColor
import com.sumsub.sns.core.theme.SNSThemeFont
import com.sumsub.sns.core.theme.colors
import com.sumsub.sns.core.theme.fonts
import org.koin.androidx.compose.koinViewModel
import java.util.Locale


@Composable
fun KycScreen(activity: Activity) {

    val viewModel = koinViewModel<KycScreenViewModel>()
    val accessTokenState = viewModel.accessTokenState.value
    val errorTextStyle = TextStyle(fontWeight = FontWeight.Normal, fontFamily = fontFamily, fontSize = 14.sp, color = JungleGreen)

    Box(modifier = Modifier.fillMaxSize()){
        when{
            accessTokenState.error.isNotEmpty() -> {
                Text(text = accessTokenState.error, style = errorTextStyle)
            }
            accessTokenState.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            else -> {
                Content(viewModel = viewModel, activity = activity)
            }
        }
    }
}

@Composable
private fun Content(activity: Activity, viewModel: KycScreenViewModel) {

    val tokenExpirationHandler = object : TokenExpirationHandler {
        override fun onTokenExpired(): String {
            val newToken = "..."
            return newToken
        }
    }
    var kycStatus by remember { mutableStateOf("") }
    val accessToken = viewModel.accessTokenState.value.accessToken
    val context = LocalContext.current


    LaunchedEffect(kycStatus) { println(kycStatus) }

    val stateChangedHandler = object : SNSStateChangedHandler {
        override fun onStateChanged(previousState: SNSSDKState, currentState: SNSSDKState) {
            when (currentState) {
                is SNSSDKState.Approved -> {
                    viewModel.updateKycStatus(true)
                    kycStatus = "KYC Completed: Approved"
                }
                is SNSSDKState.FinallyRejected -> {
                    kycStatus = "KYC Completed: Rejected"
                }
                is SNSSDKState.TemporarilyDeclined -> {
                    kycStatus = "KYC Temporarily Declined"
                }
                else -> {
                    kycStatus = "KYC in progress"
                }
            }
        }
    }

    val customTheme = SNSTheme {
//        colors {
//            contentInfo = SNSThemeColor(Color.RED)
//            progressBarTint = SNSThemeColor(JungleGreen.toArgb())
//            contentStrong = SNSThemeColor(JungleGreen.toArgb())
//            statusBarColor = SNSThemeColor(JungleGreen.toArgb())
//            backgroundCommon = SNSThemeColor(Color.WHITE)
//            contentStrong = SNSThemeColor(Color.BLACK)
//            primaryButtonBackground = SNSThemeColor(JungleGreen.toArgb())
//            primaryButtonContent = SNSThemeColor(Color.WHITE)
//        }

        val fontRegular = Typeface.create(context.resources.getFont(R.font.poppins_regular), Typeface.NORMAL)
        val fontMedium = Typeface.create(context.resources.getFont(R.font.poppins_medium), Typeface.NORMAL)
        val fontSemiBold = Typeface.create(context.resources.getFont(R.font.poppins_semibold), Typeface.NORMAL)

        fonts {
            headline1 = SNSThemeFont(fontSemiBold, 20) //Verify you identity
            headline2 = SNSThemeFont(fontMedium, 16)
            subtitle1 = SNSThemeFont(fontMedium, 14) //Button text
            subtitle2 = SNSThemeFont(fontRegular, 14)
            body = SNSThemeFont(fontRegular, 14)
            caption = SNSThemeFont(fontRegular, 13) //Progressbar Text
        }
    }

    val snsSdk = remember {
        SNSMobileSDK.Builder(activity).withDebug(true).withTheme(customTheme)
            .withAccessToken(accessToken, onTokenExpiration = tokenExpirationHandler)
            .withStateChangedHandler(stateChangedHandler)
            .withLocale(Locale("en"))
            .build()
    }

    LaunchedEffect(Unit) {
        snsSdk.launch()
    }


}






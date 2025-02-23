package com.satwik.transfertoinr.features.kyc

import android.app.Activity
import android.content.Context
import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.Typeface
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

    val snsSdk = remember {
        SNSMobileSDK.Builder(activity).withDebug(true)
            .withAccessToken(accessToken, onTokenExpiration = tokenExpirationHandler)
            .withStateChangedHandler(stateChangedHandler)
            .withLocale(Locale("en"))
            .build()
    }

    LaunchedEffect(Unit) {
        snsSdk.launch()
    }
}





//    val customTheme = SNSTheme {
//        colors {
//            backgroundCommon = SNSThemeColor(Color.WHITE)
//            contentStrong = SNSThemeColor(Color.BLACK)
//            primaryButtonBackground = SNSThemeColor(Color.BLUE)
//            primaryButtonContent = SNSThemeColor(Color.WHITE)
//        }
//        fonts {
//            headline1 = SNSThemeFont(Typeface.MONOSPACE, 40)
//            subtitle2 = SNSThemeFont(Typeface.SANS_SERIF, 16)
//            body = SNSThemeFont(Typeface.DEFAULT, 14)
//        }
//    }
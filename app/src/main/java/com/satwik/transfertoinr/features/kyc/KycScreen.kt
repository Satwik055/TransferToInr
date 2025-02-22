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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Typeface
import androidx.compose.ui.unit.dp
import com.satwik.transfertoinr.R
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.White
import com.sumsub.sns.core.SNSMobileSDK
import com.sumsub.sns.core.data.listener.TokenExpirationHandler
import com.sumsub.sns.core.theme.SNSTheme
import com.sumsub.sns.core.theme.SNSThemeColor
import com.sumsub.sns.core.theme.SNSThemeFont
import com.sumsub.sns.core.theme.colors
import com.sumsub.sns.core.theme.fonts
import java.util.Locale


@Composable
fun KycScreen(modifier: Modifier = Modifier, activity: Activity) {
    Box (
        modifier=Modifier.fillMaxSize()
    ){
        Row (
            modifier = Modifier
                .background(color = JungleGreen)
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 16.dp)
        ){
            Icon(modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(35.dp), painter = painterResource(id = R.drawable.ic_logo), tint = White, contentDescription = null)
        }
        Content(activity = activity)
    }
}

@Composable
private fun Content(modifier: Modifier = Modifier, activity: Activity) {
    Box (
        modifier.fillMaxSize()
    ){


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
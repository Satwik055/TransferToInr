package com.satwik.transfertoinr.features.auth.reset_password

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.satwik.transfertoinr.R
import com.satwik.transfertoinr.core.designsystem.components.TTFButton
import com.satwik.transfertoinr.core.designsystem.components.TTFSnackbar
import com.satwik.transfertoinr.core.designsystem.components.TTFTextField
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily
import com.satwik.transfertoinr.core.main.ScreenResetPasswordOtpVerification
import com.satwik.transfertoinr.core.utils.addSpacesToCamelCase
import org.koin.androidx.compose.koinViewModel

@Composable
fun EnterEmailScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    val style = TextStyle(fontFamily = fontFamily, fontSize = 13.sp, fontWeight = FontWeight.Normal, color = JungleGreen)
    val viewmodel = koinViewModel<ResetPasswordViewModel>()
    val state = viewmodel.sendPasswordResetEmailResult.value

    LaunchedEffect(state.success){
        if(state.success){
            navController.navigate(ScreenResetPasswordOtpVerification(email))
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_carret),
            tint = JungleGreen,
            contentDescription = null,
            modifier = Modifier.clickable { navController.popBackStack() }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "RESET PASSWORD",
            fontFamily = fontFamily,
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            color = JungleGreen
        )
        Text(
            text = "Enter your email to below and we will send a code",
            fontFamily = fontFamily,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = JungleGreen
        )

        Spacer(modifier = Modifier.height(70.dp))

        Text(text = "Email",  style = style)

        Spacer(modifier = Modifier.height(5.dp))
        TTFTextField(
            text = email,
            onValueChange = { email = it },
            placeholder = "example@gmail.com",
            isError = state.error.isNotEmpty(),
            errorText = state.error
        )

        Spacer(modifier = Modifier.height(50.dp))
        TTFButton(
            text = "Send Code",
            isLoading = state.isLoading,
            onClick = {
                viewmodel.sendPasswordResetEmail(
                    email = email,
                )
            }
        )
    }
}


@Composable
fun InfoBox(modifier: Modifier = Modifier, message:String) {
    val style = TextStyle(fontFamily = fontFamily, fontSize = 13.sp, fontWeight = FontWeight.Normal, color = JungleGreen)
    Box(
        modifier = modifier
            .background(color = JungleGreen.copy(0.05f), shape = RoundedCornerShape(8.dp))
            .border(width = 1.dp, color = JungleGreen, shape = RoundedCornerShape(8.dp))
            .padding(15.dp)
    ){
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(text = "INFO", style = style.copy(fontWeight = FontWeight.Medium, fontSize = 15.sp))
                Icon(imageVector = Icons.Filled.Info , tint = JungleGreen, contentDescription = null )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = message, style = style)
        }
    }
}
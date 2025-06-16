package com.ttf.transfertoinr.features.auth.verify_email

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.toRoute
import com.ttf.transfertoinr.R
import com.ttf.transfertoinr.core.designsystem.components.ResendOtpButton
import com.ttf.transfertoinr.core.designsystem.components.TTFButton
import com.ttf.transfertoinr.core.designsystem.components.TTFTextField
import com.ttf.transfertoinr.core.designsystem.theme.JungleGreen
import com.ttf.transfertoinr.core.designsystem.theme.LightGrey
import com.ttf.transfertoinr.core.designsystem.theme.VeryLightGrey
import com.ttf.transfertoinr.core.designsystem.theme.fontFamily
import com.ttf.transfertoinr.core.main.ScreenEmailVerification
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun EmailVerificationScreen(navController: NavController) {

    val args = navController.currentBackStackEntry!!.toRoute<ScreenEmailVerification>()
    var otp by remember { mutableStateOf("") }
    val email  = args.email

    val viewModel = koinViewModel<EmailVerificationViewModel>()
    val state =  viewModel.verificationResult.value
    val style = TextStyle(fontFamily = fontFamily, fontSize = 13.sp, fontWeight = FontWeight.Normal, color = JungleGreen)

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)){
        Spacer(modifier = Modifier.height(10.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_carret),
            tint = JungleGreen,
            contentDescription = null,
            modifier = Modifier.clickable { navController.popBackStack() }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "VERIFICATION",
            fontFamily = fontFamily,
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            color = JungleGreen
        )
        Text(
            text = "Verify your email to continue",
            fontFamily = fontFamily,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = JungleGreen
        )

        Spacer(modifier = Modifier.height(70.dp))

        Text(text = "Enter the code below sent to $email",  style = style)

        Spacer(modifier = Modifier.height(5.dp))
        TTFTextField(
            text = otp,
            onValueChange = { otp = it },
            placeholder = "Enter code",
            isError = state.error.isNotEmpty(),
            errorText = state.error
        )
        ResendOtpButton(
            onResendOtpClicked = { viewModel.resendEmailOtp(email) },
            modifier = Modifier
                .align(Alignment.End)
                .offset(y = -(20.dp))
        )

        Spacer(modifier = Modifier.height(50.dp))

        TTFButton(
            fontSize = 16.sp,
            text = "Verify Otp",
            isLoading = state.isLoading,
            onClick = {
                viewModel.verifyOtp(
                    otp = otp,
                    email = email,
                    onSuccess = {}
                )
            }
        )
    }
}


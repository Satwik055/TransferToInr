package com.satwik.transfertoinr.features.auth.reset_password

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.toRoute
import com.satwik.transfertoinr.R
import com.satwik.transfertoinr.core.designsystem.components.ResendOtpButton
import com.satwik.transfertoinr.core.designsystem.components.TTFButton
import com.satwik.transfertoinr.core.designsystem.components.TTFTextField
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily
import com.satwik.transfertoinr.core.main.ScreenCreateNewPassword
import com.satwik.transfertoinr.core.main.ScreenResetPasswordOtpVerification
import com.satwik.transfertoinr.features.auth.verify_email.EmailVerificationViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun EnterOtpScreen(navController: NavController) {

    val args = navController.currentBackStackEntry!!.toRoute<ScreenResetPasswordOtpVerification>()
    var otp by remember { mutableStateOf("") }
    val email  = args.email

    val viewModel = koinViewModel<ResetPasswordViewModel>()
    val state =  viewModel.verificationResult.value

    val style = TextStyle(
        fontFamily = fontFamily,
        fontSize = 13.sp,
        fontWeight = FontWeight.Normal,
        color = JungleGreen
    )

    LaunchedEffect(state.success){
        if(state.success){
            navController.navigate(ScreenCreateNewPassword)
        }
    }
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ){
        Spacer(modifier = Modifier.height(10.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_carret),
            tint = JungleGreen,
            contentDescription = null,
            modifier = Modifier.clickable { navController.popBackStack() }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Enter OTP",
            fontFamily = fontFamily,
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            color = JungleGreen
        )
        Text(
            text = "Enter OTP sent to your email",
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
            placeholder = "Enter otp",
            isError = state.error.isNotEmpty(),
            errorText = state.error
        )
//        ResendOtpButton(
//            onResendOtpClicked = { viewModel.resendEmailOtp(email) },
//            modifier = Modifier
//                .align(Alignment.End)
//                .offset(y = -(20.dp))
//        )


        Spacer(modifier = Modifier.height(50.dp))

        TTFButton(
            fontSize = 16.sp,
            text = "Verify Otp",
            isLoading = state.isLoading,
            onClick = {
                viewModel.verifyResetOtp(
                    otp = otp,
                    email = email,
                )
            }
        )
    }
}



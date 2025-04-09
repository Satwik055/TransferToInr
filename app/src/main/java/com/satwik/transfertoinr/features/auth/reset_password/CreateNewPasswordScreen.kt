package com.satwik.transfertoinr.features.auth.reset_password

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.satwik.transfertoinr.R
import com.satwik.transfertoinr.core.designsystem.components.TTFButton
import com.satwik.transfertoinr.core.designsystem.components.TTFTextField
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily
import com.satwik.transfertoinr.core.main.ScreenCreateNewPassword
import com.satwik.transfertoinr.core.main.ScreenLogin
import com.satwik.transfertoinr.core.main.ScreenResetPasswordSuccess
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateNewPasswordScreen(navController: NavController) {

    var newPassword by remember { mutableStateOf("") }
    var confirmNewPassword by remember { mutableStateOf("") }

    val style = TextStyle(fontFamily = fontFamily, fontSize = 13.sp, fontWeight = FontWeight.Normal, color = JungleGreen)

    val viewModel = koinViewModel<ResetPasswordViewModel>()
    val state = viewModel.changePasswordResult.value

    LaunchedEffect(state.success){
        if(state.success) {
            navController.navigate(ScreenResetPasswordSuccess) {
                popUpTo(ScreenCreateNewPassword) {
                    inclusive = true
                }
            }
        }
    }


    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp))
    {
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
            text = "Enter a new password below",
            fontFamily = fontFamily,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = JungleGreen
        )

        Spacer(modifier = Modifier.height(70.dp))

        Text(text = "Enter new password", style = style)

        Spacer(modifier = Modifier.height(5.dp))
        TTFTextField(
            text = newPassword,
            isPassword = true,
            onValueChange = { newPassword = it },
            placeholder = "Your new password",
//        isError = state.error.isNotEmpty(),
//        errorText = state.error
        )
        TTFTextField(
            text = confirmNewPassword,
            isPassword = true,
            onValueChange = { confirmNewPassword = it },
            placeholder = "Confirm password",
//        isError = state.error.isNotEmpty(),
//        errorText = state.error
        )

        Spacer(modifier = Modifier.weight(1f))
        TTFButton(
            text = "Submit",
            isLoading = state.isLoading,
            onClick = {
                viewModel.changePassword(
                    newPassword = newPassword,
                    onSuccess = {
                        navController.navigate(ScreenResetPasswordSuccess) {
                            popUpTo(ScreenCreateNewPassword) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        )
    }
}
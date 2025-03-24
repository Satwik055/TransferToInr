package com.satwik.transfertoinr.features.auth.reset_password.verify_email_reset_password

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.satwik.transfertoinr.R
import com.satwik.transfertoinr.core.designsystem.components.TTFButton
import com.satwik.transfertoinr.core.designsystem.components.TTFTextField
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily
import com.satwik.transfertoinr.features.auth.reset_password.ResetPasswordViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ResetPasswordEmailVerifyScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var submittedEmail by remember { mutableStateOf("") }

    val style = TextStyle(fontFamily = fontFamily, fontSize = 13.sp, fontWeight = FontWeight.Normal, color = JungleGreen)

    val viewmodel = koinViewModel<ResetPasswordViewModel>()
    val state = viewmodel.sendPasswordResetEmailResult.value
    val isInfoBoxVisible by remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    )
    {
        Spacer(modifier = Modifier.height(10.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_carret),
            tint = JungleGreen,
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "FORGET PASSWORD",
            fontFamily = fontFamily,
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            color = JungleGreen
        )
        Text(
            text = "Enter your email to verify it",
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
//        isError = state.error.isNotEmpty(),
//        errorText = state.error
        )
        if(state.success){
            InfoBox(message = "A verification link has been sent to $submittedEmail, Please click on it to verify")
        }
        Spacer(modifier = Modifier.height(50.dp))
        TTFButton(
            text = "Submit",
            isLoading = state.isLoading,
            onClick = {
                viewmodel.sendPasswordResetEmail(email)
                submittedEmail = email
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
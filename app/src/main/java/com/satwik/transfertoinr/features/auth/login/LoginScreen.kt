package com.satwik.transfertoinr.features.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.satwik.transfertoinr.R
import com.satwik.transfertoinr.core.designsystem.components.TTFButton
import com.satwik.transfertoinr.core.designsystem.components.TTFTextField
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = Color.White,
        darkIcons = true
    )

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    Column (Modifier.background(color = Color.White)){

        Spacer(modifier = Modifier.height(10.dp))
        Icon(painter = painterResource(id = R.drawable.ic_carret), tint = JungleGreen, contentDescription = null, modifier = Modifier.padding(start = 16.dp, end = 25.dp, top = 16.dp, bottom = 16.dp))
        Column(Modifier.padding(horizontal = 16.dp)){

            Text(text = "LOGIN", fontFamily = fontFamily, fontSize = 35.sp, fontWeight = FontWeight.Bold, color = JungleGreen)
            Text(text = "Please login to continue", fontFamily = fontFamily, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = JungleGreen)
            Spacer(modifier = Modifier.height(40.dp))
            Column(verticalArrangement = Arrangement.spacedBy(1.dp)){
                TTFTextField(text = email, onValueChange = {email=it}, placeholder = "Email", keyboardType = KeyboardType.Email)
                TTFTextField(text = password, onValueChange = {password=it}, placeholder = "Password", keyboardType = KeyboardType.Password)
            }

            Spacer(modifier = Modifier.height(100.dp))

            TTFButton(text = "Submit", onClick = {})
            Spacer(modifier = Modifier.weight(1f))
            SignupText(onClick = { /*TODO*/ }, modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 12.dp))
        }

    }

}

@Composable
internal fun SignupText(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    style: TextStyle = TextStyle( fontFamily = fontFamily, fontSize = 13.sp, fontWeight = FontWeight.Normal, color = JungleGreen),
){
    Text(
        modifier = modifier.clickable { onClick.invoke() },
        style = style,
        text = buildAnnotatedString {
            append("Don't have an account ? ")
            withStyle(style = SpanStyle(color = JungleGreen, fontWeight = FontWeight.SemiBold)){
                append("Signup")
            }
        },
    )
}
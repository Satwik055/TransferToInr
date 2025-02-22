package com.satwik.transfertoinr.features.auth.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.satwik.transfertoinr.R
import com.satwik.transfertoinr.core.designsystem.components.ButtonType
import com.satwik.transfertoinr.core.designsystem.components.TTFButton
import com.satwik.transfertoinr.core.designsystem.components.TTFTextField
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily
import com.satwik.transfertoinr.core.main.ScreenLogin
import com.satwik.transfertoinr.features.auth.login.LoginScreenViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import org.koin.viewmodel.getViewModelKey

@Composable
fun SignupScreen(modifier: Modifier = Modifier, navController: NavController) {

    val viewModel = koinViewModel<SignupScreenViewModel>()
    val state = viewModel.signupScreenState.value

    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(Modifier.background(Color.White)){
        when {
            state.isLoading -> println("Loading...")
            state.error.isNotEmpty() -> println("Error: ${state.error}")
            state.success -> println("Signup Done")
        }

        Spacer(modifier = Modifier.height(10.dp))
        Icon(painter = painterResource(id = R.drawable.ic_carret), tint = JungleGreen, contentDescription = null, modifier = Modifier.padding(start = 16.dp, end = 25.dp, top = 16.dp, bottom = 16.dp))
        Column(Modifier.padding(horizontal = 16.dp)){

            Text(text = "SIGNUP", fontFamily = fontFamily, fontSize = 35.sp, fontWeight = FontWeight.Bold, color = JungleGreen)
            Text(text = "Please signup to continue", fontFamily = fontFamily, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = JungleGreen)
            Spacer(modifier = Modifier.height(40.dp))
            Column(verticalArrangement = Arrangement.spacedBy(5.dp)){
                TTFTextField(text = name, onValueChange = {name=it}, placeholder = "Name")
                TTFTextField(text = phone, onValueChange = {phone=it}, placeholder = "Phone", keyboardType = KeyboardType.Phone)
                TTFTextField(text = email, onValueChange = {email=it}, placeholder = "Email", keyboardType = KeyboardType.Email)
                TTFTextField(text = password, onValueChange = {password=it}, placeholder = "Password", keyboardType = KeyboardType.Password, isPassword = true)
            }

            Spacer(modifier = Modifier.height(40.dp))

            TTFButton(
                text = "Submit",
                type = when(state.isLoading) {
                    true -> ButtonType.LOADING
                    false-> ButtonType.REGULAR
            },
                onClick = {
                viewModel.signup(email, password, name, phone)
            })

            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "By signing up you agree our Privacy Policy and Terms and Conditions", fontFamily = fontFamily, fontSize = 12.sp, fontWeight = FontWeight.Normal, color = JungleGreen)
            Spacer(modifier = Modifier.weight(1f))
            LoginText(onClick = { navController.navigate(ScreenLogin) }, modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 12.dp))
        }

    }

}

@Composable
internal fun LoginText(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    style: TextStyle = TextStyle( fontFamily = fontFamily, fontSize = 13.sp, fontWeight = FontWeight.Normal, color = JungleGreen),
){
    Text(
        modifier = modifier.clickable { onClick.invoke() },
        style = style,
        text = buildAnnotatedString {
            append("Already have an account ?")
            withStyle(style = SpanStyle(color = JungleGreen, fontWeight = FontWeight.SemiBold)){
                append("Login")
            }
        },
    )
}
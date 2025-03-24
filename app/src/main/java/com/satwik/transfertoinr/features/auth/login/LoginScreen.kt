package com.satwik.transfertoinr.features.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.satwik.transfertoinr.R
import com.satwik.transfertoinr.core.designsystem.components.TTFButton
import com.satwik.transfertoinr.core.designsystem.components.TTFSnackbar
import com.satwik.transfertoinr.core.designsystem.components.TTFTextField
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily
import com.satwik.transfertoinr.core.main.ScreenResetPasswordEmailVerify
import com.satwik.transfertoinr.core.main.ScreenSignup
import com.satwik.transfertoinr.core.utils.addSpacesToCamelCase
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(modifier: Modifier = Modifier, navController: NavController) {

    /*
    * NOTE:
    * Login validation use case in inactive for now, the usecase logic has been commented
    * Only supabase server side error is being displayed for now
    */

    val viewModel = koinViewModel<LoginScreenViewModel>()
    val state = viewModel.loginScreenState.value
    val formState = viewModel.formState.value
    val context = LocalContext.current
    var isFormValidated by remember { mutableStateOf(false) }
    var errorText by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    //For snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(context) {
        viewModel.validationEvents.collect{ event->
            when(event){
                is LoginScreenViewModel.ValidationEvent.Success ->{
                    isFormValidated = true
                }
            }
        }
    }

    if(state.error.isNotEmpty()){
        errorText = state.error
        isError = true
    }

    Box{
        Column(modifier = modifier.background(Color.White)){
            Spacer(modifier = Modifier.height(10.dp))
            Icon(painter = painterResource(id = R.drawable.ic_carret), tint = JungleGreen, contentDescription = null, modifier = Modifier
                .padding(start = 16.dp, end = 25.dp, top = 16.dp, bottom = 16.dp)
                .clickable { navController.popBackStack() },)
            Column(Modifier.padding(horizontal = 16.dp)){

                Text(text = "LOGIN", fontFamily = fontFamily, fontSize = 35.sp, fontWeight = FontWeight.Bold, color = JungleGreen)
                Text(text = "Please login to continue", fontFamily = fontFamily, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = JungleGreen)
                Spacer(modifier = Modifier.height(40.dp))
                Column(verticalArrangement = Arrangement.spacedBy(1.dp)){
                    TTFTextField(
                        text = formState.email,
                        onValueChange ={viewModel.onEvent(LoginFormEvent.EmailChanged(it))},
                        placeholder = "Email",
                        errorText = formState.emailError?:"",
                        isError = formState.emailError != null,
                        keyboardType = KeyboardType.Email
                    )
                    Column {
                        TTFTextField(
                            text = formState.password,
                            onValueChange ={viewModel.onEvent(LoginFormEvent.PasswordChanged(it))},
                            placeholder = "Password",
                            isError = formState.passwordError != null,
                            errorText = formState.passwordError?:"",
                            isPassword = true
                        )
                        Text(
                            text = "Forget password ?",
                            fontFamily = fontFamily,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = JungleGreen,
                            modifier = Modifier
                                .align(Alignment.End)
                                .offset(y = -(10.dp))
                                .clickable(onClick = {navController.navigate(ScreenResetPasswordEmailVerify)}, interactionSource = null, indication = null)
                        )
                    }

                }

                Spacer(modifier = Modifier.height(100.dp))

                TTFButton(
                    text = "Submit",
                    isLoading = state.isLoading,
                    onClick = {

                        if (errorText.isNotEmpty()) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = errorText,
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }

                        viewModel.onEvent(LoginFormEvent.Submit)
                    }
                )

                LaunchedEffect(isFormValidated) {
                    if (isFormValidated) {
                        viewModel.login(formState.email, formState.password)
                    }
                }


                Spacer(modifier = Modifier.weight(1f))
                SignupText(
                    onClick = { navController.navigate(ScreenSignup) },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 12.dp)
                )
            }
        }
        SnackbarHost(
            snackbar = { TTFSnackbar(text = addSpacesToCamelCase(errorText), color = Color.Red, modifier = Modifier.padding(vertical = 65.dp, horizontal = 16.dp)) },
            modifier = Modifier.align(Alignment.TopCenter),
            hostState = snackbarHostState,
        )
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
            append("Don't have an account ?")
            withStyle(style = SpanStyle(color = JungleGreen, fontWeight = FontWeight.SemiBold)){
                append(" Signup")
            }
        },
    )
}


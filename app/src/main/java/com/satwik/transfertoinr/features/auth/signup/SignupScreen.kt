package com.satwik.transfertoinr.features.auth.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import com.satwik.transfertoinr.core.main.ScreenLogin
import com.satwik.transfertoinr.core.utils.addSpacesToCamelCase
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignupScreen(modifier: Modifier = Modifier, navController: NavController) {

    val viewModel = koinViewModel<SignupScreenViewModel>()
    val state = viewModel.signupScreenState.value

    val formState = viewModel.formState.value
    val context = LocalContext.current
    var isFormValidated by remember { mutableStateOf(false) }
    var errorText by remember { mutableStateOf("") }

    //For snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()


    LaunchedEffect(context) {
        viewModel.validationEvents.collect{ event->
            when(event){
                is SignupScreenViewModel.ValidationEvent.Success ->{
                    isFormValidated = true
                }
            }
        }
    }

    if(state.error.isNotEmpty()){
        errorText = state.error
    }
    Box {

        Column(modifier.background(Color.White)){
            Spacer(modifier = Modifier.height(10.dp))
            Icon(painter = painterResource(id = R.drawable.ic_carret), tint = JungleGreen, contentDescription = null, modifier = Modifier.padding(start = 16.dp, end = 25.dp, top = 16.dp, bottom = 16.dp))
            Column(Modifier.padding(horizontal = 16.dp)){

                Text(text = "SIGNUP", fontFamily = fontFamily, fontSize = 35.sp, fontWeight = FontWeight.Bold, color = JungleGreen)
                Text(text = "Please signup to continue", fontFamily = fontFamily, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = JungleGreen)
                Spacer(modifier = Modifier.height(40.dp))
                Column(verticalArrangement = Arrangement.spacedBy(5.dp)){
                    TTFTextField(
                        text = formState.name,
                        onValueChange = { viewModel.onEvent(SignupFormEvent.NameChanged(it)) },
                        placeholder = "Name",
                        isError = formState.nameError != null,
                        errorText = formState.nameError ?: "",
                    )
                    TTFTextField(
                        text = formState.phone,
                        onValueChange = { viewModel.onEvent(SignupFormEvent.PhoneChanged(it)) },
                        placeholder = "Phone",
                        errorText = formState.phoneError?:"",
                        isError = formState.phoneError != null,
                        keyboardType = KeyboardType.Phone
                    )
                    TTFTextField(
                        text = formState.email,
                        onValueChange = { viewModel.onEvent(SignupFormEvent.EmailChanged(it)) },
                        isError = formState.emailError != null,
                        errorText = formState.emailError?:"",
                        placeholder = "Email",
                        keyboardType = KeyboardType.Email
                    )
                    TTFTextField(
                        text = formState.password,
                        onValueChange = { viewModel.onEvent(SignupFormEvent.PasswordChanged(it)) },
                        placeholder = "Password",
                        errorText = formState.passwordError?:"",
                        isError = formState.passwordError != null,
                        keyboardType = KeyboardType.Password,
                        isPassword = true
                    )
                    TTFTextField(
                        text = formState.reEnterPassword,
                        onValueChange = { viewModel.onEvent(SignupFormEvent.ReEnterPasswordChanged(it)) },
                        placeholder = "Confirm Password",
                        errorText = formState.reEnterPasswordError?:"",
                        isError = formState.reEnterPasswordError != null,
                        keyboardType = KeyboardType.Password,
                        isPassword = true
                    )

                }

                Spacer(modifier = Modifier.height(40.dp))

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
                        viewModel.onEvent(SignupFormEvent.Submit)
                        if(isFormValidated){
                            viewModel.resetFormErrors()
                            viewModel.signup(
                                email = formState.email,
                                password = formState.password,
                                name = formState.name,
                                phone = formState.phone
                            )
                        }
                    })

                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "By signing up you agree our Privacy Policy and Terms and Conditions", fontFamily = fontFamily, fontSize = 12.sp, fontWeight = FontWeight.Normal, color = JungleGreen)
                Spacer(modifier = Modifier.weight(1f))
                LoginText(
                    onClick = { navController.navigate(ScreenLogin) }, modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 12.dp))
            }
        }
        SnackbarHost(
            snackbar = { TTFSnackbar(text = addSpacesToCamelCase(errorText), color = Color.Red, modifier = Modifier.padding(vertical = 65.dp, horizontal = 16.dp)) },
            modifier = Modifier.align(Alignment.BottomCenter),
            hostState = snackbarHostState,
        )
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
                append(" Login")
            }
        },
    )
}
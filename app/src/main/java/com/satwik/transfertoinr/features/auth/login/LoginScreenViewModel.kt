package com.satwik.transfertoinr.features.auth.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satwik.transfertoinr.data.auth.AuthRepository
import com.satwik.transfertoinr.data.auth.login_preconditions.LoginValidateEmailUsecase
import com.satwik.transfertoinr.data.auth.login_preconditions.LoginValidatePasswordUsecase
import com.satwik.transfertoinr.features.auth.signup.SignupScreenViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val authRepository: AuthRepository,
    private val loginValidatePasswordUsecase: LoginValidatePasswordUsecase,
    private val loginValidateEmailUsecase: LoginValidateEmailUsecase
):ViewModel() {

    private val _loginScreenState = mutableStateOf(LoginScreenState())
    val loginScreenState: State<LoginScreenState> = _loginScreenState

    private val _formState = mutableStateOf(LoginFormState())
    val formState: State<LoginFormState> = _formState

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()


    fun login(email:String, password:String){
        viewModelScope.launch {
            _loginScreenState.value = LoginScreenState(isLoading = true)
            try{
                authRepository.login(email, password)
                _loginScreenState.value = LoginScreenState(success = true)
            }
            catch (e:Exception){
                _loginScreenState.value = LoginScreenState(error = e.message.toString())
            }

        }
    }

    fun onEvent(event: LoginFormEvent){
        when(event){
            is LoginFormEvent.EmailChanged ->
                _formState.value = _formState.value.copy(email = event.email)
            is LoginFormEvent.PasswordChanged ->
                _formState.value = _formState.value.copy(password = event.password)
            LoginFormEvent.Submit ->
                submitData()
        }
    }

    private fun submitData() {
        val emailResult = loginValidateEmailUsecase.execute(_formState.value.email)
        val passwordResult = loginValidatePasswordUsecase.execute(_formState.value.password)

        val hasError = listOf(
            emailResult,
            passwordResult,
        ).any{!it.successfull}

        if(hasError){
            _formState.value = _formState.value.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
            )
        }
        else{
            viewModelScope.launch {
                validationEventChannel.send(ValidationEvent.Success)
            }
        }
    }


    sealed class ValidationEvent{
        data object Success:ValidationEvent()
    }


}
package com.satwik.transfertoinr.features.auth.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satwik.transfertoinr.data.auth.AuthRepository
import com.satwik.transfertoinr.data.auth.AuthRepositoryImpl
import com.satwik.transfertoinr.data.auth.signup_preconditions.ValidateEmailUsecase
import com.satwik.transfertoinr.data.auth.signup_preconditions.ValidateNameUsecase
import com.satwik.transfertoinr.data.auth.signup_preconditions.ValidatePasswordUsecase
import com.satwik.transfertoinr.data.auth.signup_preconditions.ValidatePhoneUsecase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SignupScreenViewModel(
    private val authRepository: AuthRepository,
    private val validateEmailUsecase: ValidateEmailUsecase,
    private val validatePasswordUsecase: ValidatePasswordUsecase,
    private val validateNameUsecase: ValidateNameUsecase,
    private val validatePhoneUsecase: ValidatePhoneUsecase

):ViewModel() {

    private val _signupScreenState = mutableStateOf(SignupScreenState())
    val signupScreenState: State<SignupScreenState> = _signupScreenState

    private val _formState = mutableStateOf(SignupFormState())
    val formState: State<SignupFormState> = _formState

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()


    fun signup(email:String, password:String, name:String, phone:String){
        viewModelScope.launch {
            _signupScreenState.value = SignupScreenState(isLoading = true)
            try{
                authRepository.signup(email, password, name, phone)
                _signupScreenState.value = SignupScreenState(success = true)
            }
            catch (e:Exception){
                _signupScreenState.value = SignupScreenState(error = e.message.toString())
            }
        }
    }

    fun onEvent(event:SignupFormEvent){
        when(event){
            is SignupFormEvent.EmailChanged ->{
                _formState.value = _formState.value.copy(email = event.email)
            }
            is SignupFormEvent.PasswordChanged ->{
                _formState.value = _formState.value.copy(password = event.password)
            }
            is SignupFormEvent.NameChanged -> {
                _formState.value = _formState.value.copy(name = event.name)
            }
            is SignupFormEvent.PhoneChanged -> {
                _formState.value = _formState.value.copy(phone = event.phone)
            }
            is SignupFormEvent.Submit -> {
                submitData()
            }
        }
    }



    private fun submitData() {
        val emailResult = validateEmailUsecase.execute(_formState.value.email)
        val passwordResult = validatePasswordUsecase.execute(_formState.value.password)
        val nameResult = validateNameUsecase.execute(_formState.value.name)
        val phoneResult = validatePhoneUsecase.execute(_formState.value.phone)


        val hasError = listOf(
            emailResult,
            passwordResult,
            nameResult,
            phoneResult
        ).any{!it.successfull}

        if(hasError){
            _formState.value = _formState.value.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                nameError = nameResult.errorMessage,
                phoneError = phoneResult.errorMessage
            )
        }
        else{
            viewModelScope.launch {
                validationEventChannel.send(ValidationEvent.Success)
            }
        }
    }

    sealed class ValidationEvent{
        object Success:ValidationEvent()
    }
}
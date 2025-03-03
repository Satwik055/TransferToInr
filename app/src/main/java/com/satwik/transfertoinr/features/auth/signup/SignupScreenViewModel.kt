package com.satwik.transfertoinr.features.auth.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satwik.transfertoinr.data.auth.AuthRepository
import com.satwik.transfertoinr.data.auth.signup_preconditions.SignupValidateEmailUsecase
import com.satwik.transfertoinr.data.auth.signup_preconditions.SignupValidateNameUsecase
import com.satwik.transfertoinr.data.auth.signup_preconditions.SignupValidatePasswordUsecase
import com.satwik.transfertoinr.data.auth.signup_preconditions.SignupValidatePhoneUsecase
import com.satwik.transfertoinr.data.auth.signup_preconditions.SignupValidateReEnterPasswordUsecase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SignupScreenViewModel(
    private val authRepository: AuthRepository,
    private val signupValidateEmailUsecase: SignupValidateEmailUsecase,
    private val signupValidatePasswordUsecase: SignupValidatePasswordUsecase,
    private val signupValidateNameUsecase: SignupValidateNameUsecase,
    private val signupValidatePhoneUsecase: SignupValidatePhoneUsecase,
    private val signupValidateReEnterPasswordUsecase: SignupValidateReEnterPasswordUsecase
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
            is SignupFormEvent.ReEnterPasswordChanged -> {
                _formState.value = _formState.value.copy(reEnterPassword = event.reEnterPassword)
            }
        }
    }



    private fun submitData() {
        val emailResult = signupValidateEmailUsecase.execute(_formState.value.email)
        val passwordResult = signupValidatePasswordUsecase.execute(_formState.value.password)
        val nameResult = signupValidateNameUsecase.execute(_formState.value.name)
        val phoneResult = signupValidatePhoneUsecase.execute(_formState.value.phone)
        val reEnterPasswordResult = signupValidateReEnterPasswordUsecase.execute(_formState.value.reEnterPassword, _formState.value.password)

        val hasError = listOf(
            emailResult,
            passwordResult,
            nameResult,
            phoneResult,
            reEnterPasswordResult
        ).any{!it.successfull}

        if(hasError){
            _formState.value = _formState.value.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                nameError = nameResult.errorMessage,
                phoneError = phoneResult.errorMessage,
                reEnterPasswordError = reEnterPasswordResult.errorMessage
            )
        }
        else{
            viewModelScope.launch {
                validationEventChannel.send(ValidationEvent.Success)
            }
        }
    }

    fun resetFormErrors(){
        _formState.value = _formState.value.copy(
            emailError = null,
            passwordError = null,
            nameError = null,
            phoneError = null,
            reEnterPasswordError = null
        )
    }

    sealed class ValidationEvent{
        data object Success:ValidationEvent()
    }
}
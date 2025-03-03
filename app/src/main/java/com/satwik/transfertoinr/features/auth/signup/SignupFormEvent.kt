package com.satwik.transfertoinr.features.auth.signup

sealed class SignupFormEvent {
    data class EmailChanged(val email:String):SignupFormEvent()
    data class PasswordChanged(val password:String):SignupFormEvent()
    data class NameChanged(val name:String):SignupFormEvent()
    data class PhoneChanged(val phone:String):SignupFormEvent()
    data object Submit:SignupFormEvent()
}
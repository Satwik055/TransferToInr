package com.ttf.transfertoinr.features.auth.login

sealed class LoginFormEvent {
    data class EmailChanged(val email:String):LoginFormEvent()
    data class PasswordChanged(val password:String):LoginFormEvent()

    data object Submit:LoginFormEvent()
}
package com.ttf.transfertoinr.features.auth.signup

data class SignupFormState(
    val email:String = "",
    var emailError:String? = null,
    val password:String = "",
    val passwordError:String? = null,
    val name:String = "",
    val nameError:String? = null,
    val phone:String = "",
    val phoneError:String? = null,
)
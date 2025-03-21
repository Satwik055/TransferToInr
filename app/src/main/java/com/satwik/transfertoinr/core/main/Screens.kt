package com.satwik.transfertoinr.core.main

import kotlinx.serialization.Serializable

@Serializable
object ScreenMain

@Serializable
object ScreenSignup

@Serializable
object ScreenLogin

@Serializable
object ScreenRecipient

@Serializable
object ScreenAddRecipient

@Serializable
object ScreenHelp

@Serializable
object ScreenPrivacyPolicy

@Serializable
object ScreenHome

@Serializable
object ScreenTransfer

@Serializable
object ScreenTransaction

@Serializable
object ScreenAccount

@Serializable
object ScreenPayment

@Serializable
object ScreenSelectRecipient

@Serializable
object ScreenSummary

@Serializable
object ScreenAmount

@Serializable
data class ScreenEmailVerification(
    val email:String = ""
)

@Serializable
object ScreenKyc





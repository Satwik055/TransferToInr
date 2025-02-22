package com.satwik.transfertoinr.core.main

import android.app.Activity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.satwik.transfertoinr.features.auth.login.LoginScreen
import com.satwik.transfertoinr.features.auth.signup.SignupScreen
import com.satwik.transfertoinr.features.help.HelpScreen
import com.satwik.transfertoinr.features.privacypolicy.PrivacyPolicyScreen
import com.satwik.transfertoinr.features.recipient.AddRecipientScreen
import com.satwik.transfertoinr.features.recipient.RecipientScreen

@Composable
fun SetupNavgraph(navController:NavHostController, startDestination:Any, activity: Activity) {
    NavHost(navController =navController , startDestination = startDestination) {
        composable<ScreenSignup> {
            SignupScreen(navController = navController)
        }
        composable<ScreenMain> {
            MainScreen(navController = navController, activity = activity)
        }
        composable<ScreenLogin> {
            LoginScreen()
        }
        composable<ScreenRecipient> {
            RecipientScreen(navController = navController)
        }
        composable<ScreenAddRecipient> {
            AddRecipientScreen(navController = navController)
        }
        composable<ScreenHelp> {
            HelpScreen(navController = navController)
        }
        composable<ScreenHelp> {
            HelpScreen(navController = navController)
        }
        composable<ScreenPrivacyPolicy> {
            PrivacyPolicyScreen(navController = navController)
        }
    }
}
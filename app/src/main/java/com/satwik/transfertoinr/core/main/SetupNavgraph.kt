package com.satwik.transfertoinr.core.main

import android.app.Activity
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.satwik.transfertoinr.features.auth.login.LoginScreen
import com.satwik.transfertoinr.features.auth.signup.SignupScreen
import com.satwik.transfertoinr.features.help.HelpScreen
import com.satwik.transfertoinr.features.kyc.KycScreen
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
        composable<ScreenHelp>(
            enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(300)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(300)) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(300)) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(300)) }
        ){
            HelpScreen(navController = navController)
        }
        composable<ScreenPrivacyPolicy> {
            PrivacyPolicyScreen()
        }
        composable<ScreenAddRecipient>(
            enterTransition = { slideInVertically(initialOffsetY = { it }, animationSpec = tween(400)) },
            exitTransition = { slideOutVertically(targetOffsetY = { -it }, animationSpec = tween(400)) },
            popEnterTransition = { slideInVertically(initialOffsetY = { -it }, animationSpec = tween(400)) },
            popExitTransition = { slideOutVertically(targetOffsetY = { it }, animationSpec = tween(400)) }
        ){
            AddRecipientScreen(navController = navController)
        }
        composable<ScreenKyc> {
            KycScreen(activity)
        }
    }
}
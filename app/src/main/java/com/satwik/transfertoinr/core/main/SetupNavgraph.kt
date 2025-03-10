package com.satwik.transfertoinr.core.main

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.satwik.transfertoinr.features.auth.login.LoginScreen
import com.satwik.transfertoinr.features.auth.verify_email.EmailVerificationScreen
import com.satwik.transfertoinr.features.auth.signup.SignupScreen
import com.satwik.transfertoinr.features.help.HelpScreen
import com.satwik.transfertoinr.features.kyc.KycScreen
import com.satwik.transfertoinr.features.privacypolicy.PrivacyPolicyScreen
import com.satwik.transfertoinr.features.recipient.AddRecipientScreen
import com.satwik.transfertoinr.features.recipient.RecipientScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavgraph(navController:NavHostController, startDestination:Any, activity: Activity) {


    NavHost(navController =navController , startDestination = startDestination) {
        composable<ScreenSignup> {
            SignupScreen(navController = navController)
        }
        composable<ScreenMain> {
//            EmailVerificationScreen(navController = navController)
            MainScreen(navController = navController, activity = activity)
        }
        composable<ScreenLogin> {
            LoginScreen(navController=navController)
        }
        composable<ScreenRecipient> {
            RecipientScreen(navController = navController)
        }
        composable<ScreenAddRecipient> {
            AddRecipientScreen(navController = navController)
        }

        composable<ScreenEmailVerification> {
            EmailVerificationScreen(navController = navController)
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
            KycScreen(navController)
        }
    }
}
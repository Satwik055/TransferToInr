package com.satwik.transfertoinr.core.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.snap
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
import com.satwik.transfertoinr.features.transfer.shared_viewmodel.TransferSharedViewModel
import com.satwik.transfertoinr.features.transfer.payment_screen.PaymentScreen
import com.satwik.transfertoinr.features.transfer.amount_screen.AmountScreen
import com.satwik.transfertoinr.features.transfer.select_recipient_screen.SelectRecipientScreen
import com.satwik.transfertoinr.features.transfer.summary_screen.SummaryScreen
import org.koin.java.KoinJavaComponent.inject

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavgraph(navController:NavHostController, startDestination:Any) {

    NavHost(navController = navController , startDestination = startDestination) {

        val transferViewmodel: TransferSharedViewModel by inject(TransferSharedViewModel::class.java)

        composable<ScreenSignup> {
            SignupScreen(navController = navController)
        }
        composable<ScreenMain> {
            MainScreen(navController = navController, viewModel = transferViewmodel)
        }
        composable<ScreenLogin> {
            LoginScreen(navController=navController)
        }
        composable<ScreenPayment> {
            PaymentScreen(navController=navController, viewModel = transferViewmodel)
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
        composable<ScreenSelectRecipient>(
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = snap()
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = snap()
                )
            }
        ) {

            SelectRecipientScreen(navController = navController, transferSharedViewModel = transferViewmodel)
        }

        composable<ScreenAmount>{
            AmountScreen(navController = navController, viewModel = transferViewmodel)
        }

        composable<ScreenSummary>(
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = snap()
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = snap()
                )
            }
        ) {
            SummaryScreen(navController = navController, viewModel = transferViewmodel )
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
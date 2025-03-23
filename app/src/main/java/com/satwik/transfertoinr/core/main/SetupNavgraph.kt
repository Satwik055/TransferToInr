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
import androidx.navigation.compose.navigation
import androidx.navigation.createGraph
import androidx.navigation.navigation
import com.satwik.transfertoinr.R
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
import com.satwik.transfertoinr.features.transfer.success_screen.SuccessScreen
import com.satwik.transfertoinr.features.transfer.summary_screen.SummaryScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.compose.navigation.koinNavViewModel
import org.koin.core.qualifier.Qualifier
import org.koin.java.KoinJavaComponent.inject

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavgraph(navController:NavHostController, startDestination:Any) {

    NavHost(navController = navController , startDestination) {

        
        val transferViewmodel: TransferSharedViewModel by inject(TransferSharedViewModel::class.java)


        composable<ScreenSignup>(
            enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = snap()) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = snap()) }
        ) {

            SignupScreen(navController = navController)
        }
        composable<ScreenMain>(
            enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = snap()) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = snap()) }
        ) {
            MainScreen(navController = navController, viewModel = transferViewmodel)
        }
        composable<ScreenLogin>(
            enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = snap()) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = snap()) }
        ) {
            LoginScreen(navController=navController)
        }
        composable<ScreenPayment>(
            enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = snap()) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = snap()) }
        ) {
            PaymentScreen(navController=navController, viewModel = transferViewmodel)
        }
        composable<ScreenRecipient>(
            enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = snap()) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = snap()) }
        ){
            RecipientScreen(navController = navController)
        }
        composable<ScreenAddRecipient> (
            enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = snap()) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = snap()) }
        ){
            AddRecipientScreen(navController = navController)
        }

        composable<ScreenEmailVerification> (
            enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = snap()) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = snap()) }
        ){
            EmailVerificationScreen(navController = navController)
        }

        composable<ScreenSelectRecipient>(
            enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = snap()) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = snap()) }
        ) {
            SelectRecipientScreen(navController = navController, transferSharedViewModel = transferViewmodel)
        }

        composable<ScreenAmount>(
            enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = snap()) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = snap()) }
        ){
            AmountScreen(navController = navController, transferSharedViewModel = transferViewmodel)
        }

        composable<ScreenSummary>(
            enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = snap()) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = snap()) }
        ) {
            SummaryScreen(navController = navController, transferSharedViewModel = transferViewmodel )
        }

        composable<ScreenPayment> (
            enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = snap()) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = snap()) }
        ){
            PaymentScreen(navController = navController, viewModel = transferViewmodel)
        }

        composable<ScreenHelp>(
            enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = snap()) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = snap()) }
        ){
            HelpScreen(navController = navController)
        }

        composable<ScreenPrivacyPolicy>(
            enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = snap()) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = snap()) }
        ) {
            PrivacyPolicyScreen()
        }

        composable<ScreenAddRecipient>(
            enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = snap()) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = snap()) }
        ){
            AddRecipientScreen(navController = navController)
        }

        composable<ScreenSuccess>(
            enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = snap()) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = snap()) }
        ){
            SuccessScreen(navController = navController)
        }

        composable<ScreenKyc>(
            enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = snap()) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = snap()) }
        ) {
            KycScreen(navController)
        }

////        *************
//        navigation(startDestination = ScreenAmount, route = ScreenAmount::class){
//
//            val backStackEntry = navController.getBackStackEntry(ScreenAmount)
//            println("GRAPH ID: " + backStackEntry.id)
//
//
//            composable<ScreenSelectRecipient>(
//
//                enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = snap()) },
//                exitTransition = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = snap()) }
//            ) {
//                val vm = koinViewModel<TransferSharedViewModel>(viewModelStoreOwner = backStackEntry )
//
//                SelectRecipientScreen(navController = navController, transferSharedViewModel = vm)
//            }
//
//            composable<ScreenAmount>(
//                enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = snap()) },
//                exitTransition = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = snap()) }
//            ){
//                val vm = koinViewModel<TransferSharedViewModel>(viewModelStoreOwner = backStackEntry )
//                AmountScreen(navController = navController, transferSharedViewModel = vm)
//            }
//
//            composable<ScreenSummary>(
//                enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = snap()) },
//                exitTransition = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = snap()) }
//            ) {
//                val vm = koinViewModel<TransferSharedViewModel>(viewModelStoreOwner = backStackEntry )
//                SummaryScreen(navController = navController, transferSharedViewModel = vm )
//            }
//
//            composable<ScreenPayment> (
//                enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = snap()) },
//                exitTransition = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = snap()) }
//            ){
//                val vm = koinViewModel<TransferSharedViewModel>(viewModelStoreOwner = backStackEntry)
//                PaymentScreen(navController = navController, viewModel = vm)
//            }
//        }
            //**************
        }
    }

package com.satwik.transfertoinr.core.main

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.annotation.MainThread
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.satwik.transfertoinr.R
import com.satwik.transfertoinr.core.designsystem.components.TTFBottomNavigationBar
import com.satwik.transfertoinr.core.designsystem.components.TTFBottomNavigationBarz
import com.satwik.transfertoinr.core.designsystem.components.TTFIconHeader
import com.satwik.transfertoinr.core.designsystem.components.TTFTextHeader
import com.satwik.transfertoinr.core.designsystem.theme.LightGrey
import com.satwik.transfertoinr.core.designsystem.theme.TransferToInrTheme
import com.satwik.transfertoinr.core.designsystem.theme.Typography
import com.satwik.transfertoinr.features.account.AccountScreen
import com.satwik.transfertoinr.features.auth.login.LoginScreen
import com.satwik.transfertoinr.features.auth.login.LoginScreenViewModel
import com.satwik.transfertoinr.features.auth.signup.SignupScreen
import com.satwik.transfertoinr.features.help.HelpScreen
import com.satwik.transfertoinr.features.home.HomeScreen
import com.satwik.transfertoinr.features.kyc.KycScreen
import com.satwik.transfertoinr.features.privacypolicy.PrivacyPolicyScreen
import com.satwik.transfertoinr.features.recipient.AddRecipientScreen
import com.satwik.transfertoinr.features.recipient.RecipientScreen
import com.satwik.transfertoinr.features.transaction.TransactionScreen
import com.satwik.transfertoinr.features.transfer.TransferScreen
import io.github.jan.supabase.SupabaseClient
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: MainActivityViewModel by inject()
        installSplashScreen()
            .apply {
                setKeepOnScreenCondition{!viewModel.isReady.value}
        }
        setContent {
            val userState = viewModel.mainActivityState.value
            val startDestination:Any = if(userState.isUserLoggedIn){ ScreenMain } else{ ScreenSignup }
            val navController = rememberNavController()

            SetupNavgraph(
                navController = navController,
                startDestination = startDestination,
                activity = this@MainActivity
            )
        }
    }
}


@Composable
fun MainScreen(navController: NavController, activity: Activity) {
    var selectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        containerColor = Color.White,
        topBar = { TTFHeader(selectedIndex = selectedIndex, navController = navController) },
        bottomBar = { TTFBottomNavigationBarz(selectedIndex) { selectedIndex = it } }
    ) {
        when (selectedIndex) {
            0 -> HomeScreen(navController = navController, internalPadding = it)
            1 -> TransferScreen(internalPadding = it)
            2 -> TransactionScreen(internalPadding = it)
            3 -> RecipientScreen(internalPadding = it, navController = navController )
            4 -> AccountScreen(navController = navController, activity = activity, internalPadding = it)
        }
    }

}




@Composable
fun TTFHeader(selectedIndex: Int, navController: NavController) {

    fun getScreenTitle(selectedIndex: Int): String {
        return when (selectedIndex) {
            0 -> "Home"
            1 -> "Transfer"
            2 -> "Transactions"
            3 -> "Recipients"
            4 -> "Account"
            else -> "App"
        }
    }

    when (selectedIndex) {
        0 -> TTFIconHeader(helpButtonOnClick = { navController.navigate(ScreenHelp) })
        else -> TTFTextHeader(text = getScreenTitle(selectedIndex))
    }


}
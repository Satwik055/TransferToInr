package com.satwik.transfertoinr.core.main

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.satwik.transfertoinr.core.designsystem.components.TTFBottomNavigationBar
import com.satwik.transfertoinr.core.designsystem.components.headers.TTFScaffoldHeader
import com.satwik.transfertoinr.core.utils.PermissionRequester
import com.satwik.transfertoinr.features.account.AccountScreen
import com.satwik.transfertoinr.features.auth.reset_password.verify_email_reset_password.ResetPasswordEmailVerifyScreen
import com.satwik.transfertoinr.features.home.HomeScreen
import com.satwik.transfertoinr.features.recipient.RecipientScreen
import com.satwik.transfertoinr.features.transaction.TransactionScreen
import com.satwik.transfertoinr.features.transfer.shared_viewmodel.TransferSharedViewModel
import com.satwik.transfertoinr.features.transfer.amount_screen.AmountScreen
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()

        val viewModel: MainActivityViewModel by inject()
        installSplashScreen().apply { setKeepOnScreenCondition{viewModel.isInitializing.value} }

        setContent {
            PermissionRequester()
            val isInitializing = viewModel.isInitializing.collectAsState().value
            val isLoggedIn = viewModel.isLoggedIn.collectAsState().value
            val startDestination:Any = if(isLoggedIn){ ScreenMain } else{ ScreenSignup }
            val navController = rememberNavController()

            if(!isInitializing){
                SetupNavgraph(
                    navController = navController,
                    startDestination = startDestination,
                )
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(navController: NavController, viewModel: TransferSharedViewModel) {
    var selectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        containerColor = Color.White,
        topBar = { TTFScaffoldHeader(selectedIndex = selectedIndex, navController = navController) },
        bottomBar = { TTFBottomNavigationBar(selectedIndex) { selectedIndex = it } }
    ) { internalPadding ->
        Box(modifier = Modifier
            .padding(internalPadding)
            .padding(16.dp)) {
            when (selectedIndex) {
                0 -> HomeScreen()
                1 -> AmountScreen(navController = navController, transferSharedViewModel = viewModel)
                2 -> TransactionScreen(navController)
                3 -> RecipientScreen(navController = navController)
                4 -> AccountScreen(navController = navController)
            }
        }
    }
}





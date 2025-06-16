package com.ttf.transfertoinr.core.designsystem.components.headers

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.ttf.transfertoinr.core.main.ScreenHelp

@Composable
fun TTFScaffoldHeader(selectedIndex: Int, navController: NavController) {
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
        0 -> TTFHomeHeader(helpButtonOnClick = { navController.navigate(ScreenHelp) })
        else -> TTFTextHeader(text = getScreenTitle(selectedIndex))
    }
}
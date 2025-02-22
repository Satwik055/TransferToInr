package com.satwik.transfertoinr.features.help

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.satwik.transfertoinr.core.designsystem.components.headers.TTFTextHeader

@Composable
fun HelpScreen(modifier: Modifier = Modifier, navController: NavController) {
    Box (
        modifier=Modifier.fillMaxSize()
    ){
        TTFTextHeader(text = "Help & Support", isBackButtonEnabled = true, onBackClick = {navController.popBackStack()})

        Content()
    }
}

@Composable
private fun Content(modifier: Modifier = Modifier) {
    Box (
        modifier.fillMaxSize()
    ){

    }
}

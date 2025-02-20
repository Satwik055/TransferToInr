package com.satwik.transfertoinr.features.help

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.satwik.transfertoinr.R
import com.satwik.transfertoinr.core.designsystem.components.TTFTextHeader
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.White

@Composable
fun HelpScreen(modifier: Modifier = Modifier, navController: NavController) {
    Box (
        modifier=Modifier.fillMaxSize()
    ){
        TTFTextHeader(text = "Help & Support")

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

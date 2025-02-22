package com.satwik.transfertoinr.features.privacypolicy

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.satwik.transfertoinr.core.designsystem.components.headers.TTFTextHeader
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily

@Composable
fun PrivacyPolicyScreen(modifier: Modifier = Modifier, navController: NavController) {
     val style = TextStyle(fontFamily = fontFamily, color = JungleGreen, fontSize = 13.sp)
    Column {
        TTFTextHeader(text = "Privacy Policy", isBackButtonEnabled = true,  onBackClick = {navController.popBackStack()})
        Text(text = "This is the privacy policy screen.", style = style, modifier = Modifier.padding(16.dp))
    }

}


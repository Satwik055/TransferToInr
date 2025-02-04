package com.satwik.transfertoinr.features.recipient

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.satwik.transfertoinr.core.designsystem.components.TTFHeader
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.Typography
import com.satwik.transfertoinr.core.designsystem.theme.White
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily

@Composable
fun RecipientScreen(modifier: Modifier = Modifier) {
    Column (
        modifier = modifier.fillMaxSize()
    ){
        TTFHeader(text = "Recipient")
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

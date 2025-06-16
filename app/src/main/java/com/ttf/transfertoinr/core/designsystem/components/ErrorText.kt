package com.ttf.transfertoinr.core.designsystem.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ttf.transfertoinr.core.designsystem.theme.fontFamily

@Composable
fun ErrorText(message: String) {
    Text(
        text = message,
        style =  TextStyle(
            fontFamily = fontFamily,
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Red
        ),
    )
}
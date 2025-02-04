package com.satwik.transfertoinr.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.satwik.transfertoinr.R
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.Typography

@Composable
fun TTFHeader(modifier: Modifier = Modifier, text: String, style: TextStyle = Typography.headlineLarge) {
    Box(
        modifier = modifier
            .background(color = JungleGreen)
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 16.dp),
    ) {

        Text(
            text = text,
            style = style,
            modifier = Modifier.align(Alignment.CenterStart)
        )
    }

}
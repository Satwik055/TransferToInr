package com.satwik.transfertoinr.core.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily

@Composable
fun TTFSnackbar(modifier: Modifier = Modifier, text:String, color: Color) {
    val style = TextStyle(fontWeight = FontWeight.Normal, fontFamily = fontFamily, fontSize = 13.sp, color = JungleGreen)

    Surface (
        color = Color.White,
        border = BorderStroke(width = 1.dp, color = color),
        modifier = modifier
            .fillMaxWidth()
            .height(45.dp)
            .graphicsLayer {
                shadowElevation = 5.dp.toPx()
                translationY = 40f
            },
        shape = RoundedCornerShape(5.dp)
    )
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ){
            Icon(imageVector = Icons.Filled.Info, contentDescription = null, tint = color )
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = text, style = style, modifier = Modifier)
        }
    }
}
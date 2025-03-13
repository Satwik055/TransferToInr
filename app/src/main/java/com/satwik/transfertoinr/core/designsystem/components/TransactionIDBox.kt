package com.satwik.transfertoinr.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.satwik.transfertoinr.core.designsystem.theme.VeryLightGrey
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily

@Composable
fun TransactionIDBox(modifier: Modifier = Modifier, id: String) {

    val style = TextStyle(fontFamily = fontFamily, fontSize = 13.sp, fontWeight = FontWeight.Normal)

    Box(
        modifier
            .fillMaxWidth()
            .background(color = VeryLightGrey, shape = RoundedCornerShape(4.dp))
            .padding(16.dp),
    ) {
        Text(text = id, style = style)
    }
}

package com.satwik.transfertoinr.core.designsystem.components

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.satwik.transfertoinr.R
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily
import com.satwik.transfertoinr.core.utils.copyToClipboard

@Composable
fun CopyableText(title:String, modifier: Modifier = Modifier, copyableText: String, context: Context) {
    Row (horizontalArrangement = Arrangement.spacedBy(5.dp), modifier = modifier){

        val style = TextStyle(fontFamily = fontFamily, fontSize = 13.sp, fontWeight = FontWeight.Normal)

        Text(text = "$title: $copyableText", style = style)
        Icon(
            painter = painterResource(id = R.drawable.ic_copy),
            tint = JungleGreen,
            contentDescription = null,
            modifier = Modifier
                .size(15.dp)
                .clickable { copyToClipboard(context as Activity, text = copyableText) })
    }
}
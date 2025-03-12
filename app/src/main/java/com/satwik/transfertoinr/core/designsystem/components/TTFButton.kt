package com.satwik.transfertoinr.core.designsystem.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.White
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily


@Composable
fun TTFButton(
    modifier: Modifier = Modifier,
    color: Color = JungleGreen,
    textColor: Color = White,
    text:String,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    onClick: () -> Unit,
    isLoading:Boolean = false
){
    Button(
        onClick = onClick,
        enabled = !isLoading,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            disabledContainerColor = color.copy(alpha = 0.6f),
            disabledContentColor = Color.White.copy(alpha = 0.5f)
        ),
        modifier= modifier
            .fillMaxWidth()
            .height(55.dp)
    ) {

        when(isLoading){
            false ->
                Text(
                    text = text,
                    fontFamily = fontFamily,
                    style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false)),
                    fontWeight = fontWeight,
                    color = textColor,
                    fontSize = fontSize
                )
            true ->
                CircularProgressIndicator(color = White, modifier = Modifier.size(25.dp), strokeWidth = 2.dp)
        }
    }
}
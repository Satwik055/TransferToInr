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

enum class ButtonType{
    REGULAR,
    LOADING
}


@Composable
fun TTFButton(
    modifier: Modifier = Modifier,
    type: ButtonType = ButtonType.REGULAR,
    color: Color = JungleGreen,
    textColor: Color = White,
    text:String,
    fontSize: TextUnit = 18.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    onClick: () -> Unit,
){
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        modifier= modifier
            .fillMaxWidth()
            .height(55.dp)
    ) {
        when(type){
            ButtonType.REGULAR ->
                Text(
                    text = text,
                    fontFamily = fontFamily,
                    style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false)),
                    fontWeight = fontWeight,
                    color = textColor,
                    fontSize = fontSize
                )
            ButtonType.LOADING ->
                CircularProgressIndicator(color = White, modifier = Modifier.size(25.dp), strokeWidth = 2.dp)
        }
    }
}
package com.satwik.transfertoinr.features.account.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.LightGrey
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily

@Composable
fun KycButton(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(fontFamily = fontFamily, fontSize = 16.sp, fontWeight = FontWeight.Medium),
    text:String,
    onClick: () -> Unit,
    isVerified:Boolean
) {
    val customIndication = ripple(color = LightGrey)

    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable(
                enabled = !isVerified ,
                onClick = { onClick.invoke() },
                interactionSource = remember { MutableInteractionSource() },
                indication = customIndication
            )
            .height(50.dp)
            .fillMaxWidth()
            .padding(horizontal = 10.dp)


    ){
        Text(text = text, style = textStyle)
        KycStatusChip(isVerified)
    }
}

@Composable
fun KycStatusChip(isVerified:Boolean) {

    val style = TextStyle(fontFamily = fontFamily, fontSize = 12.sp, fontWeight = FontWeight.Normal, color = Color.Red)
    val color = when(isVerified){
        true-> JungleGreen
        false-> Color.Red
    }
    val text = when(isVerified) {
        true -> "Verified"
        false -> "Unverified"
    }

    Box(
        modifier = Modifier
            .border(width = 1.dp, color = color, shape = RoundedCornerShape(6.dp))
            .padding(5.dp)
    ) {
        Text(
            style = style,
            text = text,
            color = color
        )
    }
}
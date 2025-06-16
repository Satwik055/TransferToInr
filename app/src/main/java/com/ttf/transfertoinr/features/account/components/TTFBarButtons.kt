package com.ttf.transfertoinr.features.account.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ttf.transfertoinr.core.designsystem.theme.LightGrey
import com.ttf.transfertoinr.core.designsystem.theme.fontFamily

@Composable
fun TTFBarButtons(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(fontFamily = fontFamily, fontSize = 16.sp, fontWeight = FontWeight.Medium),
    text:String,
    @DrawableRes icon:Int,
    onClick: () -> Unit
    ) {

    val customIndication = ripple(color = LightGrey)
    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(50.dp)
            .clickable(
                onClick = { onClick.invoke() },
                interactionSource = remember { MutableInteractionSource() },
                indication = customIndication
            )
            .fillMaxWidth()
            .padding(horizontal = 10.dp)


    ){
        Text(text = text, style = textStyle)
        Icon(painter = painterResource(id = icon), contentDescription = null)
    }
}
package com.satwik.transfertoinr.core.designsystem.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun TTFIconButton(
    modifier:Modifier = Modifier,
    @DrawableRes
    icon:Int,
    buttonColor: Color = Color.White,
    onClick: () -> Unit
){
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(color = buttonColor, shape = RoundedCornerShape(1.dp))
            .clickable { onClick.invoke() }
    ){
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier.align(Alignment.Center)
        )

    }
}
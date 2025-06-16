package com.satwik.transfertoinr.core.designsystem.components.headers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.satwik.transfertoinr.R
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.White

@Composable
fun TTFHomeHeader(modifier: Modifier = Modifier, helpButtonOnClick: () -> Unit) {
    Row (
        modifier = modifier
            .background(color = JungleGreen)
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Icon(modifier = Modifier
            .align(Alignment.CenterVertically)
            .size(40.dp), painter = painterResource(id = R.drawable.ic_logo), tint = White, contentDescription = null)

        Icon(
            painter = painterResource(id = R.drawable.ic_message),
            tint = Color.White,
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterVertically).size(28.dp).clickable { helpButtonOnClick.invoke()}
        )
    }

}
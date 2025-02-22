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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.satwik.transfertoinr.R
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.Typography

@Composable
fun TTFTextHeader(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = Typography.headlineLarge,
    onBackClick: (() -> Unit)? = null,
    isBackButtonEnabled:Boolean = false
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        modifier = modifier
            .background(color = JungleGreen)
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 16.dp),
    ) {
        if(isBackButtonEnabled){
            Icon(
            painter = painterResource(id = R.drawable.ic_carret),
            tint = White, contentDescription = null,
            modifier = Modifier.size(23.dp).clickable { onBackClick?.invoke()}
            )
        }
        Text(
            text = text,
            style = style,
        )
    }

}
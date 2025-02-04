package com.satwik.transfertoinr.core.designsystem.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.satwik.transfertoinr.R
import com.satwik.transfertoinr.core.designsystem.theme.LightGrey
import com.satwik.transfertoinr.core.designsystem.theme.Typography
import com.satwik.transfertoinr.core.designsystem.theme.White

@Composable
fun TTFBottomNavigationBar(
    modifier: Modifier = Modifier,
    color: Color = White,
    textStyle: TextStyle = Typography.labelSmall,
) {
    Column (
        modifier = modifier.background(color = color),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ){
        HorizontalDivider(thickness = 1.dp)
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BottomNavigationItem(text = "Home", icon = R.drawable.ic_home, textStyle = textStyle)
            BottomNavigationItem(text = "Transfer", icon = R.drawable.ic_plane, textStyle = textStyle, spacing = 6.dp)
            BottomNavigationItem(text = "Transaction", icon = R.drawable.ic_page, textStyle = textStyle, spacing = 6.dp)
            BottomNavigationItem(text = "Recipient", icon = R.drawable.ic_people, textStyle = textStyle, spacing = 7.5.dp)
            BottomNavigationItem(text = "Account", icon = R.drawable.ic_account, textStyle = textStyle,spacing = 3.dp)
        }
        Spacer(modifier = Modifier.height(1.dp))
            
        }
    }

@Composable
fun BottomNavigationItem(
    modifier: Modifier = Modifier,
    textStyle: TextStyle,
    text:String,
    spacing: Dp = 4.dp,
    iconTint: Color = LightGrey,
    @DrawableRes icon:Int
){
    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing)
    ){
        Icon(painter = painterResource(id = icon), tint = iconTint, contentDescription = null )
        Text(text = text, style = textStyle)
    }
}
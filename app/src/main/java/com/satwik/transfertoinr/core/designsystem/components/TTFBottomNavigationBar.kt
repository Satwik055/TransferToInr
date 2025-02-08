package com.satwik.transfertoinr.core.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.satwik.transfertoinr.R
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.LightGrey
import com.satwik.transfertoinr.core.designsystem.theme.VeryLightGrey
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily

@Composable
fun TTFBottomNavigationBar(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit,
) {
    val items = listOf("Home", "Transfer", "Transaction", "Recipient", "Account")
    val icons = listOf(
        R.drawable.ic_home,
        R.drawable.ic_plane,
        R.drawable.ic_page,
        R.drawable.ic_people,
        R.drawable.ic_account
    )
    Row(
        modifier = Modifier.fillMaxWidth().padding(10.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEachIndexed { index, title ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable {onItemSelected(index)}
            ) {
                if (selectedItem == index) {
                    HorizontalDivider(
                        modifier = Modifier.width(30.dp),
                        thickness = 3.dp,
                        color = JungleGreen
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Icon(
                    painterResource(id = icons[index]),
                    contentDescription = title,
                    tint = if (selectedItem == index) JungleGreen else LightGrey,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = title,
                    style = TextStyle(fontFamily = fontFamily, fontSize = 11.sp, fontWeight = if (selectedItem == index) FontWeight.SemiBold else FontWeight.Normal, color = if (selectedItem == index) JungleGreen else LightGrey),
                )
            }
        }
    }
}
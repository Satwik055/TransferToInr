package com.satwik.transfertoinr.core.designsystem.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.satwik.transfertoinr.R
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.LightGrey
import com.satwik.transfertoinr.core.designsystem.theme.VeryLightGrey
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily

@Composable
fun TTFBottomNavigationBar(
    selectedItem: Int,
    onItemSelected: (Int) ->Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }


    val items = listOf("Home", "Transfer", "Transaction", "Recipient", "Account")
    val icons = listOf(
        R.drawable.ic_home,
        R.drawable.ic_plane,
        R.drawable.ic_page,
        R.drawable.ic_people,
        R.drawable.ic_account
    )

    Column{
        HorizontalDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp, start = 10.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, title ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) { onItemSelected(index) }
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
}


@Composable
fun TTFBottomNavigationBarz(
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

    val picons = listOf(
        R.drawable.ic_home to R.drawable.ic_home_filled,
        R.drawable.ic_plane to R.drawable.ic_plane_filled,
        R.drawable.ic_page to R.drawable.ic_page_filled,
        R.drawable.ic_people to R.drawable.ic_people_filled,
        R.drawable.ic_account to R.drawable.ic_account_filled
    )

    Column {
        HorizontalDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp, start = 10.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, title ->
                val indicatorWidth by animateFloatAsState(targetValue = if (selectedItem == index) 30f else 0f, label = "")
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { onItemSelected(index) }
                ) {
                    Box(
                        modifier = Modifier
                            .width(indicatorWidth.dp)
                            .height(3.dp)
                            .background(JungleGreen, RoundedCornerShape(50))
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Icon(
                        painterResource(id = if (selectedItem == index) picons[index].second else picons[index].first),
                        contentDescription = title,
                        tint = if (selectedItem == index) JungleGreen else LightGrey,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = title,
                        style = TextStyle(
                            fontFamily = fontFamily,
                            fontSize = 11.sp,
                            fontWeight = if (selectedItem == index) FontWeight.SemiBold else FontWeight.Normal,
                            color = if (selectedItem == index) JungleGreen else LightGrey
                        ),
                    )
                }
            }
        }
    }
}


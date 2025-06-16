package com.satwik.transfertoinr.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.VeryLightGrey

@Composable
fun Carousell(modifier: Modifier = Modifier) {

    val links:List<String> = listOf(
        "https://img.freepik.com/free-vector/flat-abstract-sales-banner-with-offer_23-2149020199.jpg",
        "https://img.freepik.com/free-vector/flat-abstract-sales-banner-with-offer_23-2149020199.jpg",
        "https://img.freepik.com/free-vector/flat-abstract-sales-banner-with-offer_23-2149020199.jpg"
        )

    Column {
        LazyRow (
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ){
            items(links){
                CoilImage(link = it)
            }
        }

    }



}


@Composable
fun CoilImage(modifier: Modifier = Modifier, link:String) {

    Surface(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.size(height = 172.dp, width = 287.dp)
    ) {
        AsyncImage(
            model = link,
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = modifier.background(shape = RoundedCornerShape(8.dp), color = Color.Red)
        )
    }
}

@Composable
fun PageIndicator(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    pageCount: Int,
    inactiveColor: Color = VeryLightGrey,
    activeColor: Color = JungleGreen
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until pageCount) {
            val color = if (i == pagerState.currentPage) activeColor else inactiveColor
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color)
                    .size(8.dp)
            )
        }
    }
}


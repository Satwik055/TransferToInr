package com.satwik.transfertoinr.core.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.satwik.transfertoinr.R
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.VeryLightGrey
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily

@Composable
fun RateTable() {
    val providerLogos = listOf(
        R.drawable.ic_ttf,
        R.drawable.ic_wise,
        R.drawable.ic_skrill,
        R.drawable.ic_paypal,
        R.drawable.ic_bank
    )

    val tableData = listOf(
        listOf("Send", "Receive", "Fees"),
        listOf("1 €", "93.00 ₹", "0 €"),
        listOf("1 €", "89.89 ₹", "1.71 €"),
        listOf("1 €", "89.24 ₹", "0.99 €"),
        listOf("1 €", "88.36 ₹", "1.99 €"),
        listOf("1 €", "85.00 ₹", "-"),
    )

    val textStyle = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Normal, fontFamily = fontFamily)

    Column(
        modifier = Modifier.border(1.dp, JungleGreen, RoundedCornerShape(8.dp))
    ) {
        // Header row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(JungleGreen, shape = RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp))
            ,
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Provider",
                style = textStyle.copy(fontWeight = FontWeight.Medium, color = Color.White),
                modifier = Modifier.weight(1f).padding(vertical = 16.dp, horizontal = 18.dp)
            )
            tableData[0].forEach { header ->
                Text(
                    text = header,
                    style = textStyle.copy(fontWeight = FontWeight.Medium, color = Color.White),
                    modifier = Modifier.weight(1f).padding(vertical = 16.dp, horizontal = 20.dp)
                )
            }
        }

        // Data rows
        tableData.drop(1).forEachIndexed { index, row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = providerLogos[index]),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp).weight(1f)
                )
                row.forEach { cell ->
                    Text(
                        text = cell,
                        style = textStyle,
                        modifier = Modifier.weight(1f).padding(vertical = 16.dp, horizontal = 20.dp),
                        color = JungleGreen
                    )
                }
            }
            if (index < providerLogos.size - 1) {
                HorizontalDivider(color = VeryLightGrey, thickness = 1.dp)
            }
        }
    }
}



@Preview
@Composable
fun PreviewExchangeRateTable() {
    RateTable()
}


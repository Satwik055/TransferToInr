package com.satwik.transfertoinr.features.transfer.new

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.satwik.transfertoinr.core.designsystem.components.TTFButton
import com.satwik.transfertoinr.core.designsystem.components.TTFTextField
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily
import com.satwik.transfertoinr.core.main.ScreenSelectRecipient

@Composable
fun AmountScreen(navController: NavController) {

    val style = TextStyle(fontFamily = fontFamily, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)

    var amountSend by remember { mutableStateOf("") }
    var amountReceive by remember { mutableStateOf("") }

    Column {
        Text(text = "You Send", style=style)
        Spacer(modifier = Modifier.height(5.dp))
        TTFTextField(text = amountSend, onValueChange = {amountSend = it}, placeholder = "Eg: 100" )

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)){
            OperationRow(symbol = "-", text = "Our Fees", amount = "1.00")
            OperationRow(symbol = "=", text = "Net Amount", amount = "900")
            OperationRow(symbol = "x", text = "Guaranteed Amount", amount = "10000")
        }
        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Recipient Gets", style=style)
        Spacer(modifier = Modifier.height(5.dp))
        TTFTextField(text = amountReceive, onValueChange = {amountReceive = it}, placeholder = "Eg: 1000" )

        Spacer(modifier = Modifier.weight(1f))

        TTFButton(text = "Continue", onClick = { navController.navigate(ScreenSelectRecipient) })


    }

}

@Composable
fun SymbolCircle(modifier: Modifier = Modifier, symbol: String) {
    val style = TextStyle(fontFamily = fontFamily, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)

    Box (
        modifier = Modifier
            .size(30.dp)
            .border(
                shape = RoundedCornerShape(100.dp),
                border = BorderStroke(1.dp, color = JungleGreen)
            )){
        Text(text = symbol, style = style, modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun OperationRow(modifier: Modifier = Modifier, symbol: String, text:String, amount:String) {
    
    val style2 = TextStyle(fontFamily = fontFamily, fontSize = 14.sp, fontWeight = FontWeight.Medium)

    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()){
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        )
        {
            SymbolCircle(symbol = symbol)
            Text(text = amount, style = style2)
        }
        Text(text = text, style = style2)
    }
    
}
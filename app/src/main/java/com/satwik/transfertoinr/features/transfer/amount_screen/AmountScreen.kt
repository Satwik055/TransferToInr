package com.satwik.transfertoinr.features.transfer.amount_screen

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.satwik.transfertoinr.core.designsystem.components.CompleteKYCDialog
import com.satwik.transfertoinr.core.designsystem.components.TTFButton
import com.satwik.transfertoinr.core.designsystem.components.TTFTextField
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily
import com.satwik.transfertoinr.core.main.ScreenKyc
import com.satwik.transfertoinr.core.main.ScreenMain
import com.satwik.transfertoinr.core.main.ScreenSelectRecipient
import com.satwik.transfertoinr.core.utils.getCurrencySymbol
import com.satwik.transfertoinr.core.utils.roundToTwoDecimalPlaces
import com.satwik.transfertoinr.features.transfer.shared_viewmodel.TransferSharedViewModel
import org.koin.androidx.compose.koinViewModel
import kotlin.time.times

@Composable
fun AmountScreen(navController: NavController, transferSharedViewModel: TransferSharedViewModel) {

    val user = transferSharedViewModel.userInfoState.collectAsState().value.profile

//    if(!user.kyc_status){
//        CompleteKYCDialog(
//            onDismissRequest = { },
//            onButtonClicked = { navController.navigate(ScreenKyc) },
//            onCancelClicked = {navController.navigate(ScreenMain)}
//        )
//    }

    Content(transferSharedViewModel = transferSharedViewModel, navController = navController )

}


@Composable
private fun Content(modifier: Modifier = Modifier, transferSharedViewModel: TransferSharedViewModel, navController: NavController) {

    val style = TextStyle(fontFamily = fontFamily, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
    var amountSend by remember { mutableStateOf("") }
    var errorText by remember { mutableStateOf("") }

    val amountScreenViewModel = koinViewModel<AmountScreenViewModel>()

    val user = transferSharedViewModel.userInfoState.collectAsState().value
    val symbol = getCurrencySymbol(user.profile.preferred_currency)
    val ttiRateResult = amountScreenViewModel.ttiRateResult.collectAsState().value
    val rate = ttiRateResult.successResult.toString().toDoubleOrNull()?:0.00
    var amountReceive = roundToTwoDecimalPlaces((amountSend.toDoubleOrNull()?: 0.00) * rate )


    Column (modifier = modifier){
        Text(text = "You Send", style=style)
        Spacer(modifier = Modifier.height(5.dp))

        TTFTextField(
            text = amountSend,
            isError = errorText.isNotEmpty(),
            errorText = errorText,
            onValueChange = { amountSend = it },
            placeholder = "Eg: 100$symbol",
            keyboardType = KeyboardType.Number
        )
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)){
            OperationRow(symbol = "-", text = "Our Fees", amount = "0.00$symbol (OFFER)")
            OperationRow(symbol = "=", text = "Net Amount", amount = if(amountSend=="") "0$amountSend" else "$amountSend$symbol")
            OperationRow(symbol = "x", text = "Our Rate (Google + 1)", amount = "₹$rate")
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Recipient Gets", style=style)

        Spacer(modifier = Modifier.height(5.dp))
        TTFTextField(text = "₹$amountReceive", onValueChange = {amountReceive = it.toDoubleOrNull()?:0.00}, placeholder = "Eg: 1000", keyboardType = KeyboardType.Number, enabled = false)

        Spacer(modifier = Modifier.weight(1f))
        TTFButton(
            text = "Continue",
            isLoading = ttiRateResult.isLoading,
            onClick = {
                if (amountSend.isEmpty()) {
                    errorText = "Please enter the amount"
                } else {
                    transferSharedViewModel.setSendAmount(amountSend.toInt())
                    transferSharedViewModel.setReceiveAmount(amountReceive.toInt())
                    navController.navigate(ScreenSelectRecipient)
                }
            }

        )
    }
}



@Composable
fun SymbolCircle(symbol: String) {
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
fun OperationRow(symbol: String, text:String, amount:String) {

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



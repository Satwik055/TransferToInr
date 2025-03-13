package com.satwik.transfertoinr.features.transfer.summary_screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.satwik.transfertoinr.core.designsystem.components.TTFButton
import com.satwik.transfertoinr.core.designsystem.components.headers.TTFTextHeader
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.VeryLightGrey
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily
import com.satwik.transfertoinr.core.main.ScreenPayment
import com.satwik.transfertoinr.features.transfer.shared_viewmodel.TransferSharedViewModel

@Composable
fun SummaryScreen(navController: NavController, viewModel: TransferSharedViewModel) {

    Column {
        TTFTextHeader(text = "SUMMARY", isBackButtonEnabled = true, onBackClick = {navController.popBackStack()})
        Content(viewModel = viewModel, navController = navController)
    }


}

@Composable
fun ReviewTransactionTable(modifier: Modifier = Modifier, recipientName: String, accountNumber: String, ifscCode: String, youSend: String, recipientReceived: String, exchangeRate: String, ourFee: String, purpose: String) {
    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(8.dp),
                color = VeryLightGrey
            )
            .padding(16.dp)
    ) {
        val style = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Medium, fontSize = 13.sp)
        val style1 = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Recipient Name", style = style)
            Text(text = recipientName, style = style1)
        }
        HorizontalDivider()
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Account Number", style = style)
            Text(text = accountNumber, style = style1)
        }
        HorizontalDivider()
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "IFSC Code", style = style)
            Text(text = ifscCode, style = style1)
        }
        HorizontalDivider()
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "You Send", style = style)
            Text(text = youSend, style = style1)
        }
        HorizontalDivider()
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Recipient Received", style = style)
            Text(text = recipientReceived, style = style1)
        }
        HorizontalDivider()
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = "Exchange Rate", style = style)
            Text(text = exchangeRate, style = style1)
        }
        HorizontalDivider()
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = "Our Fee", style = style)
            Text(text = ourFee, style = style1)
        }
        HorizontalDivider()
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = "Purpose", style = style)
            Text(text = purpose , style = style1)
        }
    }
}

@Composable
private fun Content(modifier: Modifier = Modifier, viewModel: TransferSharedViewModel, navController: NavController) {

    Column (modifier = Modifier.padding(16.dp)){

        val transaction = viewModel.transactionSummary.collectAsState().value

        val style = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
        val style1 = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Medium, fontSize = 13.sp)

        Text(text = "Confirm Transfer Details", style = style)
        Text(text = "Please review all details carefully", style = style1)
        Spacer(modifier = Modifier.weight(1f))
        ReviewTransactionTable(
            recipientName = transaction.recipient.name,
            accountNumber = transaction.recipient.account_number,
            ifscCode = transaction.recipient.ifsc_code,
            youSend = transaction.send.toString(),
            recipientReceived = transaction.send.toString(),
            exchangeRate = transaction.exchangeRate.toString(),
            ourFee = transaction.fee.toString(),
            purpose = transaction.reason
        )

        Spacer(modifier = Modifier.weight(1f))
        TTFButton(text = "Confirm", onClick = {navController.navigate(ScreenPayment)})
        Spacer(modifier = Modifier.height(5.dp))
        TTFButton(text = "Cancel", onClick = { /*TODO*/ }, color = Color.White, textColor = JungleGreen)

    }

}
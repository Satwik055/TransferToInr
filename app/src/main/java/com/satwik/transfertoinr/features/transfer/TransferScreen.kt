package com.satwik.transfertoinr.features.transfer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.satwik.transfertoinr.core.designsystem.components.TTFButton
import com.satwik.transfertoinr.core.designsystem.components.TTFDropdown
import com.satwik.transfertoinr.core.designsystem.components.TTFTextField
import com.satwik.transfertoinr.core.designsystem.theme.LightGrey
import com.satwik.transfertoinr.core.designsystem.theme.VeryLightGrey
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily
import org.koin.androidx.compose.koinViewModel

@Composable
fun TransferScreen(modifier: Modifier = Modifier) {
    Content(modifier = Modifier.fillMaxSize())
}

private val style = TextStyle(fontFamily = fontFamily, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
private val style2 = TextStyle(fontFamily = fontFamily, fontSize = 14.sp, fontWeight = FontWeight.Normal)

@Composable
internal fun Content(modifier: Modifier = Modifier) {

    val viewModel = koinViewModel<TransferScreenViewModel>()
    var amount by remember { mutableStateOf("") }
    var selectedCurrency by remember { mutableStateOf("Euro" to "EUR") }
    val transactionCode = generateTransactionCode()

    val recipientList = viewModel.recipientsState.value.recipients.map { it.name to it.bank }

    val currencies = listOf(
        "Euro" to "EUR",
        "US Dollar" to "USD",
        "British Pound" to "GBP",
        "Canadian Dollar" to "CAD",
        "Australian Dollar" to "AUD"
    )

    var selectedRecipient by remember {
        mutableStateOf(
            if (recipientList.isEmpty()) Pair("", "") else recipientList.first()
        )
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
            Text(text = "Select a currency", style = style)
            TTFDropdown(
                items = currencies,
                modifier = Modifier.fillMaxWidth(),
                selectedItem = selectedCurrency,
                onItemSelected = { selectedCurrency = it }
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(7.dp)){
            Text(text = "Enter the amount", style = style)
            TTFTextField(
                text = amount,
                onValueChange = { amount = it },
                placeholder = "Eg: 100",
                keyboardType = KeyboardType.Number
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
            Text(text = "Select a Recipient", style = style)
            TTFDropdown(
                items = recipientList,
                modifier = Modifier.fillMaxWidth(),
                selectedItem = selectedRecipient,
                onItemSelected = { selectedRecipient = it }
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
            Text(text = "Send money to below bank details", style = style)
            BankDetailsBox(
                modifier = Modifier.fillMaxWidth(),
                accountName = "transfertoinr",
                accountNumber = "3432 3434 6562",
                ifscCode = "DV3G4FF"
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
            Text(text = "Enter this ID in  description while making payment", style = style)
            TransactionIDBox(id = transactionCode, modifier = Modifier.fillMaxWidth())
        }
        
        TTFButton(text = "Submit", onClick = {viewModel.addTransaction(transactionCode, amount.toInt(), amount.toInt().inc(), "USD", "email")})
    }
}

@Composable
private fun TransactionIDBox(modifier: Modifier = Modifier, id: String) {
    Box(
        modifier
            .background(color = VeryLightGrey, shape = RoundedCornerShape(4.dp))
            .padding(16.dp),
    ) {
        Text(text = id, style = style2)
    }
}

@Composable
private fun BankDetailsBox(
    modifier: Modifier = Modifier,
    accountName: String,
    accountNumber: String,
    ifscCode: String,
) {
    Column(
        modifier
            .border(
                width = 1.dp,
                color = LightGrey,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(13.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)

    ) {
        Text(text = "Name: $accountName", style = style2)
        Text(text = "Account Number: $accountNumber", style = style2)
        Text(text = "IFSC Code: $ifscCode", style = style2)
    }

}

fun generateTransactionCode(): String {
    val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    return (1..6)
        .map { chars.random() }
        .joinToString("")
}


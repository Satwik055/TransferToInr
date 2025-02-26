package com.satwik.transfertoinr.features.transfer

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.satwik.transfertoinr.R
import com.satwik.transfertoinr.core.designsystem.components.NewTTFDropdown
import com.satwik.transfertoinr.core.designsystem.components.TTFButton
import com.satwik.transfertoinr.core.designsystem.components.TTFDropdown
import com.satwik.transfertoinr.core.designsystem.components.TTFTextField
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.LightGrey
import com.satwik.transfertoinr.core.designsystem.theme.VeryLightGrey
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily
import com.satwik.transfertoinr.core.model.CurrencyType
import com.satwik.transfertoinr.core.utils.copyToClipboard
import com.satwik.transfertoinr.core.utils.generateTransactionCode
import org.koin.androidx.compose.koinViewModel

@Composable
fun TransferScreen() {
    Content(modifier = Modifier.fillMaxSize())
}

private val style = TextStyle(fontFamily = fontFamily, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
private val style2 = TextStyle(fontFamily = fontFamily, fontSize = 13.sp, fontWeight = FontWeight.Normal)
@Composable
internal fun Content(modifier: Modifier = Modifier) {

    val viewModel = koinViewModel<TransferScreenViewModel>()
    var amount by remember { mutableStateOf("") }
    var transactionCode by remember { mutableStateOf("") }
    val ttirate = viewModel.ttiRate.collectAsState().value
    val amountReceive = ttirate * (amount.toIntOrNull() ?: 0)
    val context = LocalContext.current
    val kycStatus= viewModel.kycStatus.collectAsState().value


    val preferredCurrency = viewModel.prefferedCurrency.collectAsState().value

    val reasons = listOf("My NRE/NRO Account", "Savings & Family Support", "Real Estate/Housing Societies", "Educational Institutions", "Tax Payment", "Hospitals/Healthcare Providers", "Travel/Tourism Partners", "Utility Bill Payments", "Loan Account Payment")
    var selectedReason by remember { mutableStateOf(reasons.first()) }


    LaunchedEffect(Unit) {
        viewModel.getTtiRate()
        viewModel.getAllRecipients()
        viewModel.getPreferredCurrency()
    }


    LaunchedEffect (Unit){
        transactionCode = generateTransactionCode()
    }

    val recipientList = viewModel.recipientsState.value.recipients.map { it.name to it.bank }

    var selectedRecipient by remember {
        mutableStateOf(
            if (recipientList.isEmpty()) Pair("", "") else recipientList.first()
        )
    }


    Column(
        modifier = modifier,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(7.dp)){
            Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)){
                NumberCircle(number = 1)
                Text(text = "Enter the amount", style = style)
            }
            TTFTextField(
                text = amount,
//                errorText = "something went wrong if you want it",
                onValueChange = { amount = it },
                placeholder = "Eg: 100",
                keyboardType = KeyboardType.Number
            )
            Text(
                text = "Amount Recieve: ₹$amountReceive",
                style = style.copy(fontWeight = FontWeight.Medium, fontSize = 13.sp),
                modifier = Modifier
                    .align(Alignment.End)
                    .offset(y = -(19.dp))
            )


        }


        Column (
            modifier = Modifier.offset(y=-(18).dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){

            Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
                Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)){
                    NumberCircle(number = 2)
                    Text(text = "Select a recipient", style = style)
                }
                TTFDropdown(
                    items = recipientList,
                    modifier = Modifier.fillMaxWidth(),
                    selectedItem = selectedRecipient,
                    onItemSelected = { selectedRecipient = it }
                )

            }

            Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
                Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)){
                    NumberCircle(number = 3)
                    Text(text = "Send money to below bank details", style = style)
                }
                BankDetailsBox(
                    modifier = Modifier.fillMaxWidth(),
                    context = context,
                    currency = preferredCurrency
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
                Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)){
                    NumberCircle(number = 4)
                    Text(text = "Enter this ID in while making payment", style = style)
                }
                TransactionIDBox(id = transactionCode, modifier = Modifier.fillMaxWidth())
            }


            Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
                Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)){
                    NumberCircle(number = 5)
                    Text(text = "Select a reason for payment", style = style)
                }
                NewTTFDropdown(items = reasons, selectedItem = selectedReason, onItemSelected = { selectedReason =it} )
            }


            TTFButton(
                text = "Submit",
                onClick = {
                    if(!kycStatus){
                        Toast.makeText(context, "Please verify your KYC", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        viewModel.addTransaction(transactionCode, amount.toInt(), reason = selectedReason)
                        transactionCode = generateTransactionCode() //new code after submitting
                        amount = "0"
                    }
                })
        }
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
    currency: CurrencyType,
    modifier: Modifier = Modifier,
    context: Context
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

        when(currency){
            CurrencyType.USD ->
                {
                    CopyableText(title = "Name", copyableText = "Swathi Police", context = context)
                    CopyableText(title = "Account Number", copyableText = "8314603017", context = context)
                    CopyableText(title = "Account Type", copyableText = "Checking", context = context)
                    CopyableText(title = "Routing Number", copyableText = "026073150", context = context)

                }
            CurrencyType.EUR ->
                {
                    CopyableText(title = "Name", copyableText = "Shyam Sundhar Reddy Sappidi", context = context)
                    CopyableText(title = "IBAN", copyableText = "DE89 5002 4024 6773 8830 01", context = context)
                    CopyableText(title = "BIC", copyableText = "DEFFDEFFXXX", context = context)
                }
            CurrencyType.GBP ->
                {
                    CopyableText(title = "Name", copyableText = "Swathi Police", context = context)
                    CopyableText(title = "Account Number", copyableText = "31118288", context = context)
                    CopyableText(title = "Sort Code", copyableText = "23-08-01", context = context)
                    CopyableText(title = "IBAN", copyableText = "GB95 TRWI 2308 0131 1182 88", context = context)
                }
            CurrencyType.AUD ->
                {
                    CopyableText(title = "Name", copyableText = "Swathi Police", context = context)
                    CopyableText(title = "Account Number", copyableText = "220336755", context = context)
                    CopyableText(title = "BSB", copyableText = "774001", context = context)
                }
            CurrencyType.CAD ->
                {
                    CopyableText(title = "Name", copyableText = "Swathi Police", context = context)
                    CopyableText(title = "Account Number", copyableText = "200116298434", context = context)
                    CopyableText(title = "Institution Number", copyableText = "621", context = context)
                    CopyableText(title = "Transit Number", copyableText = "16001", context = context)
                }
        }
    }
}






@Composable
fun NumberCircle(modifier: Modifier = Modifier, number: Int) {
    Box(
        modifier = modifier
            .background(shape = RoundedCornerShape(100.dp), color = JungleGreen)
            .size(25.dp)
    ) {
        Text(
            text = number.toString(),
            modifier = Modifier.align(Alignment.Center),
            style = style.copy(color = Color.White, fontSize = 15.sp),
        )
    }
}


@Composable
fun CopyableText(title:String, modifier: Modifier = Modifier, copyableText: String, context: Context) {
    Row (horizontalArrangement = Arrangement.spacedBy(5.dp), modifier = modifier){
        Text(text = "$title: $copyableText", style = style2)
        Icon(
            painter = painterResource(id = R.drawable.ic_copy),
            tint = JungleGreen,
            contentDescription = null,
            modifier = Modifier
                .size(15.dp)
                .clickable { copyToClipboard(context as Activity, text = copyableText) })
    }
}


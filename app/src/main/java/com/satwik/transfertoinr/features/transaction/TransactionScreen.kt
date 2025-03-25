package com.satwik.transfertoinr.features.transaction

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.Mustard
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily
import com.satwik.transfertoinr.core.main.ScreenHelp
import com.satwik.transfertoinr.core.model.CurrencyType
import com.satwik.transfertoinr.core.model.TransactionStatus
import com.satwik.transfertoinr.core.utils.formatTimestamp
import com.satwik.transfertoinr.core.utils.getCurrencySymbol
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TransactionScreen(navController: NavController) {
    Content(modifier = Modifier.fillMaxSize(), navController)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
internal fun Content(modifier: Modifier = Modifier, navController: NavController) {
    val style1 = TextStyle(fontWeight = FontWeight.Normal, fontFamily = fontFamily, fontSize = 13.sp, color = JungleGreen)
    val viewModel = koinViewModel<TransactionViewModel>()
    val state = viewModel.transactionState.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.getPreferredCurrency()
    }

    val preferredCurrency = viewModel.prefferedCurrency.value

    Box (modifier = modifier){
        if(state.isLoading){
            CircularProgressIndicator(color = JungleGreen, modifier = Modifier.align(Alignment.Center))
        }
        if(state.error.isNotEmpty()&&!state.isLoading){
            Text(text = state.error, style = style1.copy(fontSize = 14.sp), modifier = Modifier.align(Alignment.Center))
        }
        if(state.transaction.isEmpty()){
            Text(text = "No transaction found", style = style1.copy(fontSize = 14.sp), modifier = Modifier.align(Alignment.Center))
        }

        else{
            Column {
                TableHeader()
                HorizontalDivider(Modifier.padding(5.dp))
                LazyColumn{
                    items(state.transaction) {
                        TransactionEntry (
                            modifier = Modifier.fillMaxWidth(),
                            date = formatTimestamp(it.date),
                            id = it.transaction_code,
                            sent = it.sent.toString(),
                            received = it.receive.toString(),
                            status = it.status,
                            style = style1,
                            currency = it.currency
                        )
                    }
                }
            }
            ContactUsRibbon(modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter), onClick = {navController.navigate(ScreenHelp)})
        }
    }
}

@Composable
fun TransactionEntry(modifier: Modifier = Modifier, date:String, id:String,sent:String, received:String, style:TextStyle, status: TransactionStatus, currency: CurrencyType) {
    val symbol = getCurrencySymbol(currency)
    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(text = date, style = style, modifier = Modifier
            .weight(1f)
            .padding(vertical = 8.dp, horizontal = 1.dp))
        Text(text = id, style = style,modifier = Modifier
            .weight(1f)
            .padding(vertical = 8.dp, horizontal = 5.dp))
        Text(text = sent+symbol, style = style, modifier = Modifier
            .weight(1f)
            .padding(vertical = 8.dp, horizontal = 8.dp))
        Text(text = "₹$received", style = style, modifier = Modifier
            .weight(1f)
            .padding(8.dp))
        Text(text = status.name, style = style.copy(fontWeight = FontWeight.Medium),modifier = Modifier
            .weight(1f)
            .padding(vertical = 8.dp, horizontal = 7.dp),
            color = when(status){
                TransactionStatus.SUCCESS -> Color.Green
                TransactionStatus.FAILED -> Color.Red
                TransactionStatus.PENDING -> Mustard

            }
        )
    }
}

@Composable
fun TableHeader(modifier: Modifier = Modifier) {
    val style = TextStyle(fontWeight = FontWeight.SemiBold, fontFamily = fontFamily, fontSize = 15.sp, color = JungleGreen)
    Row(
        modifier =  modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(22.dp),
    ){
        Text(text = "Date", style = style, modifier = Modifier.padding(8.dp) )
        Text(text = "ID", style = style, modifier = Modifier.padding(8.dp) )
        Text(text = "Sent", style = style, modifier = Modifier.padding(8.dp) )
        Text(text = "Receive", style = style, modifier = Modifier.padding(8.dp) )
        Text(text = "Status", style = style, modifier = Modifier.padding(8.dp) )
    }

}


@Composable
fun ContactUsRibbon(modifier: Modifier = Modifier, onClick: () -> Unit) {
    val style = TextStyle(fontFamily = fontFamily, fontSize = 13.sp, fontWeight = FontWeight.Normal, color = Color.White)
    Box (
        modifier = modifier.background(color = JungleGreen, shape = RoundedCornerShape(5.dp)).clickable { onClick.invoke() }
    ){
        Text(
            modifier = modifier.padding(14.dp),
            style = style,
            text = buildAnnotatedString {
                append("Having Issues ?")
                withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold, textDecoration = TextDecoration.Underline)){
                    append(" Contact Us")
                }
            },
        )
    }
}

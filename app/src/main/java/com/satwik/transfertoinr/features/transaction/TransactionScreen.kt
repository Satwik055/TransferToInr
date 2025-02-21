package com.satwik.transfertoinr.features.transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.satwik.transfertoinr.core.designsystem.components.TTFTextHeader
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.Mustard
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TransactionScreen(modifier: Modifier = Modifier) {
    Column (
        modifier = modifier.fillMaxSize()
    ){
        TTFTextHeader(text = "Transaction")
        Content(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp))
    }
}

@Composable
private fun Content(modifier: Modifier = Modifier) {
    val style1 = TextStyle(fontWeight = FontWeight.Normal, fontFamily = fontFamily, fontSize = 13.sp, color = JungleGreen)
    val viewModel = koinViewModel<TransactionViewModel>()

    Box (modifier = modifier){
        if(viewModel.transactionState.value.isLoading){
            CircularProgressIndicator(color = JungleGreen, modifier = Modifier.align(Alignment.Center))
        }
        if(viewModel.transactionState.value.error.isNotEmpty()){
            Text(text = viewModel.transactionState.value.error, style = style1.copy(fontSize = 14.sp), modifier = Modifier.align(Alignment.Center))
        }
        else{
            TransactionTable(transactions = viewModel.transactionState.value.transaction)
//            LazyColumn{
//                items(viewModel.transactionState.value.transaction){
//                    TransactionEntry(
//                        modifier = Modifier.fillMaxWidth(),
//                        date = it.date,
//                        id = it.transaction_code,
//                        sent = it.sent.toString(),
//                        received = it.receive.toString(),
//                        status = TransactionStatus.PENDING,
//                        style = style1
//                    )
//                }
//            }
        }
    }
}

enum class TransactionStatus{
    PENDING,
    SUCCESS,
    FAILED
}

@Composable
fun TransactionEntry(modifier: Modifier = Modifier, date:String, id:String,sent:String, received:String, style:TextStyle, status: TransactionStatus) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = date, style = style)
        Text(text = id, style = style)
        Text(text = sent, style = style)
        Text(text = received, style = style)
        Text(text = status.name, style = style,
            color = when(status){
                TransactionStatus.SUCCESS -> JungleGreen
                TransactionStatus.FAILED -> Color.Red
                TransactionStatus.PENDING -> Mustard

            }
        )
    }
}



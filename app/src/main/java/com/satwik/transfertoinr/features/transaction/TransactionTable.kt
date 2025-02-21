package com.satwik.transfertoinr.features.transaction

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.model.Transaction
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TransactionTable(transactions: List<Transaction>) {
    
    Column {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 18.dp), horizontalArrangement = Arrangement.spacedBy(22.dp)) {
            TableHeader("Date")
            TableHeader("ID")
            TableHeader("Sent")
            TableHeader("Receive")
            TableHeader("Status")
        }
        HorizontalDivider()
        transactions.forEach { transaction ->
            TableRow(transaction)
        }
    }
}

@Composable
fun TableHeader(text: String) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        modifier = Modifier.padding(8.dp)
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TableRow(transaction: Transaction) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(text = formatDateString(transaction.date), modifier = Modifier.weight(1f).padding(8.dp))
        Text(text = transaction.transaction_code , modifier = Modifier.weight(1f).padding(8.dp))
        Text(text = transaction.sent.toString(), modifier = Modifier.weight(1f).padding(8.dp))
        Text(text = transaction.receive.toString(), modifier = Modifier.weight(1f).padding(8.dp))
        Text(
            text = transaction.status,
            color = JungleGreen,
            fontWeight = FontWeight.Normal,
            fontSize = 13.sp,
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDateString(input: String): String {
    try {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSX")
        val outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
        val zonedDateTime = ZonedDateTime.parse(input, inputFormatter)
        return zonedDateTime.format(outputFormatter)
    } catch (e: Exception) {
        println("Error parsing date: ${e.message}")
        return "Invalid Date"
    }
}
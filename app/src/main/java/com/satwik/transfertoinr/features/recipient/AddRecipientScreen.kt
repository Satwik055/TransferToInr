package com.satwik.transfertoinr.features.recipient

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.satwik.transfertoinr.core.designsystem.components.TTFButton
import com.satwik.transfertoinr.core.designsystem.components.TTFTextField
import com.satwik.transfertoinr.core.designsystem.components.TTFTextHeader
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddRecipientScreen(modifier: Modifier = Modifier) {

    val viewModel = koinViewModel<RecipientViewModel>()

    var name by remember { mutableStateOf("") }
    var accountNumber by remember { mutableStateOf("") }
    var ifscCode by remember { mutableStateOf("") }
    var bank by remember { mutableStateOf("") }

    Box(
        Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp)){
        TTFTextHeader(text = "Add Recipient")

        Column (
            Modifier
                .padding(16.dp)
                .align(Alignment.Center)){
            TTFTextField(text = name, onValueChange = {name=it}, placeholder = "Recipient Name")
            TTFTextField(text = accountNumber, onValueChange = {accountNumber=it}, placeholder = "Account Number")
            TTFTextField(text = ifscCode, onValueChange = {ifscCode=it}, placeholder = "IFSC Code")
            TTFTextField(text = bank, onValueChange = {bank=it}, placeholder = "Bank")

        }
        TTFButton(
            modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(horizontal = 16.dp),
            text = "Add",
            onClick = {
                viewModel.addRecipient(name = name, accountNumber = accountNumber, ifscCode = ifscCode, bank = bank)
            }
        )
    }
}
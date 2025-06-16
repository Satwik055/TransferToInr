package com.ttf.transfertoinr.features.transfer.select_recipient_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ttf.transfertoinr.core.designsystem.components.TTFButton
import com.ttf.transfertoinr.core.designsystem.components.headers.TTFTextHeader
import com.ttf.transfertoinr.core.designsystem.theme.JungleGreen
import com.ttf.transfertoinr.core.designsystem.theme.fontFamily
import com.ttf.transfertoinr.core.main.ScreenAddRecipient
import com.ttf.transfertoinr.features.recipient.RecipientListItem
import com.ttf.transfertoinr.features.transfer.shared_viewmodel.TransferSharedViewModel
import com.ttf.transfertoinr.features.transfer.select_reason_sheet.SelectReasonSheet
import org.koin.androidx.compose.koinViewModel

@Composable
fun SelectRecipientScreen(navController: NavController, transferSharedViewModel: TransferSharedViewModel) {

    val selectRecipientViewModel = koinViewModel<SelectRecipientViewModel>()
    val state = selectRecipientViewModel.recipientsState.collectAsState().value

    Column (
        modifier = Modifier
        .fillMaxSize()
    ){
        TTFTextHeader(text = "SELECT RECIPIENT", isBackButtonEnabled = true, onBackClick = {navController.popBackStack()})
        Box(modifier = Modifier.fillMaxSize()){
            if(state.isLoading){
                CircularProgressIndicator(color = JungleGreen, modifier = Modifier.align(Alignment.Center))
            }
            if(state.recipients.isEmpty() && !state.isLoading){
                val style1 = TextStyle(fontWeight = FontWeight.Normal, fontFamily = fontFamily, fontSize = 14.sp, color = JungleGreen)
                Text(text = "No recipient found", style = style1, modifier = Modifier.align(Alignment.Center))
                TTFButton(
                    text = "Add Recipient",
                    modifier = Modifier.align(Alignment.BottomCenter).padding(start = 20.dp, end = 20.dp, bottom = 40.dp),
                    onClick = {
                        navController.navigate(ScreenAddRecipient)
                    }
                )
            }

            if(state.error.isNotEmpty()){
                Text(text = state.error, modifier = Modifier.align(Alignment.Center))
            }
            if(state.recipients.isNotEmpty()){
                Content(
                    navController = navController,
                    selectRecipientViewModel = selectRecipientViewModel,
                    transferSharedViewModel = transferSharedViewModel,
                    modifier = Modifier.padding(16.dp)
                )
            }

        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(modifier: Modifier = Modifier, navController: NavController, transferSharedViewModel: TransferSharedViewModel, selectRecipientViewModel: SelectRecipientViewModel) {

    val state = selectRecipientViewModel.recipientsState.collectAsState().value

    val recipients = state.recipients
    var selectedRecipient by remember { mutableStateOf(recipients.first()) }

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Column (
        modifier = modifier
    ){
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(recipients) { recipient ->
                RecipientListItem(
                    name = recipient.name,
                    accountNumber = recipient.account_number,
                    bankName = recipient.bank,
                    isSelected = recipient == selectedRecipient,
                    onSelect = { selectedRecipient = recipient },
                    radioEnabled = true,
                    onConfirm = {},
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        TTFButton(
            text = "Add Recipient",
            onClick = {
                navController.navigate(ScreenAddRecipient)
            }
        )


        Spacer(modifier = Modifier.height(5.dp))
        TTFButton(
            text = "Continue",
            onClick = {
                transferSharedViewModel.setRecipient(selectedRecipient)
                showBottomSheet = true
            }
        )

        if (showBottomSheet) {
            ModalBottomSheet(
                containerColor = Color.White,
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                SelectReasonSheet(navController = navController, viewModel = transferSharedViewModel)
            }
        }
    }
}


//Button(onClick = {
//    scope.launch { sheetState.hide() }.invokeOnCompletion {
//        if (!sheetState.isVisible) {
//            showBottomSheet = false
//        }
//    }
//}) {
//    Text("Hide bottom sheet")
//}

package com.satwik.transfertoinr.features.transfer.new

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.satwik.transfertoinr.core.designsystem.components.TTFButton
import com.satwik.transfertoinr.core.designsystem.components.headers.TTFTextHeader
import com.satwik.transfertoinr.core.model.Recipient
import com.satwik.transfertoinr.features.recipient.RecipientListItem
import com.sumsub.sns.internal.features.presentation.preview.photo.SNSPreviewPhotoDocumentViewModel.Content

@Composable
fun SelectRecipientScreen(navController: NavController) {
    Column(Modifier.background(color = Color.White)) {
        TTFTextHeader(text = "SELECT RECIPIENT", isBackButtonEnabled = true, onBackClick = {navController.popBackStack()})
        Content(navController = navController)
    }
}








@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(modifier: Modifier = Modifier, navController: NavController) {


    val recipients = listOf(
        Recipient(
            1,
            "Phillip Cameroon",
            "3243 4343 3244 3242",
            "DSS22A4",
            "Axis Bank",
            "cameroon@gmail.com"
        ),
        Recipient(
            2,
            "Rosie Melbourne",
            "2932 6674 3244 3242",
            "DSS22A4",
            "HDFC Bank",
            "cameroon@gmail.com"
        ),
        Recipient(
            3,
            "Pitcher Scallop",
            "6788 4353 3244 3242",
            "DSS22A4",
            "Uco Bank",
            "cameroon@gmail.com"
        )
    )
    var selectedRecipient by remember { mutableStateOf(recipients.first()) }

    // State to control the visibility of the bottom sheet
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier.padding(16.dp)
    ){
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(recipients) { recipient ->
                RecipientListItem(
                    name = recipient.name,
                    accountNumber = recipient.account_number,
                    bankName = recipient.bank,
                    deleteOnClick = { },
                    isSelected = recipient == selectedRecipient,
                    onSelect = { selectedRecipient = recipient },
                    radioEnabled = true
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        TTFButton(
            text = "Add Recipient",
            onClick = {
                showBottomSheet = true
            })
        Spacer(modifier = Modifier.height(5.dp))
        TTFButton(text = "Continue", onClick = {})

        if (showBottomSheet) {
            ModalBottomSheet(
                containerColor = Color.White,
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                SelectReasonSheet(navController = navController)
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

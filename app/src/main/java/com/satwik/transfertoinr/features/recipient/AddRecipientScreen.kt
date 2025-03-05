package com.satwik.transfertoinr.features.recipient

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.satwik.transfertoinr.core.designsystem.components.NewTTFDropdown
import com.satwik.transfertoinr.core.designsystem.components.TTFButton
import com.satwik.transfertoinr.core.designsystem.components.headers.TTFTextHeader
import com.satwik.transfertoinr.core.designsystem.components.TTFTextField
import com.satwik.transfertoinr.features.auth.signup.SignupFormEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddRecipientScreen(modifier: Modifier = Modifier, navController: NavController) {

    val viewModel = koinViewModel<RecipientViewModel>()

    val formState = viewModel.formState.value
    val context = LocalContext.current
    var isFormValidated by remember { mutableStateOf(false) }
    var errorText by remember { mutableStateOf("") }

    val relation = listOf("Father", "Mother", "Family", "Other")
    var selectedRelation by remember { mutableStateOf("") }

    LaunchedEffect(context) {
        viewModel.validationEvents.collect{ event->
            when(event){
                is ValidationEvent.Success ->{
                    isFormValidated = true
                }
            }
        }
    }



    Box(
        Modifier.fillMaxSize().padding(bottom = 16.dp).background(Color.White)){
        TTFTextHeader(text = "Add Recipient", isBackButtonEnabled = true, onBackClick = {navController.popBackStack()})

        Column(
            Modifier.padding(vertical = 90.dp, horizontal = 16.dp).align(Alignment.TopCenter)){
            TTFTextField(
                text = formState.name,
                onValueChange = { viewModel.onEvent(AddRecipientFormEvent.NameChanged(it)) },
                placeholder = "Recipient Name",
                isError = formState.nameError!=null,
                errorText = formState.nameError?:""
            )
            TTFTextField(
                text = formState.accountNumber,
                onValueChange = { viewModel.onEvent(AddRecipientFormEvent.AccountNumberChanged(it)) },
                placeholder = "Account Number",
                isError = formState.accountNumberError!=null,
                errorText = formState.accountNumberError?:""
            )
            TTFTextField(
                text = formState.reEnterAccountNumber,
                onValueChange = { viewModel.onEvent(AddRecipientFormEvent.ReEnterAccountNumberChanged(it))},
                placeholder = "Confirm account number",
                isError = formState.reEnterAccountNumberError!=null,
                errorText = formState.reEnterAccountNumberError?:""
            )
            TTFTextField(
                text = formState.ifsc,
                onValueChange = { viewModel.onEvent(AddRecipientFormEvent.IfscChanged(it)) },
                placeholder = "IFSC Code",
                isError = formState.ifscError!=null,
                errorText = formState.ifscError?:""
            )

            TTFTextField(
                text = formState.bank,
                onValueChange = { viewModel.onEvent(AddRecipientFormEvent.BankChanged(it)) },
                placeholder = "Bank",
                isError = formState.bankError!=null,
                errorText = formState.bankError?:""
            )
            NewTTFDropdown(
                items = relation,
                selectedItem = selectedRelation,
                onItemSelected = { selectedRelation = it },
                placeholder = "Select a relation"
            )
        }


        TTFButton(
            modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(vertical = 20.dp, horizontal = 16.dp),
            text = "Add",
            onClick = {
                viewModel.onEvent(AddRecipientFormEvent.Submit)
                if(isFormValidated){
                    viewModel.resetFormErrors()
                    viewModel.addRecipient(
                        name = formState.name,
                        accountNumber = formState.accountNumber,
                        ifscCode = formState.ifsc,
                        bank = formState.bank,
                        relation = selectedRelation
                    )
                    navController.popBackStack()
                }
            }
        )
    }
}
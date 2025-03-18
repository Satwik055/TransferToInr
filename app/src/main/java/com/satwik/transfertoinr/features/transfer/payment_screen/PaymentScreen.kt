package com.satwik.transfertoinr.features.transfer.payment_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.satwik.transfertoinr.core.designsystem.components.TTFButton
import com.satwik.transfertoinr.core.designsystem.components.headers.TTFTextHeader
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily
import com.satwik.transfertoinr.core.model.CurrencyType
import com.satwik.transfertoinr.core.designsystem.components.BankDetailsBox
import com.satwik.transfertoinr.core.designsystem.components.TransactionIDBox
import com.satwik.transfertoinr.core.main.ScreenMain
import com.satwik.transfertoinr.core.utils.getCurrencySymbol
import com.satwik.transfertoinr.features.transfer.shared_viewmodel.TransferSharedViewModel

@Composable
fun PaymentScreen(navController: NavController, viewModel: TransferSharedViewModel) {
    Column {
        TTFTextHeader(text = "PAYMENT", isBackButtonEnabled = true, onBackClick = {navController.popBackStack()})
        Content(modifier =  Modifier.fillMaxWidth().padding(start = 15.dp, end = 16.dp, top = 16.dp, bottom = 50.dp), viewModel, navController)
    }
}

@Composable
private fun Content(modifier: Modifier = Modifier, viewModel: TransferSharedViewModel, navController: NavController) {

    val user = viewModel.userInfoState.collectAsState().value

    Column (modifier = modifier){

        val style = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.SemiBold, fontSize = 17.sp, color = JungleGreen)
        val style1 = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Medium, fontSize = 13.sp, color = JungleGreen)

        val context = LocalContext.current

        Text(text = "Make Payment", style = style)
        Text(text = "Make payment to below details", style = style1)
        Spacer(modifier = Modifier.height(10.dp))
        BankDetailsBox(currency = user.profile.preferred_currency, context = context, modifier = Modifier.fillMaxWidth())


        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "Add this code", style = style)
        Text(text = "Add this code in description while making payment", style = style1)
        Spacer(modifier = Modifier.height(10.dp))

        TransactionIDBox(id = viewModel.transactionCode.value)

        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "Attach screenshot of payment", style = style)
        Spacer(modifier = Modifier.weight(1f))
        TTFButton(text = "Continue", onClick = {
            viewModel.createTransfer()
            navController.navigate(ScreenMain)
        })



    }


}
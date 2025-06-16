package com.ttf.transfertoinr.features.account

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ttf.transfertoinr.R
import com.ttf.transfertoinr.core.designsystem.components.LogoutConfirmationDialog
import com.ttf.transfertoinr.core.designsystem.components.NewTTFDropdown
import com.ttf.transfertoinr.core.designsystem.theme.JungleGreen
import com.ttf.transfertoinr.core.designsystem.theme.VeryLightGrey
import com.ttf.transfertoinr.core.designsystem.theme.fontFamily
import com.ttf.transfertoinr.core.main.ScreenKyc
import com.ttf.transfertoinr.core.main.ScreenPrivacyPolicy
import com.ttf.transfertoinr.core.main.ScreenSignup
import com.ttf.transfertoinr.core.model.CurrencyType
import com.ttf.transfertoinr.features.account.components.KycButton
import com.ttf.transfertoinr.features.account.components.TTFBarButtons
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccountScreen(navController: NavController) {

    val viewModel = koinViewModel<AccountsScreenViewModel>()
    val state = viewModel.userInfoState.collectAsState().value

    Box(modifier = Modifier.fillMaxSize()) {
        if(state.isLoading){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = JungleGreen)
        }
        if(state.error.isNotEmpty()){
            val errorTextStyle = TextStyle(fontWeight = FontWeight.Normal, fontFamily = fontFamily, fontSize = 13.sp, color = JungleGreen)
            Text(text = state.error, style = errorTextStyle)
        }
        if(state.profile.name.isNotEmpty()){
            Content(modifier = Modifier, navController, viewModel)
        }
    }
}

@Composable
internal fun Content(modifier: Modifier = Modifier, navController: NavController, viewModel: AccountsScreenViewModel) {

    val state = viewModel.userInfoState.collectAsState().value
    val user = state.profile
    val isLogoutClicked = remember { mutableStateOf(false) }

    if(isLogoutClicked.value){
        LogoutConfirmationDialog(
            title = "Confirm Logout",
            message = "Are you sure you want to logout?",
            onDismissRequest = { isLogoutClicked.value = false },
            onConfirm = {
                viewModel.logout()
                isLogoutClicked.value = false
                navController.navigate(ScreenSignup)
                        },
            onCancel = {isLogoutClicked.value = false}
        )
    }

    Column (modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally){

        ProfileSection(
            profilePic = R.drawable.profile_pic,
            name = user.name,
            email = user.email
        )
        Spacer(modifier = Modifier.height(50.dp))
        Column{
            KycButton(
                text = "KYC",
                isVerified = user.kyc_status,
                onClick = {navController.navigate(ScreenKyc)}
            )
            HorizontalDivider(color = VeryLightGrey)
            TTFBarButtons(
                text = "Logout",
                icon = R.drawable.ic_logout,
                onClick = { isLogoutClicked.value = true }
            )
            HorizontalDivider(color = VeryLightGrey)
            TTFBarButtons(
                text = "Privacy Policy",
                icon = R.drawable.ic_scroll,
                onClick = {navController.navigate(ScreenPrivacyPolicy)}
            )
            HorizontalDivider(color = VeryLightGrey)

            Spacer(modifier = Modifier.height(5.dp))

            val currency = listOf("EUR", "USD", "AUD", "CAD", "GBP")
            var selectedCurrency by remember { mutableStateOf(user.preferred_currency.name)  }

            LaunchedEffect(selectedCurrency) {
                viewModel.updatePreferredCurrency(CurrencyType.valueOf(selectedCurrency))
            }

            val style = TextStyle(fontFamily = fontFamily, fontSize = 16.sp, fontWeight = FontWeight.Medium)

            Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)){
                Text(text = "Currency", style = style)
                NewTTFDropdown(items = currency , selectedItem = selectedCurrency, onItemSelected = {selectedCurrency = it}, modifier = Modifier
                    .width(150.dp)
                    .height(50.dp))
            }
        }
    }
}


@Composable
fun ProfileSection(
    modifier: Modifier = Modifier,
    @DrawableRes profilePic:Int,
    name:String, email:String
){
    Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier){
        Image(painter = painterResource(id =profilePic), contentDescription = null, modifier=Modifier.size(90.dp))
        Text(text = name,fontFamily = fontFamily, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(text = email,fontFamily = fontFamily, fontSize = 13.sp, fontWeight = FontWeight.Medium, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}





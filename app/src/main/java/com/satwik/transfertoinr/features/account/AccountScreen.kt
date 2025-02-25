package com.satwik.transfertoinr.features.account

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.satwik.transfertoinr.R
import com.satwik.transfertoinr.core.designsystem.components.NewTTFDropdown
import com.satwik.transfertoinr.core.designsystem.theme.VeryLightGrey
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily
import com.satwik.transfertoinr.core.main.ScreenKyc
import com.satwik.transfertoinr.core.main.ScreenPrivacyPolicy
import com.satwik.transfertoinr.core.main.ScreenSignup
import com.satwik.transfertoinr.core.model.CurrencyType
import com.satwik.transfertoinr.features.account.components.KycButton
import com.satwik.transfertoinr.features.account.components.TTFBarButtons
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccountScreen(modifier: Modifier = Modifier, navController: NavController, activity: Activity) {
    Content(modifier, navController, activity)
}

@Composable
private fun Content(modifier: Modifier = Modifier, navController: NavController, activity: Activity) {

    val viewModel = koinViewModel<AccountsScreenViewModel>()
    val state = viewModel.userInfoState.value
    val user = state.userInfo

    LaunchedEffect(Unit){
        viewModel.getUserInfo()
    }

    Column (modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally){
        if(state.isLoading){
            println("Loading...")
        }
        if(state.error.isNotEmpty()){
            println(viewModel.userInfoState.value.error)
        }
        else{
            println(viewModel.userInfoState.value.userInfo)
        }

        UserInfoSection(profilePic = R.drawable.profile_pic, name = viewModel.userInfoState.value.userInfo.name, email = viewModel.userInfoState.value.userInfo.email)
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
                onClick = {
                    viewModel.logout()
                    navController.navigate(ScreenSignup)
                          }
            )
            HorizontalDivider(color = VeryLightGrey)
            TTFBarButtons(
                text = "Privacy Policy",
                icon = R.drawable.ic_scroll,
                onClick = {navController.navigate(ScreenPrivacyPolicy)}
            )
            HorizontalDivider(color = VeryLightGrey)


            Spacer(modifier = Modifier.height(5.dp))

            if(!state.isLoading){
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
}


@Composable
fun UserInfoSection(
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





package com.satwik.transfertoinr.features.account

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.satwik.transfertoinr.R
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.LightGrey
import com.satwik.transfertoinr.core.designsystem.theme.VeryLightGrey
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily
import com.satwik.transfertoinr.core.main.ScreenKyc
import com.satwik.transfertoinr.core.main.ScreenPrivacyPolicy
import com.satwik.transfertoinr.core.main.ScreenSignup
import com.sumsub.sns.core.SNSMobileSDK
import com.sumsub.sns.core.data.listener.TokenExpirationHandler
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@Composable
fun AccountScreen(modifier: Modifier = Modifier, navController: NavController, activity: Activity) {
    Content(modifier, navController, activity)
}

@Composable
private fun Content(modifier: Modifier = Modifier, navController: NavController, activity: Activity) {

    val viewModel = koinViewModel<AccountsScreenViewModel>()
    val user = viewModel.userInfoState.value.userInfo

    val accessToken = "_act-sbx-jwt-eyJhbGciOiJub25lIn0.eyJqdGkiOiJfYWN0LXNieC01YzJlODJkNC04YWNiLTRjZTEtYWIwNC0zZTc0NDMwNDcyZTQtdjIiLCJ1cmwiOiJodHRwczovL2FwaS5zdW1zdWIuY29tIn0.-v2"
    val tokenExpirationHandler = object : TokenExpirationHandler {
        override fun onTokenExpired(): String {
            // Fetch a new token from your backend
            val newToken = "..."
            return newToken
        }
    }
    val snsSdk = remember {
        SNSMobileSDK.Builder(activity).withDebug(true)
            .withAccessToken(accessToken, onTokenExpiration = tokenExpirationHandler)
            .withLocale(Locale("en"))
            .build()
    }


    Column ( modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally){
        if(viewModel.userInfoState.value.isLoading){
            println("Loading...")
        }
        if(viewModel.userInfoState.value.error.isNotEmpty()){
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
        }
    }



}

@Composable
fun TTFBarButtons(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(fontFamily = fontFamily, fontSize = 16.sp, fontWeight = FontWeight.Medium),
    text:String,
    @DrawableRes icon:Int,
    onClick: () -> Unit
    ) {

    val customIndication = ripple(color = LightGrey)
    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(50.dp)
            .clickable(
                onClick = { onClick.invoke() },
                interactionSource = remember { MutableInteractionSource() },
                indication = customIndication
            )
            .fillMaxWidth()
            .padding(horizontal = 10.dp)


    ){
        Text(text = text, style = textStyle)
        Icon(painter = painterResource(id = icon), contentDescription = null)
    }
}

@Composable
fun UserInfoSection(
    modifier: Modifier = Modifier,
    @DrawableRes profilePic:Int,
    name:String, email:String
){
    Column (horizontalAlignment = Alignment.CenterHorizontally){
        Image(painter = painterResource(id =profilePic), contentDescription = null, modifier=Modifier.size(90.dp))
        Text(text = name,fontFamily = fontFamily, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(text = email,fontFamily = fontFamily, fontSize = 13.sp, fontWeight = FontWeight.Medium, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

@Composable
fun KycStatusChip(isVerified:Boolean) {

    val style = TextStyle(fontFamily = fontFamily, fontSize = 12.sp, fontWeight = FontWeight.Normal, color = Color.Red)
    val color = when(isVerified){
        true-> JungleGreen
        false-> Color.Red
    }
    val text = when(isVerified) {
        true -> "Verified"
        false -> "Unverified"
    }

    Box(
        modifier = Modifier.border(width = 1.dp, color = color, shape = RoundedCornerShape(6.dp)).padding(5.dp)
    ) {
        Text(
            style = style,
            text = text,
            color = color
        )
    }

}



@Composable
fun KycButton(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(fontFamily = fontFamily, fontSize = 16.sp, fontWeight = FontWeight.Medium),
    text:String,
    onClick: () -> Unit,
    isVerified:Boolean
) {
    val customIndication = ripple(color = LightGrey)

    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable(
                onClick = { onClick.invoke() },
                interactionSource = remember { MutableInteractionSource() },
                indication = customIndication
            )
            .height(50.dp)
            .fillMaxWidth()
            .padding(horizontal = 10.dp)


    ){
        Text(text = text, style = textStyle)
        KycStatusChip(isVerified)
    }
}
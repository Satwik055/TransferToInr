package com.satwik.transfertoinr.features.account

import androidx.annotation.DrawableRes
import androidx.collection.mutableObjectIntMapOf
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.satwik.transfertoinr.R
import com.satwik.transfertoinr.core.designsystem.components.TTFTextHeader
import com.satwik.transfertoinr.core.designsystem.theme.LightGrey
import com.satwik.transfertoinr.core.designsystem.theme.VeryLightGrey
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccountScreen(modifier: Modifier = Modifier, navController: NavController) {

    val viewModel = koinViewModel<AccountsScreenViewModel>()
    Column (
        modifier = modifier.fillMaxSize()
    ){
        TTFTextHeader(text = "Account")
        Content(modifier.padding(16.dp), viewModel)

    }

}

@Composable
private fun Content(modifier: Modifier = Modifier, viewModel: AccountsScreenViewModel) {

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
            TTFBarButtons(text = "KYC", icon = R.drawable.ic_account, onClick = {}, modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp))
            HorizontalDivider(color = VeryLightGrey)
            TTFBarButtons(
                text = "Logout",
                icon = R.drawable.ic_account,
                modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
                onClick = {
                    println("viola")
                    viewModel.logout()
                },
            )
            HorizontalDivider(color = VeryLightGrey)
            TTFBarButtons(text = "Privacy Policy", icon = R.drawable.ic_account, onClick = {}, modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp))
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

    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable { onClick.invoke() }
    ){
        Text(text = text, style = textStyle)
        Icon(painter = painterResource(id = icon), contentDescription = null)
    }
}

@Composable
fun UserInfoSection(modifier: Modifier = Modifier, @DrawableRes profilePic:Int, name:String, email:String){
    Column (horizontalAlignment = Alignment.CenterHorizontally){
        Image(painter = painterResource(id =profilePic), contentDescription = null, modifier=Modifier.size(90.dp))
        Text(text = name,fontFamily = fontFamily, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        Text(text = email,fontFamily = fontFamily, fontSize = 13.sp, fontWeight = FontWeight.Medium)

    }
}



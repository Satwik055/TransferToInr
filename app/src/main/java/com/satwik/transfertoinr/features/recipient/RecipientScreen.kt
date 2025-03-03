package com.satwik.transfertoinr.features.recipient

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.satwik.transfertoinr.core.designsystem.components.TTFButton
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily
import com.satwik.transfertoinr.core.main.ScreenAddRecipient
import com.satwik.transfertoinr.features.auth.signup.SignupScreenViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun RecipientScreen(navController: NavController) {
    Content(modifier = Modifier.fillMaxSize(), navController)
}

@Composable
private fun Content(modifier: Modifier = Modifier, navController: NavController) {
    Box(modifier=modifier){

        val style1 = TextStyle(fontWeight = FontWeight.Normal, fontFamily = fontFamily, fontSize = 14.sp, color = JungleGreen)
        val viewModel = koinViewModel<RecipientViewModel>()

        val state = viewModel.recipientsState.collectAsState().value

        if(state.isLoading){
            CircularProgressIndicator(color = JungleGreen, modifier = Modifier.align(Alignment.Center))
        }
        if(state.error.isNotEmpty()){
            Text(text = state.error, style = style1, modifier = Modifier.align(Alignment.Center))
        }
        else{
            LazyColumn (
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ){
                items(state.recipients){recipient->
                    RecipientListItem(name = recipient.name, accountNumber = recipient.account_number, bankName = recipient.bank, deleteOnClick = { viewModel.deleteRecipientById(recipient.id) })
                }
            }
        }


        TTFButton(
            text = "Add Recipient",
            onClick = { navController.navigate(ScreenAddRecipient) },
            modifier = Modifier.align(Alignment.BottomCenter)
        )

    }
}



@Composable
fun RecipientListItem(modifier: Modifier = Modifier, name:String, accountNumber:String, bankName:String, deleteOnClick:()->Unit) {
    val style1 = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
    val style2 = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Normal, fontSize = 13.sp)
    val style3 = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Normal, fontSize = 13.sp)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        color = Color.White,
        shadowElevation = 3.dp,
//        border = BorderStroke(width = 1.dp, color = Color.LightGray),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row (
            modifier = Modifier.padding(13.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column(verticalArrangement = Arrangement.spacedBy(5.dp)){
                Text(text = name, style = style1)
                Text(text = accountNumber, style = style2)
                Text(text = bankName, style = style3)
            }
            Icon(imageVector = Icons.Filled.Delete, contentDescription = null, tint = Color.Red, modifier = Modifier.clickable { deleteOnClick.invoke() })
        }
    }
}


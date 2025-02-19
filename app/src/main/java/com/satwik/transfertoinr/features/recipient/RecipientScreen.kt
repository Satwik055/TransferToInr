package com.satwik.transfertoinr.features.recipient

import androidx.activity.compose.ReportDrawn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.satwik.transfertoinr.R
import com.satwik.transfertoinr.core.designsystem.components.TTFButton
import com.satwik.transfertoinr.core.designsystem.components.TTFTextHeader
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily
import com.satwik.transfertoinr.core.main.ScreenAddRecipient
import com.satwik.transfertoinr.core.model.Recipient
import com.satwik.transfertoinr.features.transfer.style
import org.koin.androidx.compose.koinViewModel

@Composable
fun RecipientScreen(modifier: Modifier = Modifier, internalPadding: PaddingValues, navController: NavController) {
    Column (
        modifier = modifier.fillMaxSize()
    ){
        TTFTextHeader(text = "Recipient")
        Content(modifier = Modifier
            .fillMaxSize()
            .padding(internalPadding)
            .padding(16.dp),
            navController
        )
    }
}

@Composable
private fun Content(modifier: Modifier = Modifier, navController: NavController) {
    Column(modifier=modifier){

        val viewModel = koinViewModel<RecipientViewModel>()
        val state = viewModel.recipientsState

        if(state.value.isLoading){
            println("Loading...")
        }
        if(state.value.error.isNotEmpty()){
            println(state.value.error)
        }
        else{
            println(state.value.recipients)
        }

        LazyColumn (
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ){
            items(state.value.recipients){recipient->
                RecipientListItem(name = recipient.name, accountNumber = recipient.account_number, bankName = recipient.bank)
            }

        }
        Spacer(modifier = Modifier.weight(1f))
        TTFButton(text = "Add Recipient", onClick = {navController.navigate(ScreenAddRecipient)})

    }
}



@Composable
fun RecipientListItem(modifier: Modifier = Modifier, name:String, accountNumber:String, bankName:String) {
    val style1 = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
    val style2 = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Normal, fontSize = 13.sp)
    val style3 = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Normal, fontSize = 13.sp)

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 5.dp,
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
            Icon(imageVector = Icons.Filled.Delete, contentDescription = null, tint = Color.Red)
        }
    }
}


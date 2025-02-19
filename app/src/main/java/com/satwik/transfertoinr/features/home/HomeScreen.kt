package com.satwik.transfertoinr.features.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.satwik.transfertoinr.core.designsystem.components.Carousell
import com.satwik.transfertoinr.core.designsystem.components.RateTable
import com.satwik.transfertoinr.core.designsystem.components.TTFIconHeader
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column (
        modifier.fillMaxSize()
    ){
        TTFIconHeader(helpButtonOnClick = {})
        Content(
            modifier = modifier
                .padding(16.dp)
                .fillMaxSize()
        )
    }
}

@Composable
private fun Content(modifier: Modifier = Modifier) {

    val viewModel =  koinViewModel<HomeScreenViewModel>()

    val style = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.SemiBold, fontSize = 37.sp, color = JungleGreen)
    Column (modifier = modifier){
        Text(text = "Hey ${viewModel.userInfoState.value.userInfo.name}", style=style)
        Spacer(modifier = Modifier.height(20.dp))
        Carousell()
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "We provide best exchange rates",
            style=style.copy(fontSize = 18.sp),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(10.dp))

        RateTable()
    }
}








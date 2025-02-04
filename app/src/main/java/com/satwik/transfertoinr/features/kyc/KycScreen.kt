package com.satwik.transfertoinr.features.kyc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.satwik.transfertoinr.R
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.White

@Composable
fun KycScreen(modifier: Modifier = Modifier) {
    Box (
        modifier=Modifier.fillMaxSize()
    ){
        Row (
            modifier = Modifier.background(color = JungleGreen).fillMaxWidth().height(60.dp).padding(horizontal = 16.dp)
        ){
            Icon(modifier = Modifier.align(Alignment.CenterVertically).size(35.dp), painter = painterResource(id = R.drawable.ic_logo), tint = White, contentDescription = null)
        }
        Content()
    }
}

@Composable
private fun Content(modifier: Modifier = Modifier) {
    Box (
        modifier.fillMaxSize()
    ){

    }
}

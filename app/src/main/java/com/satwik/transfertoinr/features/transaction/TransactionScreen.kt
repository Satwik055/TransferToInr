package com.satwik.transfertoinr.features.transaction

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.satwik.transfertoinr.core.designsystem.components.TTFHeader

@Composable
fun TransactionScreen(modifier: Modifier = Modifier) {
    Column (
        modifier = modifier.fillMaxSize()
    ){
        TTFHeader(text = "Transaction")
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
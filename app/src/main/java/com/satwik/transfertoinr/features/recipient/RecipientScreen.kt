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
import com.satwik.transfertoinr.R
import com.satwik.transfertoinr.core.designsystem.components.TTFButton
import com.satwik.transfertoinr.core.designsystem.components.TTFTextHeader
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily
import com.satwik.transfertoinr.features.transfer.style

@Composable
fun RecipientScreen(modifier: Modifier = Modifier, internalPadding: PaddingValues) {
    Column (
        modifier = modifier.fillMaxSize()
    ){
        TTFTextHeader(text = "Recipient")
        Content(modifier = Modifier
            .fillMaxSize()
            .padding(internalPadding)
            .padding(16.dp)
        )
    }
}

@Composable
private fun Content(modifier: Modifier = Modifier) {
    Column(modifier=modifier){
        RecipientBox(name = "RAJU CHANDRAGUPTA", accountNumber = "3535 4353 3454 3445", ifscCode = "VKNDVIUH8")
        
        Spacer(modifier = Modifier.weight(1f))
        TTFButton(text = "Add Recipient", onClick = {})


    }

}


@Composable
fun RecipientBox(modifier: Modifier = Modifier, name:String, accountNumber:String, ifscCode:String) {
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
                Text(text = ifscCode, style = style3)
            }
            Icon(imageVector = Icons.Filled.Delete, contentDescription = null, tint = Color.Red)
        }
    }

}

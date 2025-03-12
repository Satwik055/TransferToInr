package com.satwik.transfertoinr.features.transfer.new

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.satwik.transfertoinr.core.designsystem.components.TTFButton
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily
import com.satwik.transfertoinr.core.main.ScreenSummary

@Composable
fun SelectReasonSheet(navController: NavController) {
    val reasons = listOf("My NRE/NRO Account", "Savings & Family Support", "Real Estate/Housing Societies", "Educational Institutions", "Tax Payment", "Hospitals/Healthcare Providers", "Travel/Tourism Partners", "Utility Bill Payments", "Loan Account Payment")
    var selectedReason by remember { mutableStateOf(reasons.first()) }

    LaunchedEffect(selectedReason) {
        println(selectedReason)
    }




    Column (modifier = Modifier.padding(horizontal = 16.dp)) {

        val style = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Medium, fontSize = 18.sp)
        Text(text = "Select reason for transfer", style = style)
        Spacer(modifier = Modifier.height(20.dp))
        reasons.forEach { reason ->
            ReasonListItem(
                reason = reason,
                isSelected = reason == selectedReason,
                onSelect = { selectedReason = reason }
            )
        }
        TTFButton(
            text = "Continue",
            onClick = {
//                scope.launch { sheetState.hide() }.invokeOnCompletion {
//                    if (!sheetState.isVisible) {
//                        showBottomSheet = false
//                    }
//                }
                navController.navigate(ScreenSummary)
            }
        )
    }
}


@Composable
fun ReasonListItem(
    modifier: Modifier = Modifier,
    reason: String,
    isSelected:Boolean = false,
    onSelect: () -> Unit,
) {
    val style = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Normal, fontSize = 13.sp, color = JungleGreen)


    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(text = reason, style = style)
        RadioButton(selected = isSelected, onClick = { onSelect.invoke() }, colors = RadioButtonColors(selectedColor = JungleGreen, unselectedColor = JungleGreen, disabledSelectedColor = JungleGreen, disabledUnselectedColor = JungleGreen))
    }
}


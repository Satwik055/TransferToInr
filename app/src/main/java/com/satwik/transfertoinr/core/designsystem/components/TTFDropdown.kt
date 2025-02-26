package com.satwik.transfertoinr.core.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.LightGrey
import com.satwik.transfertoinr.core.designsystem.theme.VeryLightGrey
import com.satwik.transfertoinr.core.designsystem.theme.White
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TTFDropdown(
    modifier: Modifier = Modifier,
    dropDownColor: Color = White,
    textStyle: TextStyle = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Normal, fontSize = 15.sp, color = JungleGreen),
    items:List<Pair<String, String>>,
    selectedItem: Pair<String, String>,
    onItemSelected: (Pair<String, String>) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = if (selectedItem.first.isEmpty()) "No item found" else selectedItem.first,
            onValueChange = {},
            singleLine = true,
            textStyle = textStyle,
            readOnly = true,

            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = modifier.menuAnchor().focusRequester(focusRequester),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                cursorColor = JungleGreen,
                focusedBorderColor = JungleGreen,
                unfocusedBorderColor = LightGrey,
            ),
        )

        ExposedDropdownMenu(
            containerColor = dropDownColor,
            border = BorderStroke(1.dp, VeryLightGrey),
            shadowElevation = 0.dp,
            tonalElevation = 0.dp,
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                focusManager.clearFocus()
            }
        ) {
            if(items.isNotEmpty()){
                items.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        text = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = item.first, style = textStyle)
                                Text(text = item.second, style = textStyle)
                            }
                        },
                        onClick = {
                            onItemSelected(item)
                            expanded = false
                            focusManager.clearFocus()
                        }
                    )
                    if (index < items.lastIndex) {
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp), thickness = 1.dp, color = VeryLightGrey)
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTTFDropdown(
    modifier: Modifier = Modifier,
    dropDownColor: Color = White,
    textStyle: TextStyle = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Normal, fontSize = 15.sp, color = JungleGreen),
    items:List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    ExposedDropdownMenuBox(
        modifier = modifier.fillMaxWidth(),
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedItem,
            onValueChange = {},
            singleLine = true,
            textStyle = textStyle,
            readOnly = true,

            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = modifier.menuAnchor().focusRequester(focusRequester).fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                cursorColor = JungleGreen,
                focusedBorderColor = JungleGreen,
                unfocusedBorderColor = LightGrey,
            ),
        )

        ExposedDropdownMenu(
            containerColor = dropDownColor,
            border = BorderStroke(1.dp, VeryLightGrey),
            shadowElevation = 0.dp,
            tonalElevation = 0.dp,
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                focusManager.clearFocus()
            }
        ) {
            items.forEachIndexed { index, item ->
                DropdownMenuItem(
                    text = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = item, style = textStyle)
                        }
                    },
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                        focusManager.clearFocus()
                    }
                )
                if (index < items.lastIndex) {
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp), thickness = 1.dp, color = VeryLightGrey)
                }
            }
        }
    }
}

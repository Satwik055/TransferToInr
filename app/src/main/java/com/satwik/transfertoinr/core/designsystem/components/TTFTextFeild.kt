package com.satwik.transfertoinr.core.designsystem.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.satwik.transfertoinr.R
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.LightGrey
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily

@Composable
fun TTFTextField(
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    text:String,
    font: FontFamily = fontFamily,
    onValueChange: (String) -> Unit,
    placeholder:String,
    errorText: String = "",
    enabled:Boolean = true,
    isPassword:Boolean = false,
    isError:Boolean = false
){
    val passwordVisible = remember { mutableStateOf(false) }


    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        value = text,
        enabled = enabled,
        onValueChange = onValueChange,
        isError = isError,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            cursorColor = JungleGreen,
            focusedBorderColor = JungleGreen,
            unfocusedBorderColor = LightGrey,
            errorBorderColor = Color.Red,
        ),
        singleLine = true,
        supportingText = { Text(text = errorText,fontFamily = font, color = Color.Red, fontSize = 12.sp, fontWeight = FontWeight.Medium, modifier = Modifier.offset(x=-(10.dp)))},
        textStyle = TextStyle(fontFamily = font, fontWeight = FontWeight.Normal, fontSize = 16.sp, color = JungleGreen),
        placeholder = {
            Text(
                text = placeholder,
                color = LightGrey,
                fontFamily = font,
                fontWeight = FontWeight.Normal
            )
        },
        visualTransformation =
        if (isPassword && !passwordVisible.value) {
            PasswordVisualTransformation(mask = '\u25CF')
        } else {
            VisualTransformation.None
        },
        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                    Icon(
                        painter = if (passwordVisible.value) {
                            painterResource(id = R.drawable.ic_eye_open)
                        } else {
                            painterResource(id = R.drawable.ic_eye_hide)
                        },
                        contentDescription = null
                    )
                }
            }
        }
    )

}

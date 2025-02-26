package com.satwik.transfertoinr.core.designsystem.components

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily


@Composable
fun CustomAlertDialog(
    title: String,
    message: String,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {

    val heading = TextStyle(fontFamily = fontFamily, fontSize = 16.sp, color = JungleGreen, fontWeight = FontWeight.SemiBold)
    val body = TextStyle(fontFamily = fontFamily, fontSize = 13.sp, color = JungleGreen, fontWeight = FontWeight.Normal)

    Dialog(onDismissRequest = onDismissRequest) {
        Box(
            modifier = Modifier
                .background(Color.White, shape = RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = title,
                        style = heading,
                        textAlign = TextAlign.Start,
                    )
                    Spacer(modifier = Modifier.width(5.dp))

                    Icon(imageVector = Icons.Filled.Info, contentDescription = null, tint = Color.Red)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Message
                Text(
                    text = message,
                    style = body,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TTFButton(
                        text = "Confirm",
                        color = Color.Red,
                        onClick = onConfirm,
                        modifier = Modifier.width(130.dp).height(40.dp),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    TTFButton(
                        text = "Cancel",
                        color = Color.White,
                        textColor = JungleGreen,
                        onClick = onCancel,
                        modifier = Modifier.width(130.dp).height(40.dp),
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp,
                    )

                }
            }
        }
    }
}
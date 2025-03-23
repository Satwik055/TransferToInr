package com.satwik.transfertoinr.core.designsystem.components

import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.satwik.transfertoinr.R
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.LightGrey
import com.satwik.transfertoinr.core.designsystem.theme.VeryLightGrey
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily


@Composable
fun CompleteKYCDialog(
    onDismissRequest: () -> Unit,
    onButtonClicked: () -> Unit,
    onCancelClicked:()-> Unit
) {

    val heading = TextStyle(fontFamily = fontFamily, fontSize = 16.sp, color = JungleGreen, fontWeight = FontWeight.SemiBold)
    val body = TextStyle(fontFamily = fontFamily, fontSize = 13.sp, color = JungleGreen, fontWeight = FontWeight.Normal)

    Dialog(
        onDismissRequest = onDismissRequest,
    ) {

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
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Icon(painter = painterResource(id = R.drawable.ic_caution), contentDescription = null, tint = Color.Red)
                    Icon(painter = painterResource(id = R.drawable.ic_cancel), contentDescription = null, tint = VeryLightGrey, modifier = Modifier.size(12.dp).clickable { onCancelClicked.invoke() })
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = "Incomplete KYC",
                        style = heading,
                        textAlign = TextAlign.Start,
                    )

                }

                Spacer(modifier = Modifier.height(10.dp))

                // Message
                Text(
                    text = "Please first complete KYC to start transferring money",
                    style = body,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                TTFButton(
                    text = "Complete KYC",
                    onClick = onButtonClicked,
                    fontSize = 13.sp,
                    modifier = Modifier.height(45.dp),
                    fontWeight = FontWeight.Medium
                )
                }
            }
        }
    }


@Preview
@Composable
fun Preview(modifier: Modifier = Modifier) {
    CompleteKYCDialog(
        onDismissRequest = { /*TODO*/ },
        onButtonClicked = {},
        onCancelClicked = {}
    )

}
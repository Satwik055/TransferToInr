package com.ttf.transfertoinr.features.transfer.payment_screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.ttf.transfertoinr.R
import com.ttf.transfertoinr.core.designsystem.components.TTFButton
import com.ttf.transfertoinr.core.designsystem.components.headers.TTFTextHeader
import com.ttf.transfertoinr.core.designsystem.theme.JungleGreen
import com.ttf.transfertoinr.core.designsystem.theme.fontFamily
import com.ttf.transfertoinr.core.model.CurrencyType
import com.ttf.transfertoinr.core.designsystem.components.BankDetailsBox
import com.ttf.transfertoinr.core.designsystem.components.TransactionIDBox
import com.ttf.transfertoinr.core.main.ScreenMain
import com.ttf.transfertoinr.core.main.ScreenSuccess
import com.ttf.transfertoinr.core.utils.getCurrencySymbol
import com.ttf.transfertoinr.features.transfer.shared_viewmodel.TransferSharedViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.io.File

@Composable
fun PaymentScreen(navController: NavController, viewModel: TransferSharedViewModel) {
    Column {
        TTFTextHeader(
            text = "PAYMENT",
            isBackButtonEnabled = true, onBackClick = { navController.popBackStack() })
        Content(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 16.dp, top = 16.dp, bottom = 50.dp), viewModel, navController)
    }
}

@Composable
private fun Content(modifier: Modifier = Modifier, viewModel: TransferSharedViewModel, navController: NavController) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val coroutineScope = rememberCoroutineScope()
    var isUploaded by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> imageUri = uri }
    )

    val uploadScreenshotState = viewModel.uploadScreenshotResult.collectAsState().value
    val user = viewModel.userInfoState.collectAsState().value

    Column (modifier = modifier) {
        val style = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 17.sp,
            color = JungleGreen
        )
        val style1 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            color = JungleGreen
        )

        val context = LocalContext.current

        Text(text = "Make Payment", style = style)
        Text(text = "Make payment to below details", style = style1)
        Spacer(modifier = Modifier.height(10.dp))
        BankDetailsBox(
            currency = user.profile.preferred_currency,
            context = context,
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "Add this code", style = style)
        Text(text = "Add this code in description while making payment", style = style1)
        Spacer(modifier = Modifier.height(10.dp))

        TransactionIDBox(id = viewModel.transactionCode.value)

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Attach screenshot of payment", style = style)
            Icon(
                painter = painterResource(id = R.drawable.ic_paperpin),
                tint = JungleGreen,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        launcher.launch("image/*")
                        viewModel.resetUploadScreenshotState()
                    }
                    .padding(vertical = 7.dp, horizontal = 30.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        imageUri?.let { uri ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = uri),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))

                TTFButton(
                    text = if (uploadScreenshotState.success) "Uploaded" else "Upload",
                    isEnabled = !uploadScreenshotState.success,
                    isLoading = uploadScreenshotState.isLoading,
                    modifier = Modifier
                        .height(30.dp)
                        .width(100.dp),
                    fontSize = 10.sp,
                    onClick = {
                        coroutineScope.launch {
                            val file = File(context.cacheDir, "temp_image.jpg")
                            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                                file.outputStream().use { outputStream ->
                                    inputStream.copyTo(outputStream)
                                }
                            }
                            val fileBytes = file.readBytes()
                            val fileName = "images/${System.currentTimeMillis()}.jpg"
                            viewModel.uploadScreenshot(fileName, fileBytes)
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        TTFButton(
            text = "Continue",
            isEnabled = uploadScreenshotState.success,
            onClick = {
                viewModel.createTransfer()
                navController.navigate(ScreenSuccess)
            }
        )

    }
}
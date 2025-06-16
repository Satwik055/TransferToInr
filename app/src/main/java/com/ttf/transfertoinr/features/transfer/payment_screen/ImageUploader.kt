package com.ttf.transfertoinr.features.transfer.payment_screen

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.ttf.transfertoinr.R
import com.ttf.transfertoinr.core.designsystem.components.TTFButton
import com.ttf.transfertoinr.core.designsystem.theme.JungleGreen
import com.ttf.transfertoinr.core.designsystem.theme.fontFamily
import com.ttf.transfertoinr.features.transfer.shared_viewmodel.TransferSharedViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.io.ByteArrayOutputStream
import java.io.File

@Composable
fun ImageUploader() {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val imageUrl by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            imageUri = uri
        }
    )

    val viewmodel = koinViewModel<TransferSharedViewModel>()
    val uploadScreenshotState = viewmodel.uploadScreenshotResult.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        val style = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Medium, fontSize = 13.sp, color = JungleGreen)

        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = "Attach screenshot of payment", style = style)
            Icon(
                painter = painterResource(id = R.drawable.ic_paperpin), tint = JungleGreen , contentDescription = null,
                modifier = Modifier
                    .clickable { launcher.launch("image/*") }
                    .padding(vertical = 7.dp, horizontal = 30.dp)
            )
        }


        Spacer(modifier = Modifier.height(16.dp))

        imageUri?.let { uri ->

            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
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
                    text = "Upload",
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

                            viewmodel.uploadScreenshot(fileName, fileBytes)
                        }
                    })
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

    }
}


@Composable
fun UploadScreenshotSection(modifier: Modifier = Modifier) {

}
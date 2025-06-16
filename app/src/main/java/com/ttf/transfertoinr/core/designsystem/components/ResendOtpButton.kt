package com.ttf.transfertoinr.core.designsystem.components


import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.toRoute
import com.ttf.transfertoinr.R
import com.ttf.transfertoinr.core.designsystem.components.ResendOtpButton
import com.ttf.transfertoinr.core.designsystem.components.TTFButton
import com.ttf.transfertoinr.core.designsystem.components.TTFTextField
import com.ttf.transfertoinr.core.designsystem.theme.JungleGreen
import com.ttf.transfertoinr.core.designsystem.theme.LightGrey
import com.ttf.transfertoinr.core.designsystem.theme.VeryLightGrey
import com.ttf.transfertoinr.core.designsystem.theme.fontFamily
import com.ttf.transfertoinr.core.main.ScreenEmailVerification
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun ResendOtpButton(
    modifier: Modifier = Modifier,
    onResendOtpClicked: () -> Unit,
    cooldownDuration: Int = 20,
) {
    var remainingTime by remember { mutableIntStateOf(cooldownDuration) }
    val isButtonEnabled by derivedStateOf { remainingTime == 0 }

    // Timer logic
    LaunchedEffect(remainingTime) {
        if (remainingTime > 0) {
            delay(1000)
            remainingTime--
        }
    }

    val style = TextStyle(fontFamily = fontFamily, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = JungleGreen)

    TextButton(
        modifier = modifier,
        onClick = {
            if (isButtonEnabled) {
                onResendOtpClicked()
                remainingTime = cooldownDuration // Reset the timer
            }
        },
        shape = RoundedCornerShape(5.dp),
        enabled = isButtonEnabled
    ) {
        if (isButtonEnabled) {
            Text("Resend Code", style = style)
        } else {
            Text("Resend Code in $remainingTime seconds", style = style.copy(color = VeryLightGrey))
        }
    }
}


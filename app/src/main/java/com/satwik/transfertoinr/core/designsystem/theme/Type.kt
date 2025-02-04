package com.satwik.transfertoinr.core.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.satwik.transfertoinr.R

val poppins = FontFamily(
    Font(R.font.poppins_thin, FontWeight.Thin),
    Font(R.font.poppins_light, FontWeight.Light),
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
    Font(R.font.poppins_bold, FontWeight.Bold),
    Font(R.font.poppins_extrabold, FontWeight.ExtraBold),
    Font(R.font.poppins_black, FontWeight.Black),
    )

val fontFamily = poppins

val Typography = Typography(
    labelSmall = TextStyle(fontSize = 12.sp, fontFamily = fontFamily, fontWeight = FontWeight.Medium, color = JungleGreen),
    headlineLarge = TextStyle(fontSize = 26.sp, fontFamily = fontFamily, fontWeight = FontWeight.SemiBold, color = White)
)
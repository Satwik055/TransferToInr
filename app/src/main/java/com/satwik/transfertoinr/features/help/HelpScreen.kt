package com.satwik.transfertoinr.features.help

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.satwik.transfertoinr.core.designsystem.components.headers.TTFTextHeader
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily

@Composable
fun HelpScreen(navController: NavController) {
    Column {
        TTFTextHeader(text = "Help & Support", isBackButtonEnabled = true, onBackClick = { navController.popBackStack() })
        Content(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp))
    }

}

@Composable
private fun Content(modifier: Modifier = Modifier) {
    Column (modifier = modifier){

        val heading = TextStyle(fontFamily = fontFamily, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = JungleGreen)
        val subheading = TextStyle(fontFamily = fontFamily, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = JungleGreen)
        val body = TextStyle(fontFamily = fontFamily, fontSize = 14.sp, fontWeight = FontWeight.Normal, color = JungleGreen)

        val scroll = rememberScrollState()


        Text(text = "Reach us at", style = heading)
        Spacer(modifier = Modifier.height(10.dp))
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            Text(text = "Email:", style = subheading)
            Text(text = "support@transfertoinr.com", style = body)
        }
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            Text(text = "Phone", style = subheading)
            Text(text = "924932343", style = body)
        }

        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "FAQ", style = heading)
        Spacer(modifier = Modifier.height(5.dp))

        Column(
            modifier = Modifier.verticalScroll(scroll),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            FAQQuery(question = QUESTION1, answer = ANSWER1)
            FAQQuery(question = QUESTION2, answer = ANSWER2)
            FAQQuery(question = QUESTION3, answer = ANSWER3)
            FAQQuery(question = QUESTION4, answer = ANSWER4)
            FAQQuery(question = QUESTION5, answer = ANSWER5)
            FAQQuery(question = QUESTION6, answer = ANSWER6)
            FAQQuery(question = QUESTION7, answer = ANSWER7)
            FAQQuery(question = QUESTION8, answer = ANSWER8)
            FAQQuery(question = QUESTION9, answer = ANSWER9)
        }








    }
}

@Composable
fun FAQQuery(question: String, answer: String) {

    val subheading = TextStyle(fontFamily = fontFamily, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = JungleGreen)
    val body = TextStyle(fontFamily = fontFamily, fontSize = 14.sp, fontWeight = FontWeight.Normal, color = JungleGreen)

    Column {
        Text(text = question, style = subheading)
        Text(text = answer, style = body)
    }

}


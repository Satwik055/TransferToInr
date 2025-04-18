package com.satwik.transfertoinr.features.home

import AutoSlidingCarousel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.satwik.transfertoinr.core.designsystem.components.RateTable
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily
import com.satwik.transfertoinr.core.model.ExchangeRate
import com.satwik.transfertoinr.core.model.Profile
import com.satwik.transfertoinr.core.utils.extension_functions.firstWord
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    val viewModel =  koinViewModel<HomeScreenViewModel>()
    val userState = viewModel.userInfoState.collectAsState().value
    val rateState = viewModel.exchangeRateState.collectAsState().value

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (userState.isLoading || rateState.isLoading){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = JungleGreen)
        }
        if(userState.error.isNotEmpty()){
            val style = TextStyle(fontFamily = fontFamily, fontSize = 13.sp, fontWeight = FontWeight.Normal, color = Color.Red)
            Text(text = userState.error, style = style)
        }
        if(userState.profile.name.isNotEmpty()) {
            Content(user = userState.profile, rate = rateState.rate, viewModel = viewModel)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun Content(modifier: Modifier = Modifier, user: Profile, rate:ExchangeRate, viewModel: HomeScreenViewModel) {

    val style = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.SemiBold, fontSize = 37.sp, color = JungleGreen)
    val carouselImageLinkResultState = viewModel.carousellImageLinkResult.collectAsState().value
//    val images = carouselImageLinkResultState.successResult as? List<*>
    val images = (carouselImageLinkResultState.successResult as? List<*>) ?: emptyList<Any>()
    Column (modifier = modifier){

        Text(text = "Hey ${user.name.firstWord()}", style=style, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Spacer(modifier = Modifier.height(10.dp))

        AutoSlidingCarousel(
            itemsCount = images.size,
            itemContent = { index ->
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(images[index]).build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.height(200.dp)
                )
            }
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "We provide best exchange rates",
            style=style.copy(fontSize = 18.sp),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(10.dp))

        RateTable(
            currency = user.preferred_currency,
            ttiRate = rate.tti,
            ttiFees = rate.tti_fees,
            wiseRate = rate.wise,
            wiseFees = rate.wise_fees,
            skrillRate = rate.skrill,
            skrillFees = rate.skrill_fees,
            paypalRate = rate.paypal,
            paypalFees = rate.paypal_fees,
            bankRate = rate.bank,
            bankFees = rate.bank_fees
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview(modifier: Modifier = Modifier) {
    HomeScreen()
}







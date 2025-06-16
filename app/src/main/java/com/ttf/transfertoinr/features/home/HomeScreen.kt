package com.ttf.transfertoinr.features.home

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
import com.ttf.transfertoinr.core.designsystem.components.ErrorText
import com.ttf.transfertoinr.core.designsystem.components.RateTable
import com.ttf.transfertoinr.core.designsystem.theme.JungleGreen
import com.ttf.transfertoinr.core.designsystem.theme.fontFamily
import com.ttf.transfertoinr.core.model.ExchangeRate
import com.ttf.transfertoinr.core.model.Profile
import com.ttf.transfertoinr.core.model.Result
import com.ttf.transfertoinr.core.utils.extension_functions.firstWord
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    val viewModel = koinViewModel<HomeScreenViewModel>()
    val profileState = viewModel.profileState.collectAsState().value
    val rateState = viewModel.exchangeRateState.collectAsState().value
    val carouselImageLinkState = viewModel.carousellImageLinkResult.collectAsState().value

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if(profileState.isLoading || rateState.isLoading){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = JungleGreen)
        }
        if(profileState.error.isNotEmpty() || rateState.error.isNotEmpty()){
            ErrorText(message = profileState.error)
            ErrorText(message = rateState.error)
        }
        if(profileState.success && rateState.success) {
            Content(user = profileState.successResult as Profile, rate = rateState.successResult as ExchangeRate, carouselImageLinkState = carouselImageLinkState)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun Content(modifier: Modifier = Modifier, user: Profile, rate:ExchangeRate, carouselImageLinkState:Result) {

    val style = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.SemiBold, fontSize = 37.sp, color = JungleGreen)
    val images = (carouselImageLinkState.successResult as? List<*>) ?: emptyList<Any>()
    Column (modifier = modifier){

        Text(text = "Hey ${user.name.firstWord()}", style=style, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Spacer(modifier = Modifier.height(10.dp))

        //handle image link states
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







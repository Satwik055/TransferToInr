package com.satwik.transfertoinr.features.home

import AutoSlidingCarousel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.satwik.transfertoinr.core.designsystem.components.RateTable
import com.satwik.transfertoinr.core.designsystem.theme.JungleGreen
import com.satwik.transfertoinr.core.designsystem.theme.fontFamily
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Content(
        modifier = modifier.fillMaxSize()
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun Content(modifier: Modifier = Modifier) {

    val viewModel =  koinViewModel<HomeScreenViewModel>()
    val user = viewModel.userInfoState.value.userInfo
    val rate = viewModel.exchangeRateState.value.rate
    val style = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.SemiBold, fontSize = 37.sp, color = JungleGreen)
    val images:List<String> = listOf(
        "https://as2.ftcdn.net/v2/jpg/04/86/72/71/1000_F_486727138_LIbtjQYhz2nwYFoziXPeUIFSpdz5tiHZ.jpg",
        "https://img.freepik.com/free-vector/flat-abstract-sales-banner-with-offer_23-2149020199.jpg",
        "https://img.freepik.com/premium-vector/extra-discount-3d-sale-banner-template-design-background_416835-474.jpg"
    )

    LaunchedEffect(Unit) {
        viewModel.getExchangeRates()
        viewModel.getUserInfo()
    }

    Column (modifier = modifier){
        Text(text = "Hey ${user.name}", style=style, maxLines = 1, overflow = TextOverflow.Ellipsis)
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







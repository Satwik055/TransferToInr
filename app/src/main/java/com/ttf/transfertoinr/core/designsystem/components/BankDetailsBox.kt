package com.ttf.transfertoinr.core.designsystem.components

import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ttf.transfertoinr.core.designsystem.theme.LightGrey
import com.ttf.transfertoinr.core.model.CurrencyType


@Composable
fun BankDetailsBox(
    currency: CurrencyType,
    modifier: Modifier = Modifier,
    context: Context
) {

    Column(
        modifier
            .border(
                width = 1.dp,
                color = LightGrey,
                shape = RoundedCornerShape(6.dp)
            )
            .padding(13.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)

    ) {
        when(currency){
            CurrencyType.USD ->
                {
                    CopyableText(title = "Name", copyableText = "Swathi Police", context = context)
                    CopyableText(title = "Account Number", copyableText = "8314603017", context = context)
                    CopyableText(title = "Account Type", copyableText = "Checking", context = context)
                    CopyableText(title = "Routing Number", copyableText = "026073150", context = context)

                }
            CurrencyType.EUR ->
                {
                    CopyableText(title = "Name", copyableText = "Shyam Sundhar Reddy Sappidi", context = context)
                    CopyableText(title = "IBAN", copyableText = "DE89 5002 4024 6773 8830 01", context = context)
                    CopyableText(title = "BIC", copyableText = "DEFFDEFFXXX", context = context)
                }
            CurrencyType.GBP ->
                {
                    CopyableText(title = "Name", copyableText = "Swathi Police", context = context)
                    CopyableText(title = "Account Number", copyableText = "31118288", context = context)
                    CopyableText(title = "Sort Code", copyableText = "23-08-01", context = context)
                    CopyableText(title = "IBAN", copyableText = "GB95 TRWI 2308 0131 1182 88", context = context)
                }
            CurrencyType.AUD ->
                {
                    CopyableText(title = "Name", copyableText = "Swathi Police", context = context)
                    CopyableText(title = "Account Number", copyableText = "220336755", context = context)
                    CopyableText(title = "BSB", copyableText = "774001", context = context)
                }
            CurrencyType.CAD ->
                {
                    CopyableText(title = "Name", copyableText = "Swathi Police", context = context)
                    CopyableText(title = "Account Number", copyableText = "200116298434", context = context)
                    CopyableText(title = "Institution Number", copyableText = "621", context = context)
                    CopyableText(title = "Transit Number", copyableText = "16001", context = context)
                }

            CurrencyType.BANK -> {
                CopyableText(title = "Name", copyableText = "Swathi Police", context = context)
            }
        }
    }
}
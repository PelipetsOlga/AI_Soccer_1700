package com.manager1700.soccer.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manager1700.soccer.Montserrat
import com.manager1700.soccer.ui.theme.colorGreen
import com.manager1700.soccer.ui.theme.colorGrey_3b
import com.manager1700.soccer.ui.theme.colorGrey_66
import com.manager1700.soccer.ui.theme.colorWhite

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = { onClick() },
        enabled = enabled,
        modifier = modifier
            .padding(vertical = 8.dp, horizontal = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorGreen,
            disabledContainerColor = colorGreen.copy(alpha = 0.6f),
        )
    ) {
        AutoSizeText(
            text = text.uppercase(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = if (enabled) colorWhite else colorWhite.copy(alpha = 0.6f),
            fontFamily = Montserrat,
        )
    }
}

@Composable
fun SecondaryButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = { onClick() },
        enabled = enabled,
        modifier = modifier
            .padding(vertical = 8.dp, horizontal = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorGrey_3b,
            disabledContentColor = colorGrey_3b.copy(alpha = 0.8f)
        )
    ) {
        AutoSizeText(
            text = text.uppercase(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = colorWhite,
            fontFamily = Montserrat,
        )
    }
}

@Composable
fun SmallGreyButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = { onClick() },
        enabled = enabled,
        contentPadding = PaddingValues(
            start = 4.dp,
            top = 0.dp,
            end = 4.dp,
            bottom = 0.dp,
        ),
        modifier = modifier
            .padding(vertical = 1.dp, horizontal = 2.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorGrey_66,
            disabledContentColor = colorGrey_66.copy(alpha = 0.8f)
        )
    ) {
        AutoSizeText(
            text = text.uppercase(),
            fontSize = 9.sp,
            fontWeight = FontWeight.Normal,
            color = colorWhite,
            fontFamily = Montserrat,
        )
    }
}
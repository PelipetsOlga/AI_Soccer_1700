package com.manager1700.soccer.ui.components

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
fun PrimaryButton(onClick: () -> Unit, text: String, modifier: Modifier = Modifier) {
    Button(
        onClick = { onClick() },
        modifier = modifier
            .padding(vertical = 8.dp, horizontal = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorGreen
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
fun SecondaryButton(onClick: () -> Unit, text: String, modifier: Modifier = Modifier) {
    Button(
        onClick = { onClick() },
        modifier = modifier
            .padding(vertical = 8.dp, horizontal = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorGrey_3b
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
fun SmallGreyButton(onClick: () -> Unit, text: String, modifier: Modifier = Modifier) {
    Button(
        onClick = { onClick() },
        modifier = modifier
            .padding(vertical = 4.dp, horizontal = 8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorGrey_66
        )
    ) {
        AutoSizeText(
            text = text.uppercase(),
            fontSize = 10.sp,
            fontWeight = FontWeight.Normal,
            color = colorWhite,
            fontFamily = Montserrat,
        )
    }
}
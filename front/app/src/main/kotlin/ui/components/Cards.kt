package com.manager1700.soccer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manager1700.soccer.Montserrat
import com.manager1700.soccer.R
import com.manager1700.soccer.ui.theme.SoccerManagerTheme
import com.manager1700.soccer.ui.theme.colorGrey_2b
import com.manager1700.soccer.ui.theme.colorGrey_3b
import com.manager1700.soccer.ui.theme.colorWhite
import com.manager1700.soccer.ui.utils.cardBigClipShape
import com.manager1700.soccer.ui.utils.cardBrushLightGradient
import com.manager1700.soccer.ui.utils.cardClipShape

@Composable
fun Card(title: String, modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(cardBigClipShape)
            .background(brush = cardBrushLightGradient)
            .padding(all = 1.dp)
            .background(colorGrey_2b)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = title.uppercase(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = colorWhite,
                fontFamily = Montserrat,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            )
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp)
                    .clip(cardClipShape)
                    .background(colorGrey_3b)
            ) {
                content()
            }
        }
    }
}

@Composable
@Preview
private fun CardPreview() {
    SoccerManagerTheme {
        Card(title = "Title", modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(164.dp)
            ) {
                Text(
                    text = stringResource(R.string.home_title),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
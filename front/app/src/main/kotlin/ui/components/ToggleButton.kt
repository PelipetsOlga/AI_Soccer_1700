package com.manager1700.soccer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manager1700.soccer.Montserrat
import com.manager1700.soccer.ui.theme.SoccerManagerTheme
import com.manager1700.soccer.ui.theme.colorBlack
import com.manager1700.soccer.ui.theme.colorGrey_2b
import com.manager1700.soccer.ui.utils.PreviewApp
import com.manager1700.soccer.ui.utils.cardBigClipShape
import com.manager1700.soccer.ui.utils.cardBrushDarkVerticalGradient
import com.manager1700.soccer.ui.utils.cardBrushLightGradient
import com.manager1700.soccer.ui.utils.cardVeryLightGradient

@Composable
fun BigToggleButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        contentPadding = PaddingValues(
            start = 16.dp,
            top = 0.dp,
            end = 16.dp,
            bottom = 0.dp,
        ),
        modifier = modifier
            .clip(cardBigClipShape)
            .background(brush = cardBrushLightGradient)
            .padding(all = 1.dp)
            .background(colorGrey_2b)
            .background(
                brush =
                    if (isSelected) {
                        cardVeryLightGradient
                    } else {
                        cardBrushDarkVerticalGradient
                    }
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContentColor = Color.Transparent,
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = text.uppercase(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color.White,
            fontFamily = Montserrat,
        )
    }
}

@Composable
fun SmallToggleButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        contentPadding = PaddingValues(
            start = 8.dp,
            top = 0.dp,
            end = 8.dp,
            bottom = 0.dp,
        ),
        modifier = modifier
            .clip(cardBigClipShape)
            .background(brush = cardBrushLightGradient)
            .padding(all = 1.dp)
            .background(colorGrey_2b)
            .background(
                brush =
                    if (isSelected) {
                        cardVeryLightGradient
                    } else {
                        cardBrushDarkVerticalGradient
                    }
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContentColor = Color.Transparent,
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = text.uppercase(),
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = Color.White,
            fontFamily = Montserrat,
        )
    }
}

@PreviewApp
@Composable
private fun ToggleButtonPreview() {
    SoccerManagerTheme {
        Column(
            modifier = Modifier
                .background(colorBlack)
                .padding(all = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BigToggleButton(
                text = "Attendance",
                isSelected = true,
                onClick = {}
            )
            BigToggleButton(
                text = "Calendar",
                isSelected = false,
                onClick = {}
            )
            SmallToggleButton(
                text = "By event",
                isSelected = true,
                onClick = {}
            )
            SmallToggleButton(
                text = "By player",
                isSelected = false,
                onClick = {}
            )
        }
    }
}

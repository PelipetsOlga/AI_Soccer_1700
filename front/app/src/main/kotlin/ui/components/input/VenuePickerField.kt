package com.manager1700.soccer.ui.components.input

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manager1700.soccer.Montserrat
import com.manager1700.soccer.R
import com.manager1700.soccer.ui.theme.colorBlack
import com.manager1700.soccer.ui.theme.colorGrey_89
import com.manager1700.soccer.ui.theme.colorWhite
import com.manager1700.soccer.ui.utils.cardVeryBigClipShape

@Composable
fun VenuePickerField(
    value: String,
    onValueChange: (String) -> Unit,
    onVenuePickerClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.field_venue).uppercase(),
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = colorWhite,
            fontFamily = Montserrat,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = { onValueChange(it) },
            maxLines = 1,
            readOnly = true,
            enabled = false,
            placeholder = {
                Text(
                    text = stringResource(R.string.tint_venue),
                    fontSize = 12.sp,
                    minLines = 1,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic,
                    color = colorBlack.copy(alpha = 0.6f),
                    fontFamily = Montserrat,
                    modifier = Modifier.fillMaxWidth(),
                )
            },
            colors = getInputColors(),
            modifier = Modifier
                .fillMaxWidth()
                .clip(cardVeryBigClipShape)
                .background(colorGrey_89)
                .clickable { onVenuePickerClick() }
        )
    }
}

// Venue options
object VenueOptions {
    const val TRAINING_BASE_1 = "Training Base #1"
    const val TRAINING_BASE_2 = "Training Base #2"
    const val ENTER = "Enter"
    
    val ALL_VENUES = listOf(
        TRAINING_BASE_1,
        TRAINING_BASE_2,
        ENTER
    )
}


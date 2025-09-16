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
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun TimePickerField(
    value: String,
    onValueChange: (String) -> Unit,
    onTimePickerClick: () -> Unit,
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = label.uppercase(),
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
                    text = placeholder,
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
                .clickable { onTimePickerClick() }
        )
    }
}

fun formatTime(time: LocalTime): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return time.format(formatter)
}

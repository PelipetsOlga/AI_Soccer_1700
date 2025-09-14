package com.manager1700.soccer.ui.components.input

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.manager1700.soccer.domain.models.Foot
import com.manager1700.soccer.ui.theme.colorBlack
import com.manager1700.soccer.ui.theme.colorGrey_89
import com.manager1700.soccer.ui.theme.colorWhite
import com.manager1700.soccer.ui.utils.cardVeryBigClipShape


@Composable
fun FootInputField(
    value: String,
    onClick: (Foot) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    LaunchedEffect(isFocused) {
        if (isFocused) {
            expanded = true
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.field_foot).uppercase(),
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = colorWhite,
            fontFamily = Montserrat,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = { },
                maxLines = 1,
                readOnly = true,
                interactionSource = interactionSource,
                placeholder = {
                    Text(
                        text = stringResource(R.string.tint_foot),
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
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(colorWhite)
            ) {
                Foot.entries.forEach { foot ->
                    DropdownMenuItem(
                        onClick = {
                            onClick(foot)
                            expanded = false
                        }
                    ) {
                        Text(
                            text = stringResource(foot.titleId),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = colorBlack,
                            fontFamily = Montserrat,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}


package com.manager1700.soccer.ui.components.training_input

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manager1700.soccer.Montserrat
import com.manager1700.soccer.domain.models.SportEventStatus
import com.manager1700.soccer.ui.theme.colorBlack
import com.manager1700.soccer.ui.theme.colorGrey_2b
import com.manager1700.soccer.ui.theme.colorGrey_89
import com.manager1700.soccer.ui.theme.colorLightGreen
import com.manager1700.soccer.ui.theme.colorLightPink
import com.manager1700.soccer.ui.theme.colorWhite
import com.manager1700.soccer.ui.utils.cardVeryBigClipShape

@Composable
fun SportEventStatusDropdown(
    currentStatus: SportEventStatus,
    onStatusSelected: (SportEventStatus) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
    ) {
        // Status chip that acts as dropdown trigger
        val color = when (currentStatus) {
            SportEventStatus.Scheduled -> colorWhite
            SportEventStatus.Canceled -> colorLightPink
            SportEventStatus.Completed -> colorLightGreen
        }
        
        Text(
            text = stringResource(currentStatus.titleId),
            color = colorGrey_2b,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .clip(cardVeryBigClipShape)
                .background(color)
                .padding(vertical = 2.dp, horizontal = 8.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    expanded = true
                }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(colorWhite)
        ) {
            SportEventStatus.entries.forEach { status ->
                DropdownMenuItem(
                    onClick = {
                        onStatusSelected(status)
                        expanded = false
                    }
                ) {
                    Text(
                        text = stringResource(status.titleId),
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

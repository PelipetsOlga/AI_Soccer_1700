package com.manager1700.soccer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.manager1700.soccer.Montserrat
import com.manager1700.soccer.domain.models.PlayerStatus
import com.manager1700.soccer.domain.models.SportEventStatus
import com.manager1700.soccer.ui.theme.colorGrey_2b
import com.manager1700.soccer.ui.theme.colorLightGreen
import com.manager1700.soccer.ui.theme.colorLightPink
import com.manager1700.soccer.ui.theme.colorWhite
import com.manager1700.soccer.ui.theme.colorYellow

@Composable
fun FitnessChip(value: Int) {
    val color = if (value < 70) {
        colorLightPink
    } else if (value > 85) {
        colorLightGreen
    } else {
        colorYellow
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(color)
            .size(8.dp)
    )
}

@Preview
@Composable
fun FitnessChipPreview() {
    Column(
    ) {
        Row {
            FitnessChip(5)
            Text("Player", modifier = Modifier.padding(start = 8.dp))
        }
        Row {
            FitnessChip(75)
            Text("Player", modifier = Modifier.padding(start = 8.dp))
        }
        Row {
            FitnessChip(95)
            Text("Player", modifier = Modifier.padding(start = 8.dp))
        }
    }
}

@Composable
fun PlayerStatusSmallChip(value: PlayerStatus) {
    val color = if (value == PlayerStatus.Active) {
        colorLightGreen
    } else {
        colorLightPink
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(color)
            .size(8.dp)
    )
}

@Composable
fun PlayerStatusChip(value: PlayerStatus) {
    val color = if (value == PlayerStatus.Active) {
        colorLightGreen
    } else {
        colorLightPink
    }
    Text(
        text = stringResource(value.titleId),
        color = colorGrey_2b,
        fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(color)
            .padding(vertical = 2.dp, horizontal = 8.dp)
    )
}

@Preview
@Composable
fun PlayerStatusChipPreview() {
    Column(
    ) {
        PlayerStatusChip(PlayerStatus.Active)
        PlayerStatusChip(PlayerStatus.Injured)
    }
}

@Composable
fun SportEventStatusChip(value: SportEventStatus) {
    val color = if (value == SportEventStatus.Scheduled) {
        colorWhite
    } else if (value == SportEventStatus.Canceled) {
        colorLightPink
    } else {
        colorLightGreen
    }
    Text(
        text = stringResource(value.titleId),
        color = colorGrey_2b,
        fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(color)
            .padding(vertical = 2.dp, horizontal = 8.dp)
    )
}

@Preview
@Composable
fun SportEventStatusChipPreview() {
    Column {
        SportEventStatusChip(SportEventStatus.Scheduled)
        SportEventStatusChip(SportEventStatus.Canceled)
        SportEventStatusChip(SportEventStatus.Completed)
    }
}


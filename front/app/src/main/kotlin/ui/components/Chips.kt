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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.manager1700.soccer.ui.theme.colorLightGreen
import com.manager1700.soccer.ui.theme.colorLightPink
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
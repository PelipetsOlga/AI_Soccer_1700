package com.manager1700.soccer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manager1700.soccer.Montserrat
import com.manager1700.soccer.ui.feature_training.TrainingScreenContract
import com.manager1700.soccer.ui.theme.colorGrey_2b
import com.manager1700.soccer.ui.theme.colorGrey_89
import com.manager1700.soccer.ui.theme.colorWhite

@Composable
fun FilterTabs(
    selectedViewType: TrainingScreenContract.ViewType,
    selectedFilterType: TrainingScreenContract.FilterType,
    onViewTypeChanged: (TrainingScreenContract.ViewType) -> Unit,
    onFilterTypeChanged: (TrainingScreenContract.FilterType) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(colorGrey_2b)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // View type tabs (LIST / CALENDAR)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TrainingScreenContract.ViewType.values().forEach { viewType ->
                FilterTab(
                    text = viewType.name,
                    isSelected = selectedViewType == viewType,
                    onClick = { onViewTypeChanged(viewType) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
        
        // Filter type tabs (ALL / UPCOMING / PAST)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TrainingScreenContract.FilterType.values().forEach { filterType ->
                FilterTab(
                    text = filterType.name,
                    isSelected = selectedFilterType == filterType,
                    onClick = { onFilterTypeChanged(filterType) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun FilterTab(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) colorGrey_89 else Color.Transparent
    val textColor = if (isSelected) colorWhite else colorGrey_89
    
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = textColor,
            fontFamily = Montserrat
        )
    }
}

package com.manager1700.soccer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.manager1700.soccer.ui.feature_match.MatchScreenContract
import com.manager1700.soccer.ui.theme.colorGrey_2b

@Composable
fun MatchFilterTabs(
    selectedViewType: MatchScreenContract.ViewType,
    selectedFilterType: MatchScreenContract.FilterType,
    onViewTypeChanged: (MatchScreenContract.ViewType) -> Unit,
    onFilterTypeChanged: (MatchScreenContract.FilterType) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // View type tabs (LIST / CALENDAR)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MatchScreenContract.ViewType.entries.forEach { viewType ->
                BigToggleButton(
                    text = viewType.name,
                    isSelected = selectedViewType == viewType,
                    onClick = { onViewTypeChanged(viewType) },
                )
            }
        }

        if (selectedViewType == MatchScreenContract.ViewType.LIST) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MatchScreenContract.FilterType.entries.forEach { filterType ->
                    SmallToggleButton(
                        text = filterType.name,
                        isSelected = selectedFilterType == filterType,
                        onClick = { onFilterTypeChanged(filterType) },
                    )
                }
            }
        }
    }
}

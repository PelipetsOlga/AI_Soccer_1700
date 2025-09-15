package com.manager1700.soccer.ui.feature_team.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manager1700.soccer.Montserrat
import com.manager1700.soccer.R
import com.manager1700.soccer.domain.models.Player
import com.manager1700.soccer.ui.components.AppCard
import com.manager1700.soccer.ui.components.FitnessChip
import com.manager1700.soccer.ui.components.PlayerStatusChip
import com.manager1700.soccer.ui.components.SmallGreyButton
import com.manager1700.soccer.ui.feature_team.TeamScreenContract
import com.manager1700.soccer.ui.theme.SoccerManagerTheme
import com.manager1700.soccer.ui.theme.colorBlack
import com.manager1700.soccer.ui.theme.colorWhite
import com.manager1700.soccer.ui.theme.colorYellow
import com.manager1700.soccer.ui.utils.PreviewApp

@Composable
fun PlayerCard(
    player: Player,
    onEvent: (TeamScreenContract.Event) -> Unit,
) {

    var expanded by remember { mutableStateOf(false) }

    ExpandablePlayerCard(
        player = player,
        expanded = expanded,
        onProfileClick = { expanded = true },
        onCloseClick = { expanded = false },
        onEditClick = {},
        onRemoveClick = { onEvent(TeamScreenContract.Event.RemovePlayerClicked(player)) },
        onSetActiveClick = {},
        onSetInjuredClick = {}
    )
}

@Composable
private fun ExpandablePlayerCard(
    player: Player,
    expanded: Boolean,
    onProfileClick: () -> Unit,
    onSetInjuredClick: () -> Unit,
    onRemoveClick: () -> Unit,
    onCloseClick: () -> Unit,
    onSetActiveClick: () -> Unit,
    onEditClick: () -> Unit,
) {
    AppCard(
        title = if (expanded) {
            player.name
        } else {
            "${player.name} | ${player.number} | ${player.position.shortName}"
        }
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            if (expanded.not()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    FitnessChip(player.fitness)
                    Text(
                        text = "${stringResource(R.string.field_fitness)}: ${player.fitness}%",
                        fontSize = 16.sp,
                        fontFamily = Montserrat,
                        color = colorWhite,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    PlayerStatusChip(player.status)
                }
                Text(
                    text = player.note,
                    fontSize = 14.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.ExtraLight,
                    color = colorWhite,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    SmallGreyButton(
                        text = stringResource(R.string.btn_profile),
                        onClick = { onProfileClick() })
                    SmallGreyButton(
                        text = stringResource(R.string.btn_set_injured),
                        onClick = { onSetInjuredClick() })
                    SmallGreyButton(
                        text = stringResource(R.string.btn_remove),
                        onClick = { onRemoveClick() })
                }
            } else {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        Modifier
                            .background(colorYellow)
                            .width(75.dp)
                            .height(100.dp)
                    )
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    ) {
                        Text(
                            text = "${stringResource(R.string.field_name)}: ${player.name}",
                            fontSize = 14.sp,
                            fontFamily = Montserrat,
                            color = colorWhite,
                        )
                        Text(
                            text = "${stringResource(R.string.field_number)}: ${player.number}",
                            fontSize = 12.sp,
                            fontFamily = Montserrat,
                            color = colorWhite,
                        )
                        Text(
                            text = "${stringResource(R.string.field_position)}: ${
                                stringResource(
                                    player.position.fullNameId
                                )
                            }",
                            fontSize = 12.sp,
                            fontFamily = Montserrat,
                            color = colorWhite,
                        )
                        Text(
                            text = "${stringResource(R.string.field_foot)}: ${stringResource(player.foot.titleId)}",
                            fontSize = 12.sp,
                            fontFamily = Montserrat,
                            color = colorWhite,
                        )
                    }
                }
                Text(
                    text = player.note,
                    fontSize = 14.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.ExtraLight,
                    color = colorWhite,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .padding(top = 4.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    SmallGreyButton(
                        text = stringResource(R.string.btn_edit),
                        onClick = { onEditClick })
                    SmallGreyButton(
                        text = stringResource(R.string.btn_set_active),
                        onClick = { onSetActiveClick() })
                    SmallGreyButton(
                        text = stringResource(R.string.btn_close),
                        onClick = { onCloseClick() })
                }
            }
        }
    }

}

@Composable
@PreviewApp
private fun PlayerCardPreview() {
    SoccerManagerTheme {
        Column(
            modifier = Modifier
                .background(colorBlack)
                .padding(all = 16.dp)
        ) {
            ExpandablePlayerCard(player = Player.TEST_1, expanded = false, {}, {}, {}, {}, {}, {})
            Spacer(Modifier.height(16.dp))
            ExpandablePlayerCard(player = Player.TEST_1, expanded = true, {}, {}, {}, {}, {}, {})
        }
    }
}

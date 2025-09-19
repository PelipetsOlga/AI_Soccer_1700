package com.manager1700.soccer.ui.feature_team.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.manager1700.soccer.Montserrat
import com.manager1700.soccer.R
import com.manager1700.soccer.domain.models.Player
import com.manager1700.soccer.domain.models.PlayerStatus
import com.manager1700.soccer.ui.components.AppCard
import com.manager1700.soccer.ui.components.FitnessChip
import com.manager1700.soccer.ui.components.PlayerStatusChip
import com.manager1700.soccer.ui.components.PlayerStatusSmallChip
import com.manager1700.soccer.ui.components.SmallGreyButton
import com.manager1700.soccer.ui.feature_team.TeamScreenContract
import com.manager1700.soccer.ui.theme.SoccerManagerTheme
import com.manager1700.soccer.ui.theme.colorBlack
import com.manager1700.soccer.ui.theme.colorWhite
import com.manager1700.soccer.ui.utils.PreviewApp
import java.io.File

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
        onEditClick = { onEvent(TeamScreenContract.Event.EditPlayerClicked(player)) },
        onRemoveClick = { onEvent(TeamScreenContract.Event.RemovePlayerClicked(player)) },
        onSetActiveClick = { onEvent(TeamScreenContract.Event.SetActiveClicked(player)) },
        onSetInjuredClick = { onEvent(TeamScreenContract.Event.SetInjuredClicked(player)) }
    )
}

@Composable
private fun ExpandablePlayerCard(
    player: Player,
    expanded: Boolean,
    onProfileClick: () -> Unit,
    onSetInjuredClick: (Player) -> Unit,
    onRemoveClick: () -> Unit,
    onCloseClick: () -> Unit,
    onSetActiveClick: () -> Unit,
    onEditClick: () -> Unit,
) {
    val context = LocalContext.current
    AppCard(
        title = if (expanded) {
            player.name
        } else {
            "${player.name} | ${player.number} | ${player.position.shortName}"
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 4.dp)
        ) {
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
                        onClick = { onProfileClick() },
                        modifier = Modifier.weight(1f)
                    )
                    if (player.status == com.manager1700.soccer.domain.models.PlayerStatus.Active) {
                        SmallGreyButton(
                            text = stringResource(R.string.btn_set_injured),
                            onClick = { onSetInjuredClick(player) },
                            modifier = Modifier.weight(1f)
                        )
                    } else {
                        SmallGreyButton(
                            text = stringResource(R.string.btn_set_active),
                            onClick = { onSetActiveClick() },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    SmallGreyButton(
                        text = stringResource(R.string.btn_remove),
                        onClick = { onRemoveClick() },
                        modifier = Modifier.weight(1f)
                    )
                }
            } else {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .size(100.dp, 135.dp)
                            .clip(RoundedCornerShape(8.dp))
                    ) {
                        if (player.imageUrl != null) {
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data(File(player.imageUrl))
                                    .build(),
                                contentDescription = "Player photo",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(75f / 100f),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
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

                        val injuredDate =
                            if (player.status == PlayerStatus.Injured) ", (${player.dateOfInjury})" else ""

                        Text(
                            text = "${stringResource(R.string.field_foot)}: ${
                                stringResource(
                                    player.foot.titleId
                                )
                            }",
                            fontSize = 12.sp,
                            fontFamily = Montserrat,
                            color = colorWhite,
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            FitnessChip(player.fitness)
                            Text(
                                text = "${stringResource(R.string.field_fitness)}: ${player.fitness}%",
                                fontSize = 16.sp,
                                fontFamily = Montserrat,
                                color = colorWhite,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            PlayerStatusSmallChip(player.status)
                            Text(
                                text = "${stringResource(R.string.field_status)}: ${
                                    stringResource(
                                        player.status.titleId
                                    )
                                }$injuredDate",
                                fontSize = 16.sp,
                                fontFamily = Montserrat,
                                color = colorWhite,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                        }
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
                        onClick = { onEditClick() },
                        modifier = Modifier.weight(1f)
                    )
                    if (player.status == com.manager1700.soccer.domain.models.PlayerStatus.Active) {
                        SmallGreyButton(
                            text = stringResource(R.string.btn_set_injured),
                            onClick = { onSetInjuredClick(player) },
                            modifier = Modifier.weight(1f)
                        )
                    } else {
                        SmallGreyButton(
                            text = stringResource(R.string.btn_set_active),
                            onClick = { onSetActiveClick() },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    SmallGreyButton(
                        text = stringResource(R.string.btn_close),
                        onClick = { onCloseClick() },
                        modifier = Modifier.weight(1f)
                    )
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
            ExpandablePlayerCard(
                player = Player.TEST_1.copy(
                status = PlayerStatus.Injured,
                dateOfInjury = "25.07.2025"
            ), expanded = true, {}, {}, {}, {}, {}, {})
        }
    }
}

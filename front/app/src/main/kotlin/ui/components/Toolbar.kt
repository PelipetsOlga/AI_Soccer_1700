package com.manager1700.soccer.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manager1700.soccer.R
import com.manager1700.soccer.ui.theme.SoccerManagerTheme
import com.manager1700.soccer.ui.theme.colorRed
import com.manager1700.soccer.ui.theme.colorWhite
import com.manager1700.soccer.ui.utils.PreviewApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(
    title: String,
    showBackButton: Boolean = false,
    showSettingsButton: Boolean = false,
    onBackClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    TopAppBar(
        windowInsets = WindowInsets(top = 0.dp, bottom = 0.dp, left = 0.dp, right = .0.dp),
        title = {
            AutoSizeText(
                text = title.uppercase(),
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorWhite,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onBackClick) {
                    Image(
                        painter = painterResource(id = R.mipmap.back_btn),
                        contentDescription = "Back",
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
            }
        },
        actions = {
            if (showSettingsButton) {
                IconButton(onClick = onSettingsClick) {
                    Image(
                        painter = painterResource(id = R.mipmap.settings_btn),
                        contentDescription = "Settings",
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorRed,
            titleContentColor = colorWhite
        )
    )
}

@PreviewApp
@Composable
fun ToolbarPreview() {
    SoccerManagerTheme {
        Toolbar(
            title = "Home",
            showBackButton = true,
            showSettingsButton = true,
            onBackClick = {},
            onSettingsClick = {}
        )
    }
}

package com.manager1700.soccer.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
    modifier: Modifier = Modifier,
    showBackButton: Boolean = false,
    showSettingsButton: Boolean = false,
    onBackClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        // Main toolbar with red background
        TopAppBar(
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

        // White shadow at the bottom
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(colorWhite.copy(alpha = 0.6f))
                .align(Alignment.BottomCenter)
        )
    }
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

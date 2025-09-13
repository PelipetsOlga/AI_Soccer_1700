package com.manager1700.soccer.ui.utils

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    showSystemUi = true,
    name = "A Default",
    device = Devices.DEFAULT,
)
@Preview(
    showSystemUi = true,
    name = "A Large Font",
    device = Devices.DEFAULT,
    fontScale = 2f,
)
@Preview(
    showSystemUi = true,
    name = "Fold",
    device = Devices.PIXEL_FOLD,
)
annotation class PreviewApp
package com.manager1700.soccer.ui.utils

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.WindowManager
import kotlin.apply

fun Activity.setUpEdgeToEdgeMode(navBarColor: Int? = Color.rgb(59, 61, 79)) {
    window.apply {
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        statusBarColor = Color.TRANSPARENT
        navigationBarColor = (navBarColor ?: Color.TRANSPARENT)
        decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    }
}
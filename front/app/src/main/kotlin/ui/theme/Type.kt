package com.manager1700.soccer

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Montserrat = FontFamily(
    Font(R.font.montserrat_bold, FontWeight.Bold),
    Font(R.font.montserrat_extrabold, FontWeight.ExtraBold),
    Font(R.font.montserrat_semibold, FontWeight.SemiBold),
    Font(R.font.montserrat_medium, FontWeight.Medium),
    Font(R.font.montserrat_regular, FontWeight.Normal),
    Font(R.font.montserrat_light, FontWeight.Light),
    Font(R.font.montserrat_extralight, FontWeight.ExtraLight),
    Font(R.font.montserrat_thin, FontWeight.Thin),
)

val AppTypography = Typography(
    bodyLarge = Typography().bodyLarge.copy(fontFamily = Montserrat),
    bodyMedium = Typography().bodyMedium.copy(fontFamily = Montserrat),
    bodySmall = Typography().bodySmall.copy(fontFamily = Montserrat),
    titleLarge = Typography().titleLarge.copy(fontFamily = Montserrat),
    titleMedium = Typography().titleMedium.copy(fontFamily = Montserrat),
    titleSmall = Typography().titleSmall.copy(fontFamily = Montserrat),
    labelLarge = Typography().labelLarge.copy(fontFamily = Montserrat),
    labelMedium = Typography().labelMedium.copy(fontFamily = Montserrat),
    labelSmall = Typography().labelSmall.copy(fontFamily = Montserrat),
    displayLarge = Typography().displayLarge.copy(fontFamily = Montserrat),
    displayMedium = Typography().displayMedium.copy(fontFamily = Montserrat),
    displaySmall = Typography().displaySmall.copy(fontFamily = Montserrat),
    headlineLarge = Typography().headlineLarge.copy(fontFamily = Montserrat),
    headlineMedium = Typography().headlineMedium.copy(fontFamily = Montserrat),
    headlineSmall = Typography().headlineSmall.copy(fontFamily = Montserrat)
)

val AppTypography_H1 = AppTypography.bodyMedium.copy(
    fontSize = 43.sp,
    lineHeight = 50.sp,
)



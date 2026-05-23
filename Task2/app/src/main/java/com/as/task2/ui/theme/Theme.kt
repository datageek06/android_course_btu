package com.`as`.task2.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val AppColorScheme = darkColorScheme(
    primary = Main,
    onPrimary = DarkBg,
    background = DarkBg,
    surface = CardBg,
    onSurface = TextPrimary,
    error = ErrorRed
)

@Composable
fun StudentFormTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = Typography(),
        content = content
    )
}
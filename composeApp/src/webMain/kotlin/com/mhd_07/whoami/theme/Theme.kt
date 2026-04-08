package com.mhd_07.whoami.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

val theme = darkColorScheme(
    primary = primary,
    background = background,
    onBackground = onBackground,
    surface = surface,
    onSurface = onSurface,
    secondary = secondary
)

@Composable
fun WhoAmITheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = theme,
        typography = whoamiType(),
        content = content
    )
}
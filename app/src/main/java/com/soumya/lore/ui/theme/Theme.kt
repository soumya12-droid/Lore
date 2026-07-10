package com.soumya.lore.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Dark theme isn't part of the current design spec; this keeps the app
// usable in dark mode without clashing, and can be refined later.
private val DarkColorScheme = darkColorScheme(
    primary = LoreEmerald,
    secondary = LoreTextSecondary,
    tertiary = LoreEmerald
)

private val LightColorScheme = lightColorScheme(
    primary = LoreEmerald,
    onPrimary = Color.White,
    secondary = LoreTextSecondary,
    background = LoreBackground,
    onBackground = LoreTextPrimary,
    surface = LoreSurface,
    onSurface = LoreTextPrimary,
    surfaceVariant = LoreBackground,
    onSurfaceVariant = LoreTextSecondary,
    outline = LoreOutline
)

@Composable
fun LoreTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Off by default: dynamic (wallpaper-based) color would override the
    // brand palette above on Android 12+. Lore's identity is deliberate,
    // not device-dependent.
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
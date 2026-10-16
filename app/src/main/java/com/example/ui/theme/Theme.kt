package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = ElectricBlue,
    onPrimary = Navy950,
    primaryContainer = ElectricBlueDark,
    onPrimaryContainer = TextWhite,
    secondary = NeonPurple,
    onSecondary = Navy950,
    secondaryContainer = Navy700,
    onSecondaryContainer = NeonPurpleLight,
    tertiary = NeonViolet,
    onTertiary = Navy950,
    background = Navy950,
    onBackground = TextWhite,
    surface = Navy800,
    onSurface = TextWhite,
    surfaceVariant = Navy700,
    onSurfaceVariant = TextMuted,
    outline = Navy600
)

@Composable
fun HRAIStudioTheme(
    darkTheme: Boolean = true, // Default to stunning dark navy theme
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Navy950.toArgb()
            window.navigationBarColor = Navy950.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

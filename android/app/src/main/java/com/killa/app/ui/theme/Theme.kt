package com.killa.app.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// ============================================================
// Esquemas de color Killa (light/dark).
// Mapeo directo de la paleta en Color.kt a Material 3.
// ============================================================

private val KillaLightColorScheme = lightColorScheme(
    primary = KillaPrimaryLight,
    onPrimary = Color.White,
    primaryContainer = KillaPrimarySoftLight,
    onPrimaryContainer = KillaPrimaryLight,

    secondary = KillaSecondaryLight,
    onSecondary = Color.White,
    secondaryContainer = KillaSecondarySoftLight,
    onSecondaryContainer = KillaSecondaryLight,

    error = KillaErrorLight,
    onError = Color.White,
    errorContainer = KillaErrorSoftLight,
    onErrorContainer = KillaErrorLight,

    background = KillaBgLight,
    onBackground = KillaTextLight,
    surface = KillaSurfaceLight,
    onSurface = KillaTextLight,
    surfaceVariant = KillaSurfaceAltLight,
    onSurfaceVariant = KillaTextSecondaryLight,
    outline = KillaBorderLight
)

private val KillaDarkColorScheme = darkColorScheme(
    primary = KillaPrimaryLightDarkMode,
    onPrimary = Color.White,
    primaryContainer = KillaPrimarySoftDark,
    onPrimaryContainer = KillaPrimaryLightDarkMode,

    secondary = KillaSecondaryDark,
    onSecondary = Color.Black,
    secondaryContainer = KillaSecondarySoftDark,
    onSecondaryContainer = KillaSecondaryDark,

    error = KillaErrorDark,
    onError = Color.Black,
    errorContainer = KillaErrorSoftDark,
    onErrorContainer = KillaErrorDark,

    background = KillaBgDark,
    onBackground = KillaTextDark,
    surface = KillaSurfaceDark,
    onSurface = KillaTextDark,
    surfaceVariant = KillaSurfaceAltDark,
    onSurfaceVariant = KillaTextSecondaryDark,
    outline = KillaBorderDark
)

@Composable
fun KillaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) KillaDarkColorScheme else KillaLightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = KillaTypography,
        content = content
    )
}
package com.example.rickyandmortyapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = DarkAccentPrimary,
    background = DarkBackgroundPrimary,
    surface = DarkBackgroundSecondary,
    onPrimary = DarkForegroundOnPrimary,
    onBackground = DarkForegroundPrimary,
    onSurface = DarkForegroundSecondary,
    secondary = DarkBackgroundTertiary,
    tertiary = DarkBackgroundBottomNavigation,
    outline = DarkForegroundSeparator,
    inversePrimary = DarkIconsPrimary,
    inverseSurface = DarkIconsSecondary,
    inverseOnSurface = DarkIconsTertiary,
    onTertiary = DarkForegroundTertiary
)

private val LightColorScheme = lightColorScheme(
    primary = AccentPrimary,
    background = BackgroundPrimary,
    surface = BackgroundSecondary,
    onPrimary = ForegroundOnPrimary,
    onBackground = ForegroundPrimary,
    onSurface = ForegroundSecondary,
    secondary = BackgroundTertiary,
    tertiary = BackgroundBottomNavigation,
    outline = ForegroundSeparator,
    inversePrimary = IconsPrimary,
    inverseSurface = IconsSecondary,
    inverseOnSurface = IconsTertiary,
    onTertiary = ForegroundTertiary
)

@Composable
fun RickyAndMortyAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
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
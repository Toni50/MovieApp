package com.example.moviesapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = SpecialViolet,
    secondary = Teal,
    tertiary = Amber,

    background = BackgroundDark,
    surface = SurfaceDark,
    surfaceVariant = SurfaceVariantDark,

    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.Black,

    onBackground = Color.White,
    onSurface = Color.White,
    onSurfaceVariant = LightGray
)

private val LightColorScheme = lightColorScheme(
    primary = OceanBlue,
    secondary = ForestGreen,
    tertiary = DeepOrange,

    background = BackgroundLight,
    surface = SurfaceLight,
    surfaceVariant = SurfaceVariantLight,

    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,

    onBackground = Color.Black,
    onSurface = Color.Black,
    onSurfaceVariant = SlateGray
)

@Composable
fun MoviesAppTheme(
    darkTheme: Boolean = true, //isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
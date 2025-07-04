package com.flareframe.ui.theme

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

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFF7043),
    secondary = Color(0xFFFFB74D),
    background = Color(0xFF121212),
    primaryContainer = Color(0xFFBF360C),
    onPrimaryContainer = Color.White,

    onSecondary = Color.Black,
    secondaryContainer = Color(0xFFEF6C00),
    onSecondaryContainer = Color.White,


    onBackground = Color(0xFFECECEC),

    surface = Color(0xFF1E1E1E),
    onSurface = Color.White,

    error = Color(0xFFCF6679),
    onError = Color.Black
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFFF5722),         // Flame Orange
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFCCBC), // Soft peach background
    onPrimaryContainer = Color(0xFF3E2723),

    secondary = Color(0xFFFF9800),       // Amber
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFFFFE0B2),
    onSecondaryContainer = Color(0xFF3E2723),

    background = Color(0xFFFFFBF7),
    onBackground = Color(0xFF1A1A1A),

    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF333333),

    error = Color(0xFFB00020),
    onError = Color.White
)
val SupabaseLightColors = lightColorScheme(
    primary = Color(0xFF3ECF8E),
    secondary = Color(0xFF00A97F),
    background = Color(0xFFF8F9FA),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFB2F0D1),
    onPrimaryContainer = Color(0xFF004D40),

    onSecondary = Color.White,
    secondaryContainer = Color(0xFFA7F3D0),
    onSecondaryContainer = Color(0xFF00382B),
    onBackground = Color(0xFF212529),

    surface = Color.White,
    onSurface = Color(0xFF212529),

    error = Color(0xFFB00020),
    onError = Color.White
)

val SupabaseDarkColors = darkColorScheme(
    primary = Color(0xFF20C997),
    secondary = Color(0xFF009578),
    background = Color(0xFF121212),
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF007B5E),
    onPrimaryContainer = Color.White,


    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF006B56),
    onSecondaryContainer = Color.White,

    onBackground = Color(0xFFE0E0E0),

    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFF0F0F0),

    error = Color(0xFFCF6679),
    onError = Color.Black
)
val PinkPurpleLightColors = lightColorScheme(
    primary = Color(0xFFE91E63), // Pink
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFC1E3),
    onPrimaryContainer = Color(0xFF52002C),

    secondary = Color(0xFFBA68C8), // Light Purple
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE1BEE7),
    onSecondaryContainer = Color(0xFF4A0072),

    background = Color(0xFFFFF0F6),
    onBackground = Color(0xFF1C1B1F),

    surface = Color.White,
    onSurface = Color(0xFF1C1B1F),

    error = Color(0xFFB00020),
    onError = Color.White
)

val PinkPurpleDarkColors = darkColorScheme(
    primary = Color(0xFFEC407A), // Bright pink for dark
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF880E4F),
    onPrimaryContainer = Color.White,

    secondary = Color(0xFFAB47BC), // Purple
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF6A1B9A),
    onSecondaryContainer = Color.White,

    background = Color(0xFF121212),
    onBackground = Color(0xFFE0E0E0),

    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFF0F0F0),

    error = Color(0xFFCF6679),
    onError = Color.Black
)

@Composable
fun FlareFrameTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    // Automatically picks dark or light based on system setting
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val dynamicColor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val colors = when {
        dynamicColor && darkTheme -> dynamicDarkColorScheme(LocalContext.current)
        dynamicColor && !darkTheme -> dynamicLightColorScheme(LocalContext.current)
        darkTheme -> SupabaseDarkColors
        else -> SupabaseLightColors
    }
    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
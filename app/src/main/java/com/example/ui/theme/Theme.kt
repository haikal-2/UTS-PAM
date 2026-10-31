package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = TerracottaAccent,
    onPrimary = Color.White,
    primaryContainer = CoffeeBrownDark,
    onPrimaryContainer = CoffeeContainer,
    secondary = CaramelGold,
    onSecondary = Color.Black,
    secondaryContainer = CoffeeBrownLight,
    onSecondaryContainer = Color.White,
    background = Color(0xFF191310),
    onBackground = Color(0xFFECE1DA),
    surface = Color(0xFF221A15),
    onSurface = Color(0xFFECE1DA),
    surfaceVariant = Color(0xFF2E241E),
    onSurfaceVariant = Color(0xFFD6C6BC),
    outline = Color(0xFF5D4D44)
)

private val LightColorScheme = lightColorScheme(
    primary = CoffeeBrown,
    onPrimary = Color.White,
    primaryContainer = CoffeeContainer,
    onPrimaryContainer = OnCoffeeContainer,
    secondary = TerracottaAccent,
    onSecondary = Color.White,
    secondaryContainer = TerracottaLight,
    onSecondaryContainer = TerracottaDark,
    tertiary = CaramelGold,
    onTertiary = Color.White,
    tertiaryContainer = CaramelLight,
    background = WarmCreamBackground,
    onBackground = EspressoTextPrimary,
    surface = WarmCardSurface,
    onSurface = EspressoTextPrimary,
    surfaceVariant = WarmSurfaceVariant,
    onSurfaceVariant = EspressoTextSecondary,
    outline = OutlineBorder
)

@Composable
fun KopiDanNyemilTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

// Keep alias for compatibility
@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    KopiDanNyemilTheme(darkTheme = darkTheme, content = content)
}

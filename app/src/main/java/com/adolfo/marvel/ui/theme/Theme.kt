package com.adolfo.marvel.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val darkColorPalette = darkColors(
    primary = marvelRedDark,
    primaryVariant = primaryDark,
    secondary = secondaryDark,
    surface = primarySurfaceDark,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onSurface = Color.White
)

private val lightColorPalette = lightColors(
    primary = marvelRed,
    primaryVariant = primaryLight,
    secondary = secondaryLight,
    onPrimary = Color.White,
    onSecondary = liquorice
)

@Composable
fun MarvelAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    val colors = if (darkTheme) {
        systemUiController.setSystemBarsColor(
            color = marvelRedDark
        )
        darkColorPalette
    } else {
        systemUiController.setSystemBarsColor(
            color = marvelRed
        )
        lightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

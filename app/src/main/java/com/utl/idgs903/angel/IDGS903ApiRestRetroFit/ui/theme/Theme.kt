package com.utl.idgs903.angel.IDGS903ApiRestRetroFit.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val SPRMLightColorScheme = lightColorScheme(
    primary = TealPrimary,
    secondary = TealMedium,
    background = LightBackground,
    surface = TealCard, // Usamos el TealCard para las tarjetas de contactos
    onPrimary = SurfaceColor, // Texto sobre el color primario (blanco)
    onSecondary = SurfaceColor,
    onBackground = TextMain, // Texto principal oscuro
    onSurface = TextMain, // Texto oscuro sobre las tarjetas
    error = ErrorColor
)

@Composable
fun IDGS903ApiRestRetroFitTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = SPRMLightColorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = SPRMLightColorScheme,
        typography = Typography,
        content = content
    )
}

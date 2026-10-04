package com.arnav.flowmeter.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.arnav.flowmeter.branding.colors.FlowMeterColors
import com.arnav.flowmeter.theme.dark.FlowMeterDarkColorScheme
import com.arnav.flowmeter.theme.light.FlowMeterLightColorScheme

@Composable
fun FlowMeterTheme(
    darkTheme: Boolean = true, // Default to dark mode matching design
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) FlowMeterDarkColorScheme else FlowMeterLightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = FlowMeterColors.BackgroundDark.toArgb()
            window.navigationBarColor = FlowMeterColors.BackgroundDark.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
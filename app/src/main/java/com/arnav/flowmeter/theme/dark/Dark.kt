package com.arnav.flowmeter.theme.dark

import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color
import com.arnav.flowmeter.branding.colors.FlowMeterColors

val FlowMeterDarkColorScheme = darkColorScheme(
    primary = FlowMeterColors.ElectricBlue,
    secondary = FlowMeterColors.CyanAccent,
    tertiary = FlowMeterColors.DeepBlue,
    background = FlowMeterColors.BackgroundDark,
    surface = FlowMeterColors.CardBackground,
    surfaceVariant = FlowMeterColors.CardSurface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = FlowMeterColors.TextPrimary,
    onSurface = FlowMeterColors.TextPrimary,
    onSurfaceVariant = FlowMeterColors.TextSecondary
)

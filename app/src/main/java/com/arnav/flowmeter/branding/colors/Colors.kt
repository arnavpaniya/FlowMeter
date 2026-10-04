package com.arnav.flowmeter.branding.colors

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object FlowMeterColors {
    // Backgrounds
    val BackgroundDark = Color(0xFF090D16)
    val BackgroundNavy = Color(0xFF0B101D)
    
    // Cards & Surfaces
    val CardBackground = Color(0xFF111927)
    val CardSurface = Color(0xFF141E30)
    val CardSurfaceGlass = Color(0xCC111927)
    val CardBorder = Color(0x3338BDF8)
    val CardBorderSubtle = Color(0x1A38BDF8)
    val CardBorderGlow = Color(0x6600A3FF)
    
    // Accents & Blues
    val ElectricBlue = Color(0xFF00A3FF)
    val CyanAccent = Color(0xFF38BDF8)
    val DeepBlue = Color(0xFF0284C7)
    val AquaGlow = Color(0xFF00E5FF)
    val DarkBlueAction = Color(0xFF162238)
    val DarkBlueIconBg = Color(0xFF131D2E)
    
    // Typography
    val TextPrimary = Color(0xFFFFFFFF)
    val TextSecondary = Color(0xFF8E9EB5)
    val TextMuted = Color(0xFF556987)
    val TextLightBlue = Color(0xFFBAE6FD)
    
    // Status Chips
    val StatusWaitingBg = Color(0x2638BDF8)
    val StatusWaitingText = Color(0xFF7DD3FC)
    val StatusWaitingBorder = Color(0x4038BDF8)
    
    // Gradients
    val ConnectButtonBrush = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFF0284C7),
            Color(0xFF00A3FF)
        )
    )
    
    val CardBorderBrush = Brush.linearGradient(
        colors = listOf(
            Color(0x6638BDF8),
            Color(0x1A00A3FF),
            Color(0x4038BDF8)
        )
    )
    
    val ActiveNavBrush = Brush.horizontalGradient(
        colors = listOf(
            Color(0x330284C7),
            Color(0x4D00A3FF)
        )
    )
    
    val WaveGradient1 = listOf(
        Color(0x0000A3FF),
        Color(0x2600A3FF),
        Color(0x4038BDF8),
        Color(0x0038BDF8)
    )
    
    val WaveGradient2 = listOf(
        Color(0x000284C7),
        Color(0x1F00E5FF),
        Color(0x3300A3FF),
        Color(0x000284C7)
    )
}

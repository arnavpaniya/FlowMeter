package com.arnav.flowmeter.components.waves

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

/**
 * Refined, elegant flowing water-wave ribbon for section separation.
 * Positioned between greeting/title and main cards with smooth, continuous motion,
 * restrained electric blue gradients, and zero interference with typography.
 */
@Composable
fun HeaderAtmosphericWaves(
    modifier: Modifier = Modifier,
    height: Dp = 32.dp,
    speedMillis: Int = 9000
) {
    val infiniteTransition = rememberInfiniteTransition(label = "header_waves_anim")

    // Primary wave cycle
    val phase1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * Math.PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = speedMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase1"
    )

    // Secondary wave cycle (counter-directional & gentle)
    val phase2 by infiniteTransition.animateFloat(
        initialValue = (2 * Math.PI).toFloat(),
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = (speedMillis * 0.85f).toInt(), easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase2"
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        val w = size.width
        val h = size.height

        // Layer 1: Soft translucent azure wave ribbon
        val path1 = Path()
        path1.moveTo(0f, h * 0.5f)
        var x = 0f
        val step = 4f
        while (x <= w) {
            val progress = x / w
            val envelope = sin(progress * Math.PI).toFloat() // Tapers gracefully at edges
            val y = (h * 0.5f) +
                    (sin((progress * 2.2 * Math.PI) + phase1).toFloat() * (h * 0.35f) * envelope)
            path1.lineTo(x, y)
            x += step
        }

        drawPath(
            path = path1,
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color.Transparent,
                    Color(0x1F00A3FF),
                    Color(0x6638BDF8),
                    Color(0x3300A3FF),
                    Color.Transparent
                )
            ),
            style = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
        )

        // Layer 2: Fine counter-harmonic electric blue crest line
        val path2 = Path()
        path2.moveTo(0f, h * 0.5f)
        x = 0f
        while (x <= w) {
            val progress = x / w
            val envelope = sin(progress * Math.PI).toFloat()
            val y = (h * 0.5f) +
                    (cos((progress * 3.4 * Math.PI) + phase2).toFloat() * (h * 0.25f) * envelope)
            path2.lineTo(x, y)
            x += step
        }

        drawPath(
            path = path2,
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color.Transparent,
                    Color(0x1500E5FF),
                    Color(0x4D00A3FF),
                    Color(0x8038BDF8),
                    Color.Transparent
                )
            ),
            style = Stroke(width = 1.2.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

/**
 * Backward compatibility alias for HeaderAtmosphericWaves.
 */
@Composable
fun EnhancedLuminousWaves(
    modifier: Modifier = Modifier,
    height: Dp = 32.dp,
    waveColors: List<Color> = listOf(
        Color(0xFF00A3FF),
        Color(0xFF38BDF8),
        Color(0xFF00E5FF)
    ),
    showParticles: Boolean = false,
    speedMillis: Int = 9000
) {
    HeaderAtmosphericWaves(
        modifier = modifier,
        height = height,
        speedMillis = speedMillis
    )
}

/**
 * Card Ambient Wave Overlay for gauges and status cards.
 */
@Composable
fun CardAmbientWaveOverlay(
    modifier: Modifier = Modifier,
    height: Dp = 32.dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "card_ambient_wave_anim")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * Math.PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 7500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "card_ambient_phase"
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        val w = size.width
        val h = size.height

        val wavePath = Path()
        wavePath.moveTo(0f, h * 0.5f)
        var x = 0f
        val step = 4f
        while (x <= w) {
            val progress = x / w
            val y = (h * 0.5f) + (sin((progress * 2.5 * Math.PI) + phase).toFloat() * (h * 0.28f))
            wavePath.lineTo(x, y)
            x += step
        }

        drawPath(
            path = wavePath,
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color(0x0000A3FF),
                    Color(0x4000A3FF),
                    Color(0x8038BDF8),
                    Color(0x4000A3FF),
                    Color(0x0000A3FF)
                )
            ),
            style = Stroke(width = 1.4.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0B0F19)
@Composable
fun HeaderAtmosphericWavesPreview() {
    HeaderAtmosphericWaves()
}

package com.arnav.flowmeter.components.waves

import androidx.compose.animation.core.FastOutSlowInEasing
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

/**
 * Premium Multi-Harmonic Atmospheric Wave Ribbons for Screen Headers.
 * Features volumetric translucent gradient fills, sharp neon crest strokes,
 * dual harmonic liquid turbulence, and breathing bioluminescent micro-sparks.
 *
 * Positioned in the upper-right quadrant starting gracefully from x = 18% to avoid
 * any overlap with typography, logo, or navigation icons.
 */
@Composable
fun HeaderAtmosphericWaves(
    modifier: Modifier = Modifier,
    height: Dp = 64.dp,
    speedMillis: Int = 8500
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

    // Secondary wave cycle (counter-directional & slightly faster)
    val phase2 by infiniteTransition.animateFloat(
        initialValue = (2 * Math.PI).toFloat(),
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = (speedMillis * 0.78f).toInt(), easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase2"
    )

    // Micro shimmer breathing pulse
    val shimmerPulse by infiniteTransition.animateFloat(
        initialValue = 0.45f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shimmer_pulse"
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        val w = size.width
        val h = size.height
        val startX = w * 0.18f // Safe margin away from left logo/titles

        // --- LAYER 1: Volumetric Translucent Liquid Ribbon Fill ---
        val fillPath = Path()
        fillPath.moveTo(startX, h)
        var px = startX
        val step = 5f
        while (px <= w) {
            val progress = (px - startX) / (w - startX)
            val baseAmp = h * 0.28f * sin(progress * Math.PI).toFloat() // Tapered envelope at endpoints
            val y = (h * 0.48f) +
                    (sin((progress * 2.4 * Math.PI) + phase1).toFloat() * baseAmp * 0.75f) +
                    (cos((progress * 4.2 * Math.PI) + phase2).toFloat() * baseAmp * 0.25f)
            fillPath.lineTo(px, y)
            px += step
        }
        fillPath.lineTo(w, h)
        fillPath.close()

        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0x2200A3FF),
                    Color(0x0C0284C7),
                    Color.Transparent
                ),
                startY = 0f,
                endY = h
            ),
            style = Fill
        )

        // --- LAYER 2: Primary Radiant Neon Wave Crest ---
        val crest1Path = Path()
        var isFirst = true
        px = startX
        while (px <= w) {
            val progress = (px - startX) / (w - startX)
            val baseAmp = h * 0.28f * sin(progress * Math.PI).toFloat()
            val y = (h * 0.48f) +
                    (sin((progress * 2.4 * Math.PI) + phase1).toFloat() * baseAmp * 0.75f) +
                    (cos((progress * 4.2 * Math.PI) + phase2).toFloat() * baseAmp * 0.25f)
            if (isFirst) {
                crest1Path.moveTo(px, y)
                isFirst = false
            } else {
                crest1Path.lineTo(px, y)
            }
            px += step
        }

        // Soft outer glow stroke for Layer 2
        drawPath(
            path = crest1Path,
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color(0x0000E5FF),
                    Color(0x2638BDF8),
                    Color(0x4D00A3FF),
                    Color(0x1A00E5FF)
                ),
                startX = startX,
                endX = w
            ),
            style = Stroke(width = 3.5.dp.toPx(), cap = StrokeCap.Round)
        )

        // Sharp core specular line for Layer 2
        drawPath(
            path = crest1Path,
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color(0x0038BDF8),
                    Color(0x5938BDF8),
                    Color(0xBF00F5D4),
                    Color(0x8038BDF8),
                    Color(0x1000A3FF)
                ),
                startX = startX,
                endX = w
            ),
            style = Stroke(width = 1.6.dp.toPx(), cap = StrokeCap.Round)
        )

        // --- LAYER 3: Counter-Harmonic Cyan Flow Line ---
        val crest2Path = Path()
        val startX2 = w * 0.32f
        isFirst = true
        px = startX2
        while (px <= w) {
            val progress = (px - startX2) / (w - startX2)
            val baseAmp = h * 0.22f * sin(progress * Math.PI).toFloat()
            val y = (h * 0.60f) +
                    (cos((progress * 2.8 * Math.PI) + phase2).toFloat() * baseAmp * 0.8f) +
                    (sin((progress * 5.0 * Math.PI) + phase1).toFloat() * baseAmp * 0.2f)
            if (isFirst) {
                crest2Path.moveTo(px, y)
                isFirst = false
            } else {
                crest2Path.lineTo(px, y)
            }
            px += step
        }

        drawPath(
            path = crest2Path,
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color(0x0000A3FF),
                    Color(0x4000A3FF),
                    Color(0x8038BDF8),
                    Color(0x1500A3FF)
                ),
                startX = startX2,
                endX = w
            ),
            style = Stroke(width = 1.2.dp.toPx(), cap = StrokeCap.Round)
        )

        // --- LAYER 4: Bioluminescent Micro-Spark Beads along the waves ---
        val particleRatios = listOf(0.42f, 0.64f, 0.82f)
        particleRatios.forEachIndexed { index, ratio ->
            val particleX = startX + ratio * (w - startX)
            val progress = ratio
            val baseAmp = h * 0.28f * sin(progress * Math.PI).toFloat()
            val particleY = (h * 0.48f) +
                    (sin((progress * 2.4 * Math.PI) + phase1).toFloat() * baseAmp * 0.75f) +
                    (cos((progress * 4.2 * Math.PI) + phase2).toFloat() * baseAmp * 0.25f)

            val dynamicAlpha = ((shimmerPulse * (0.6f + 0.4f * sin(phase1 + index)))).coerceIn(0.2f, 0.95f)

            // Spark halo
            drawCircle(
                color = Color(0x3338BDF8).copy(alpha = dynamicAlpha * 0.4f),
                radius = 4.dp.toPx(),
                center = Offset(particleX, particleY)
            )
            // Spark core
            drawCircle(
                color = Color(0xFFE0F7FF).copy(alpha = dynamicAlpha),
                radius = 1.2.dp.toPx(),
                center = Offset(particleX, particleY)
            )
        }
    }
}

/**
 * Backward compatibility alias for HeaderAtmosphericWaves.
 */
@Composable
fun EnhancedLuminousWaves(
    modifier: Modifier = Modifier,
    height: Dp = 64.dp,
    waveColors: List<Color> = listOf(
        Color(0xFF00A3FF),
        Color(0xFF38BDF8),
        Color(0xFF00E5FF)
    ),
    showParticles: Boolean = false,
    speedMillis: Int = 8500
) {
    HeaderAtmosphericWaves(
        modifier = modifier,
        height = height,
        speedMillis = speedMillis
    )
}

/**
 * Card Ambient Wave Overlay for gauges and status cards.
 * Provides rich hydrodynamic undulation with soft subsurface luminescence.
 */
@Composable
fun CardAmbientWaveOverlay(
    modifier: Modifier = Modifier,
    height: Dp = 42.dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "card_ambient_wave_anim")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * Math.PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 6500, easing = LinearEasing),
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

        // Fill path under wave
        val fillPath = Path()
        fillPath.moveTo(0f, h)
        var x = 0f
        val step = 5f
        while (x <= w) {
            val progress = x / w
            val y = (h * 0.45f) +
                    (sin((progress * 2.2 * Math.PI) + phase).toFloat() * (h * 0.22f)) +
                    (cos((progress * 4.0 * Math.PI) - phase * 0.8f).toFloat() * (h * 0.10f))
            fillPath.lineTo(x, y)
            x += step
        }
        fillPath.lineTo(w, h)
        fillPath.close()

        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0x2E00A3FF),
                    Color(0x100284C7),
                    Color.Transparent
                ),
                startY = 0f,
                endY = h
            )
        )

        // Wave 1 crest line
        val wavePath = Path()
        x = 0f
        var isFirst = true
        while (x <= w) {
            val progress = x / w
            val y = (h * 0.45f) +
                    (sin((progress * 2.2 * Math.PI) + phase).toFloat() * (h * 0.22f)) +
                    (cos((progress * 4.0 * Math.PI) - phase * 0.8f).toFloat() * (h * 0.10f))
            if (isFirst) {
                wavePath.moveTo(x, y)
                isFirst = false
            } else {
                wavePath.lineTo(x, y)
            }
            x += step
        }

        drawPath(
            path = wavePath,
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color(0x0000A3FF),
                    Color(0x6600A3FF),
                    Color(0xCC38BDF8),
                    Color(0x9900F5D4),
                    Color(0x0000A3FF)
                )
            ),
            style = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
        )

        // Wave 2 counter subtle crest line
        val wave2Path = Path()
        x = 0f
        isFirst = true
        while (x <= w) {
            val progress = x / w
            val y = (h * 0.58f) +
                    (cos((progress * 2.8 * Math.PI) - phase).toFloat() * (h * 0.16f))
            if (isFirst) {
                wave2Path.moveTo(x, y)
                isFirst = false
            } else {
                wave2Path.lineTo(x, y)
            }
            x += step
        }

        drawPath(
            path = wave2Path,
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color(0x000284C7),
                    Color(0x3300A3FF),
                    Color(0x6638BDF8),
                    Color(0x000284C7)
                )
            ),
            style = Stroke(width = 1.2.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0B0F19)
@Composable
fun HeaderAtmosphericWavesPreview() {
    HeaderAtmosphericWaves()
}

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
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

/**
 * High-fidelity 3-Layered Atmospheric Water Wave Ribbon with Volumetric Depth.
 *
 * Visual hierarchy:
 * 1. Deep Background Water Layer (darker oceanic depth, slower undulating flow)
 * 2. Translucent Middle Water Layer (cerulean body, moderate speed, light refraction)
 * 3. Foreground Water Layer (brighter electric-blue volume, faster motion)
 * 4. Specular Reflected Light Crest (fine luminous highlight with subtle ambient glow)
 */
@Composable
fun HeaderAtmosphericWaves(
    modifier: Modifier = Modifier,
    height: Dp = 38.dp,
    speedMillis: Int = 8500
) {
    val infiniteTransition = rememberInfiniteTransition(label = "water_waves_anim")

    // Layer 1: Background Wave (Slowest, deepest)
    val phaseBack by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * Math.PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = (speedMillis * 1.35f).toInt(), easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase_back"
    )

    // Layer 2: Middle Translucent Wave (Moderate speed, counter-directional)
    val phaseMid by infiniteTransition.animateFloat(
        initialValue = (2 * Math.PI).toFloat(),
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = speedMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase_mid"
    )

    // Layer 3: Foreground Wave (Slightly faster, dynamic)
    val phaseFront by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * Math.PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = (speedMillis * 0.72f).toInt(), easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase_front"
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        val w = size.width
        val h = size.height
        val step = 4f

        // =========================================================================
        // LAYER 1: DEEP BACKGROUND WATER LAYER (Dark depth, slow rolling volume)
        // =========================================================================
        val backFillPath = Path()
        val backCrestPath = Path()
        backFillPath.moveTo(0f, h)

        var x = 0f
        var isFirst = true
        while (x <= w) {
            val progress = x / w
            val envelope = sin(progress * Math.PI).toFloat() // Tapers edges gracefully
            val baseAmp = h * 0.32f * envelope
            val y = (h * 0.58f) +
                    (sin((progress * 1.8 * Math.PI) + phaseBack).toFloat() * baseAmp * 0.70f) +
                    (cos((progress * 3.2 * Math.PI) - phaseBack * 0.6).toFloat() * baseAmp * 0.30f)

            backFillPath.lineTo(x, y)
            if (isFirst) {
                backCrestPath.moveTo(x, y)
                isFirst = false
            } else {
                backCrestPath.lineTo(x, y)
            }
            x += step
        }
        backFillPath.lineTo(w, h)
        backFillPath.close()

        // Background water body fill
        drawPath(
            path = backFillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0x2E0A2855),
                    Color(0x1A061A3C),
                    Color.Transparent
                ),
                startY = 0f,
                endY = h
            ),
            style = Fill
        )

        // Subtle background rim stroke
        drawPath(
            path = backCrestPath,
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color.Transparent,
                    Color(0x180066CC),
                    Color(0x400284C7),
                    Color(0x180066CC),
                    Color.Transparent
                )
            ),
            style = Stroke(width = 1.0.dp.toPx(), cap = StrokeCap.Round)
        )

        // =========================================================================
        // LAYER 2: MIDDLE TRANSLUCENT WATER LAYER (Light refraction & volume)
        // =========================================================================
        val midFillPath = Path()
        val midCrestPath = Path()
        midFillPath.moveTo(0f, h)

        x = 0f
        isFirst = true
        while (x <= w) {
            val progress = x / w
            val envelope = sin(progress * Math.PI).toFloat()
            val baseAmp = h * 0.30f * envelope
            val y = (h * 0.50f) +
                    (sin((progress * 2.4 * Math.PI) + phaseMid).toFloat() * baseAmp * 0.75f) +
                    (sin((progress * 4.0 * Math.PI) + phaseMid * 1.2).toFloat() * baseAmp * 0.25f)

            midFillPath.lineTo(x, y)
            if (isFirst) {
                midCrestPath.moveTo(x, y)
                isFirst = false
            } else {
                midCrestPath.lineTo(x, y)
            }
            x += step
        }
        midFillPath.lineTo(w, h)
        midFillPath.close()

        // Middle translucent water body
        drawPath(
            path = midFillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0x3B0084D6),
                    Color(0x18024888),
                    Color.Transparent
                ),
                startY = 0f,
                endY = h
            ),
            style = Fill
        )

        // Middle crest highlight
        drawPath(
            path = midCrestPath,
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color.Transparent,
                    Color(0x2238BDF8),
                    Color(0x6638BDF8),
                    Color(0x3300A3FF),
                    Color.Transparent
                )
            ),
            style = Stroke(width = 1.4.dp.toPx(), cap = StrokeCap.Round)
        )

        // =========================================================================
        // LAYER 3: FOREGROUND WATER LAYER & SPECULAR LIGHT CREST
        // =========================================================================
        val frontFillPath = Path()
        val frontCrestPath = Path()
        frontFillPath.moveTo(0f, h)

        x = 0f
        isFirst = true
        while (x <= w) {
            val progress = x / w
            val envelope = sin(progress * Math.PI).toFloat()
            val baseAmp = h * 0.28f * envelope
            val y = (h * 0.44f) +
                    (sin((progress * 2.8 * Math.PI) + phaseFront).toFloat() * baseAmp * 0.80f) +
                    (cos((progress * 4.8 * Math.PI) - phaseFront * 0.8).toFloat() * baseAmp * 0.20f)

            frontFillPath.lineTo(x, y)
            if (isFirst) {
                frontCrestPath.moveTo(x, y)
                isFirst = false
            } else {
                frontCrestPath.lineTo(x, y)
            }
            x += step
        }
        frontFillPath.lineTo(w, h)
        frontFillPath.close()

        // Foreground luminous electric-blue water body
        drawPath(
            path = frontFillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0x4D00A3FF),
                    Color(0x200284C7),
                    Color.Transparent
                ),
                startY = 0f,
                endY = h
            ),
            style = Fill
        )

        // Soft ambient glow on front crest
        drawPath(
            path = frontCrestPath,
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color.Transparent,
                    Color(0x1F38BDF8),
                    Color(0x4D38BDF8),
                    Color(0x1F38BDF8),
                    Color.Transparent
                )
            ),
            style = Stroke(width = 3.0.dp.toPx(), cap = StrokeCap.Round)
        )

        // Razor-sharp specular light crest line
        drawPath(
            path = frontCrestPath,
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color.Transparent,
                    Color(0x33E0F7FF),
                    Color(0xCCE0F7FF),
                    Color(0x9938BDF8),
                    Color.Transparent
                )
            ),
            style = Stroke(width = 1.3.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

/**
 * Backward compatibility alias for HeaderAtmosphericWaves.
 */
@Composable
fun EnhancedLuminousWaves(
    modifier: Modifier = Modifier,
    height: Dp = 38.dp,
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
 * Card Ambient Wave Overlay with translucent liquid depth and specular surface.
 */
@Composable
fun CardAmbientWaveOverlay(
    modifier: Modifier = Modifier,
    height: Dp = 34.dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "card_water_anim")

    val phase1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * Math.PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 7500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "card_phase_1"
    )

    val phase2 by infiniteTransition.animateFloat(
        initialValue = (2 * Math.PI).toFloat(),
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "card_phase_2"
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        val w = size.width
        val h = size.height
        val step = 4f

        // Middle Layer Fill
        val midFill = Path()
        midFill.moveTo(0f, h)
        var x = 0f
        while (x <= w) {
            val progress = x / w
            val y = (h * 0.54f) + (sin((progress * 2.0 * Math.PI) + phase1).toFloat() * (h * 0.22f))
            midFill.lineTo(x, y)
            x += step
        }
        midFill.lineTo(w, h)
        midFill.close()

        drawPath(
            path = midFill,
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0x280284C7),
                    Color(0x0C024888),
                    Color.Transparent
                ),
                startY = 0f,
                endY = h
            ),
            style = Fill
        )

        // Front Layer Fill & Crest
        val frontFill = Path()
        val frontCrest = Path()
        frontFill.moveTo(0f, h)

        x = 0f
        var isFirst = true
        while (x <= w) {
            val progress = x / w
            val y = (h * 0.44f) +
                    (sin((progress * 2.6 * Math.PI) + phase2).toFloat() * (h * 0.26f)) +
                    (cos((progress * 4.4 * Math.PI) - phase1).toFloat() * (h * 0.10f))

            frontFill.lineTo(x, y)
            if (isFirst) {
                frontCrest.moveTo(x, y)
                isFirst = false
            } else {
                frontCrest.lineTo(x, y)
            }
            x += step
        }
        frontFill.lineTo(w, h)
        frontFill.close()

        drawPath(
            path = frontFill,
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0x3800A3FF),
                    Color(0x120066CC),
                    Color.Transparent
                ),
                startY = 0f,
                endY = h
            ),
            style = Fill
        )

        // Specular Crest Stroke
        drawPath(
            path = frontCrest,
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color(0x0000A3FF),
                    Color(0x6638BDF8),
                    Color(0xCCE0F7FF),
                    Color(0x6638BDF8),
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

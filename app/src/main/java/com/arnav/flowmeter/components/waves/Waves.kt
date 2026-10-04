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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

private data class WaveParticle(
    val initialXRatio: Float,
    val yOffsetRatio: Float,
    val radius: Float,
    val speedFactor: Float,
    val alphaBase: Float
)

/**
 * Premium multi-layered luminous water wave ribbons with floating micro-particles
 * and smooth horizontal fluid motion.
 */
@Composable
fun EnhancedLuminousWaves(
    modifier: Modifier = Modifier,
    height: Dp = 80.dp,
    waveColors: List<Color> = listOf(
        Color(0xFF00A3FF),
        Color(0xFF38BDF8),
        Color(0xFF00E5FF)
    ),
    showParticles: Boolean = true,
    speedMillis: Int = 7000
) {
    val infiniteTransition = rememberInfiniteTransition(label = "enhanced_waves_anim")
    
    // Smooth infinite time phase for continuous flowing wave
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * Math.PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = speedMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave_phase"
    )

    // Particle shimmer pulsation
    val shimmer by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2400, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "particle_shimmer"
    )

    // Deterministic particles for calm fluid aesthetics
    val particles = remember {
        val rand = Random(42)
        List(14) {
            WaveParticle(
                initialXRatio = rand.nextFloat(),
                yOffsetRatio = rand.nextFloat() * 0.4f - 0.2f,
                radius = 1.5f + rand.nextFloat() * 2.5f,
                speedFactor = 0.8f + rand.nextFloat() * 0.5f,
                alphaBase = 0.35f + rand.nextFloat() * 0.45f
            )
        }
    }

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        val w = size.width
        val h = size.height
        val centerY = h * 0.52f

        // --- LAYER 1: Deep ambient background water glow ---
        val ambientPath = Path()
        ambientPath.moveTo(0f, centerY + 6.dp.toPx())
        var x = 0f
        val step = 8f
        while (x <= w) {
            val progress = x / w
            val y = centerY + 6.dp.toPx() + (sin((progress * 2.2 * Math.PI) + phase * 0.7).toFloat() * (h * 0.24f))
            ambientPath.lineTo(x, y)
            x += step
        }
        drawPath(
            path = ambientPath,
            brush = Brush.horizontalGradient(
                colors = listOf(
                    waveColors[0].copy(alpha = 0.05f),
                    waveColors[0].copy(alpha = 0.22f),
                    waveColors[1].copy(alpha = 0.28f),
                    waveColors[0].copy(alpha = 0.08f)
                )
            ),
            style = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round)
        )

        // --- LAYER 2: Primary flowing crest ribbon with luminous highlight ---
        val ribbonPath1 = Path()
        ribbonPath1.moveTo(0f, centerY)
        x = 0f
        while (x <= w) {
            val progress = x / w
            val y = centerY + (sin((progress * 2.8 * Math.PI) + phase).toFloat() * (h * 0.26f))
            ribbonPath1.lineTo(x, y)
            x += step
        }
        drawPath(
            path = ribbonPath1,
            brush = Brush.horizontalGradient(
                colors = listOf(
                    waveColors[0].copy(alpha = 0.1f),
                    waveColors[1].copy(alpha = 0.65f),
                    waveColors[2].copy(alpha = 0.85f),
                    waveColors[0].copy(alpha = 0.2f)
                )
            ),
            style = Stroke(width = 2.4.dp.toPx(), cap = StrokeCap.Round)
        )

        // --- LAYER 3: Secondary counter-flowing translucent wave ---
        val ribbonPath2 = Path()
        ribbonPath2.moveTo(0f, centerY - 4.dp.toPx())
        x = 0f
        while (x <= w) {
            val progress = x / w
            val y = centerY - 4.dp.toPx() + (cos((progress * 2.0 * Math.PI) - phase * 0.85).toFloat() * (h * 0.20f))
            ribbonPath2.lineTo(x, y)
            x += step
        }
        drawPath(
            path = ribbonPath2,
            brush = Brush.horizontalGradient(
                colors = listOf(
                    waveColors[1].copy(alpha = 0.05f),
                    waveColors[0].copy(alpha = 0.45f),
                    waveColors[1].copy(alpha = 0.55f),
                    waveColors[2].copy(alpha = 0.1f)
                )
            ),
            style = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
        )

        // --- LAYER 4: Floating Micro-Bubbles / Luminous Particles ---
        if (showParticles) {
            particles.forEach { p ->
                val particlePhase = (phase * p.speedFactor) % (2 * Math.PI.toFloat())
                val particleX = ((p.initialXRatio * w) + (particlePhase / (2 * Math.PI.toFloat()) * w)) % w
                val waveY = centerY + (sin(((particleX / w) * 2.8 * Math.PI) + phase).toFloat() * (h * 0.26f))
                val particleY = waveY + (p.yOffsetRatio * h)

                val alpha = (p.alphaBase * shimmer).coerceIn(0.1f, 0.95f)

                // Soft outer glow for bubble
                drawCircle(
                    color = waveColors[2].copy(alpha = alpha * 0.35f),
                    radius = p.radius.dp.toPx() * 1.8f,
                    center = Offset(particleX, particleY)
                )
                // Core particle
                drawCircle(
                    color = Color.White.copy(alpha = alpha),
                    radius = p.radius.dp.toPx(),
                    center = Offset(particleX, particleY)
                )
            }
        }
    }
}

/**
 * Wave overlay with soft gradient fill for the lower section of cards.
 */
@Composable
fun CardAmbientWaveOverlay(
    modifier: Modifier = Modifier,
    height: Dp = 44.dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "card_wave_anim")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * Math.PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 6500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "card_wave_phase"
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        val w = size.width
        val h = size.height

        // Upper crest line
        val wavePath = Path()
        wavePath.moveTo(0f, h * 0.45f)
        var x = 0f
        val step = 8f
        while (x <= w) {
            val progress = x / w
            val y = (h * 0.45f) + (sin((progress * 2.4 * Math.PI) + phase).toFloat() * (h * 0.25f))
            wavePath.lineTo(x, y)
            x += step
        }

        // Stroke line
        drawPath(
            path = wavePath,
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color(0x1A00A3FF),
                    Color(0x8038BDF8),
                    Color(0x9900E5FF),
                    Color(0x2600A3FF)
                )
            ),
            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
        )

        // Lower secondary wave
        val wavePath2 = Path()
        wavePath2.moveTo(0f, h * 0.65f)
        x = 0f
        while (x <= w) {
            val progress = x / w
            val y = (h * 0.65f) + (cos((progress * 2.0 * Math.PI) - phase).toFloat() * (h * 0.20f))
            wavePath2.lineTo(x, y)
            x += step
        }
        drawPath(
            path = wavePath2,
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color(0x0D00A3FF),
                    Color(0x4D00A3FF),
                    Color(0x6638BDF8),
                    Color(0x1A00A3FF)
                )
            ),
            style = Stroke(width = 1.4.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

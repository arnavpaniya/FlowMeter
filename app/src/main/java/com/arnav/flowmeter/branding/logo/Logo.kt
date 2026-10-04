package com.arnav.flowmeter.branding.logo

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Renders the FlowMeter water-drop wave logo as a high-fidelity vector graphic.
 */
@Composable
fun FlowMeterLogo(
    modifier: Modifier = Modifier,
    size: Dp = 32.dp,
    showContainer: Boolean = false
) {
    Box(
        modifier = modifier.size(size)
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val w = this.size.width
            val h = this.size.height

            if (showContainer) {
                // Background rounded squircle if desired
                drawRoundRect(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF0077E6), Color(0xFF00A3FF))
                    ),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(w * 0.28f, h * 0.28f)
                )
            }

            val scale = if (showContainer) 0.65f else 0.95f
            val offsetX = (w * (1f - scale)) / 2f
            val offsetY = (h * (1f - scale)) / 2f

            val pw = w * scale
            val ph = h * scale

            // Upper droplet portion with S-wave bottom
            val upperPath = Path().apply {
                moveTo(offsetX + pw * 0.50f, offsetY + ph * 0.04f)
                cubicTo(
                    offsetX + pw * 0.68f, offsetY + ph * 0.28f,
                    offsetX + pw * 0.94f, offsetY + ph * 0.55f,
                    offsetX + pw * 0.78f, offsetY + ph * 0.70f
                )
                cubicTo(
                    offsetX + pw * 0.65f, offsetY + ph * 0.62f,
                    offsetX + pw * 0.35f, offsetY + ph * 0.52f,
                    offsetX + pw * 0.08f, offsetY + ph * 0.62f
                )
                cubicTo(
                    offsetX + pw * 0.08f, offsetY + ph * 0.50f,
                    offsetX + pw * 0.32f, offsetY + ph * 0.28f,
                    offsetX + pw * 0.50f, offsetY + ph * 0.04f
                )
                close()
            }

            // Lower wave base portion
            val lowerPath = Path().apply {
                moveTo(offsetX + pw * 0.06f, offsetY + ph * 0.69f)
                cubicTo(
                    offsetX + pw * 0.32f, offsetY + ph * 0.60f,
                    offsetX + pw * 0.64f, offsetY + ph * 0.68f,
                    offsetX + pw * 0.92f, offsetY + ph * 0.76f
                )
                cubicTo(
                    offsetX + pw * 0.88f, offsetY + ph * 0.94f,
                    offsetX + pw * 0.50f, offsetY + ph * 1.00f,
                    offsetX + pw * 0.18f, offsetY + ph * 0.94f
                )
                cubicTo(
                    offsetX + pw * 0.04f, offsetY + ph * 0.86f,
                    offsetX + pw * 0.04f, offsetY + ph * 0.76f,
                    offsetX + pw * 0.06f, offsetY + ph * 0.69f
                )
                close()
            }

            val dropletBrush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFFFFFFF),
                    Color(0xFFD9EFFF),
                    Color(0xFFA1D8FF)
                )
            )

            drawPath(path = upperPath, brush = dropletBrush)
            drawPath(path = lowerPath, brush = dropletBrush)
        }
    }
}

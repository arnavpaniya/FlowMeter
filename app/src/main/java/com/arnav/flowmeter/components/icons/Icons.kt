package com.arnav.flowmeter.components.icons

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arnav.flowmeter.branding.colors.FlowMeterColors

@Composable
fun WaterDropIcon(
    modifier: Modifier = Modifier,
    size: Dp = 20.dp,
    tint: Color = FlowMeterColors.CyanAccent
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val path = Path().apply {
            moveTo(w * 0.5f, h * 0.12f)
            cubicTo(w * 0.75f, h * 0.45f, w * 0.88f, h * 0.65f, w * 0.88f, h * 0.78f)
            cubicTo(w * 0.88f, h * 0.95f, w * 0.72f, h * 1.0f, w * 0.5f, h * 1.0f)
            cubicTo(w * 0.28f, h * 1.0f, w * 0.12f, h * 0.95f, w * 0.12f, h * 0.78f)
            cubicTo(w * 0.12f, h * 0.65f, w * 0.25f, h * 0.45f, w * 0.5f, h * 0.12f)
            close()
        }
        drawPath(
            path = path,
            color = tint,
            style = Stroke(width = w * 0.09f, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
    }
}

@Composable
fun FlowWavesIcon(
    modifier: Modifier = Modifier,
    size: Dp = 20.dp,
    tint: Color = FlowMeterColors.CyanAccent
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = w * 0.09f

        val dropPath = Path().apply {
            moveTo(w * 0.35f, h * 0.15f)
            cubicTo(w * 0.45f, h * 0.28f, w * 0.52f, h * 0.38f, w * 0.52f, h * 0.45f)
            cubicTo(w * 0.52f, h * 0.55f, w * 0.44f, h * 0.58f, w * 0.35f, h * 0.58f)
            cubicTo(w * 0.26f, h * 0.58f, w * 0.18f, h * 0.55f, w * 0.18f, h * 0.45f)
            cubicTo(w * 0.18f, h * 0.38f, w * 0.25f, h * 0.28f, w * 0.35f, h * 0.15f)
            close()
        }
        drawPath(path = dropPath, color = tint, style = Stroke(width = strokeWidth))

        val wave1 = Path().apply {
            moveTo(w * 0.1f, h * 0.72f)
            cubicTo(w * 0.35f, h * 0.62f, w * 0.65f, h * 0.82f, w * 0.9f, h * 0.72f)
        }
        drawPath(path = wave1, color = tint, style = Stroke(width = strokeWidth, cap = StrokeCap.Round))

        val wave2 = Path().apply {
            moveTo(w * 0.1f, h * 0.88f)
            cubicTo(w * 0.35f, h * 0.78f, w * 0.65f, h * 0.98f, w * 0.9f, h * 0.88f)
        }
        drawPath(path = wave2, color = tint, style = Stroke(width = strokeWidth, cap = StrokeCap.Round))
    }
}

@Composable
fun ClockIcon(
    modifier: Modifier = Modifier,
    size: Dp = 20.dp,
    tint: Color = FlowMeterColors.CyanAccent
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = w * 0.09f

        drawCircle(
            color = tint,
            radius = (w / 2f) - (strokeWidth / 2f),
            center = Offset(w / 2f, h / 2f),
            style = Stroke(width = strokeWidth)
        )

        val center = Offset(w / 2f, h / 2f)
        drawLine(
            color = tint,
            start = center,
            end = Offset(w / 2f, h * 0.28f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = tint,
            start = center,
            end = Offset(w * 0.72f, h / 2f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
    }
}

@Composable
fun DeviceSensorIcon(
    modifier: Modifier = Modifier,
    size: Dp = 20.dp,
    tint: Color = FlowMeterColors.CyanAccent
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = w * 0.09f

        drawRoundRect(
            color = tint,
            topLeft = Offset(w * 0.15f, h * 0.25f),
            size = Size(w * 0.7f, h * 0.55f),
            cornerRadius = CornerRadius(w * 0.12f, h * 0.12f),
            style = Stroke(width = strokeWidth)
        )

        drawLine(
            color = tint,
            start = Offset(w * 0.3f, h * 0.45f),
            end = Offset(w * 0.7f, h * 0.45f),
            strokeWidth = strokeWidth * 0.8f,
            cap = StrokeCap.Round
        )

        drawCircle(color = tint, radius = strokeWidth * 0.6f, center = Offset(w * 0.35f, h * 0.63f))
        drawCircle(color = tint, radius = strokeWidth * 0.6f, center = Offset(w * 0.65f, h * 0.63f))
    }
}

@Composable
fun ConnectLinkIcon(
    modifier: Modifier = Modifier,
    size: Dp = 18.dp,
    tint: Color = Color.White
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = w * 0.11f

        val path1 = Path().apply {
            moveTo(w * 0.45f, h * 0.25f)
            lineTo(w * 0.6f, h * 0.25f)
            cubicTo(w * 0.85f, h * 0.25f, w * 0.85f, h * 0.75f, w * 0.6f, h * 0.75f)
            lineTo(w * 0.45f, h * 0.75f)
        }
        drawPath(path = path1, color = tint, style = Stroke(width = strokeWidth, cap = StrokeCap.Round))

        val path2 = Path().apply {
            moveTo(w * 0.55f, h * 0.75f)
            lineTo(w * 0.4f, h * 0.75f)
            cubicTo(w * 0.15f, h * 0.75f, w * 0.15f, h * 0.25f, w * 0.4f, h * 0.25f)
            lineTo(w * 0.55f, h * 0.25f)
        }
        drawPath(path = path2, color = tint, style = Stroke(width = strokeWidth, cap = StrokeCap.Round))
    }
}

@Composable
fun BellIcon(
    modifier: Modifier = Modifier,
    size: Dp = 18.dp,
    tint: Color = FlowMeterColors.TextSecondary,
    hasNotificationDot: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = w * 0.09f

        val bellPath = Path().apply {
            moveTo(w * 0.5f, h * 0.15f)
            cubicTo(w * 0.3f, h * 0.2f, w * 0.25f, h * 0.5f, w * 0.2f, h * 0.7f)
            lineTo(w * 0.8f, h * 0.7f)
            cubicTo(w * 0.75f, h * 0.5f, w * 0.7f, h * 0.2f, w * 0.5f, h * 0.15f)
            close()
        }
        drawPath(path = bellPath, color = tint, style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round))
        drawCircle(color = tint, radius = strokeWidth * 0.7f, center = Offset(w * 0.5f, h * 0.85f))

        if (hasNotificationDot) {
            drawCircle(
                color = FlowMeterColors.ElectricBlue,
                radius = w * 0.12f,
                center = Offset(w * 0.78f, h * 0.18f)
            )
        }
    }
}

@Composable
fun SettingsGearIcon(
    modifier: Modifier = Modifier,
    size: Dp = 18.dp,
    tint: Color = FlowMeterColors.TextSecondary
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = w * 0.09f

        drawCircle(
            color = tint,
            radius = w * 0.26f,
            center = Offset(w / 2f, h / 2f),
            style = Stroke(width = strokeWidth)
        )
        for (i in 0 until 6) {
            val angle = (i * 60) * (Math.PI / 180.0)
            val cos = Math.cos(angle).toFloat()
            val sin = Math.sin(angle).toFloat()
            drawLine(
                color = tint,
                start = Offset(w / 2f + cos * w * 0.28f, h / 2f + sin * h * 0.28f),
                end = Offset(w / 2f + cos * w * 0.44f, h / 2f + sin * h * 0.44f),
                strokeWidth = strokeWidth * 1.3f,
                cap = StrokeCap.Round
            )
        }
    }
}

@Composable
fun MoreVertIcon(
    modifier: Modifier = Modifier,
    size: Dp = 18.dp,
    tint: Color = FlowMeterColors.TextSecondary
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val r = w * 0.08f
        drawCircle(color = tint, radius = r, center = Offset(w / 2f, h * 0.25f))
        drawCircle(color = tint, radius = r, center = Offset(w / 2f, h * 0.50f))
        drawCircle(color = tint, radius = r, center = Offset(w / 2f, h * 0.75f))
    }
}

@Composable
fun HistoryIcon(
    modifier: Modifier = Modifier,
    size: Dp = 20.dp,
    tint: Color = FlowMeterColors.CyanAccent
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = w * 0.09f

        drawArc(
            color = tint,
            startAngle = 40f,
            sweepAngle = 290f,
            useCenter = false,
            topLeft = Offset(w * 0.12f, h * 0.12f),
            size = Size(w * 0.76f, h * 0.76f),
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )
        drawLine(
            color = tint,
            start = Offset(w / 2f, h / 2f),
            end = Offset(w / 2f, h * 0.32f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = tint,
            start = Offset(w / 2f, h / 2f),
            end = Offset(w * 0.65f, h / 2f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
    }
}

@Composable
fun HomeNavIcon(
    modifier: Modifier = Modifier,
    size: Dp = 22.dp,
    tint: Color = Color.White
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = w * 0.09f

        val path = Path().apply {
            moveTo(w * 0.15f, h * 0.45f)
            lineTo(w * 0.5f, h * 0.15f)
            lineTo(w * 0.85f, h * 0.45f)
            lineTo(w * 0.85f, h * 0.88f)
            lineTo(w * 0.15f, h * 0.88f)
            close()
        }
        drawPath(path = path, color = tint, style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round))
    }
}

@Composable
fun AnalyticsNavIcon(
    modifier: Modifier = Modifier,
    size: Dp = 22.dp,
    tint: Color = FlowMeterColors.TextSecondary
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = w * 0.10f

        drawLine(
            color = tint,
            start = Offset(w * 0.25f, h * 0.85f),
            end = Offset(w * 0.25f, h * 0.50f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = tint,
            start = Offset(w * 0.50f, h * 0.85f),
            end = Offset(w * 0.50f, h * 0.25f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = tint,
            start = Offset(w * 0.75f, h * 0.85f),
            end = Offset(w * 0.75f, h * 0.40f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
    }
}

@Composable
fun CalendarIcon(
    modifier: Modifier = Modifier,
    size: Dp = 16.dp,
    tint: Color = FlowMeterColors.TextSecondary
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = w * 0.09f

        // Calendar body
        drawRoundRect(
            color = tint,
            topLeft = Offset(w * 0.12f, h * 0.22f),
            size = Size(w * 0.76f, h * 0.68f),
            cornerRadius = CornerRadius(w * 0.12f, h * 0.12f),
            style = Stroke(width = strokeWidth)
        )

        // Top line
        drawLine(
            color = tint,
            start = Offset(w * 0.12f, h * 0.42f),
            end = Offset(w * 0.88f, h * 0.42f),
            strokeWidth = strokeWidth
        )

        // Binder pins
        drawLine(
            color = tint,
            start = Offset(w * 0.32f, h * 0.10f),
            end = Offset(w * 0.32f, h * 0.24f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = tint,
            start = Offset(w * 0.68f, h * 0.10f),
            end = Offset(w * 0.68f, h * 0.24f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
    }
}

@Composable
fun ChevronRightIcon(
    modifier: Modifier = Modifier,
    size: Dp = 16.dp,
    tint: Color = FlowMeterColors.TextSecondary
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = w * 0.12f

        val path = Path().apply {
            moveTo(w * 0.35f, h * 0.20f)
            lineTo(w * 0.65f, h * 0.50f)
            lineTo(w * 0.35f, h * 0.80f)
        }
        drawPath(path = path, color = tint, style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round))
    }
}

@Composable
fun ChevronDownIcon(
    modifier: Modifier = Modifier,
    size: Dp = 14.dp,
    tint: Color = FlowMeterColors.TextSecondary
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = w * 0.12f

        val path = Path().apply {
            moveTo(w * 0.20f, h * 0.35f)
            lineTo(w * 0.50f, h * 0.65f)
            lineTo(w * 0.80f, h * 0.35f)
        }
        drawPath(path = path, color = tint, style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round))
    }
}

@Composable
fun UpArrowIcon(
    modifier: Modifier = Modifier,
    size: Dp = 20.dp,
    tint: Color = FlowMeterColors.CyanAccent
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = w * 0.10f

        val path = Path().apply {
            moveTo(w * 0.50f, h * 0.18f)
            lineTo(w * 0.22f, h * 0.46f)
            moveTo(w * 0.50f, h * 0.18f)
            lineTo(w * 0.78f, h * 0.46f)
            moveTo(w * 0.50f, h * 0.18f)
            lineTo(w * 0.50f, h * 0.82f)
        }
        drawPath(path = path, color = tint, style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round))
    }
}

@Composable
fun PieChartIcon(
    modifier: Modifier = Modifier,
    size: Dp = 20.dp,
    tint: Color = FlowMeterColors.CyanAccent
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = w * 0.09f

        drawArc(
            color = tint,
            startAngle = 10f,
            sweepAngle = 250f,
            useCenter = true,
            topLeft = Offset(w * 0.12f, h * 0.12f),
            size = Size(w * 0.76f, h * 0.76f),
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )

        drawArc(
            color = tint,
            startAngle = 275f,
            sweepAngle = 80f,
            useCenter = true,
            topLeft = Offset(w * 0.18f, h * 0.08f),
            size = Size(w * 0.76f, h * 0.76f),
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
    }
}

@Composable
fun ShieldCheckIcon(
    modifier: Modifier = Modifier,
    size: Dp = 28.dp,
    tint: Color = FlowMeterColors.CyanAccent
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = w * 0.09f

        // Shield contour
        val shieldPath = Path().apply {
            moveTo(w * 0.5f, h * 0.10f)
            lineTo(w * 0.85f, h * 0.25f)
            cubicTo(w * 0.85f, h * 0.62f, w * 0.68f, h * 0.82f, w * 0.5f, h * 0.92f)
            cubicTo(w * 0.32f, h * 0.82f, w * 0.15f, h * 0.62f, w * 0.15f, h * 0.25f)
            close()
        }
        drawPath(
            path = shieldPath,
            color = tint,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )

        // Checkmark inside shield
        val checkPath = Path().apply {
            moveTo(w * 0.36f, h * 0.50f)
            lineTo(w * 0.46f, h * 0.60f)
            lineTo(w * 0.64f, h * 0.40f)
        }
        drawPath(
            path = checkPath,
            color = tint,
            style = Stroke(width = strokeWidth * 1.1f, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
    }
}

@Composable
fun TrendingFlowIcon(
    modifier: Modifier = Modifier,
    size: Dp = 20.dp,
    tint: Color = FlowMeterColors.CyanAccent
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = w * 0.10f

        val path = Path().apply {
            moveTo(w * 0.18f, h * 0.68f)
            lineTo(w * 0.42f, h * 0.45f)
            lineTo(w * 0.60f, h * 0.58f)
            lineTo(w * 0.82f, h * 0.32f)
            // Arrow head
            moveTo(w * 0.66f, h * 0.32f)
            lineTo(w * 0.82f, h * 0.32f)
            lineTo(w * 0.82f, h * 0.48f)
        }
        drawPath(
            path = path,
            color = tint,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
    }
}

@Composable
fun ThermometerIcon(
    modifier: Modifier = Modifier,
    size: Dp = 20.dp,
    tint: Color = FlowMeterColors.CyanAccent
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = w * 0.09f

        // Stem tube
        drawRoundRect(
            color = tint,
            topLeft = Offset(w * 0.38f, h * 0.12f),
            size = Size(w * 0.24f, h * 0.52f),
            cornerRadius = CornerRadius(w * 0.12f, w * 0.12f),
            style = Stroke(width = strokeWidth)
        )

        // Bottom bulb
        drawCircle(
            color = tint,
            radius = w * 0.24f,
            center = Offset(w * 0.5f, h * 0.72f),
            style = Stroke(width = strokeWidth)
        )

        // Mercury fill inside bulb
        drawCircle(
            color = tint,
            radius = w * 0.12f,
            center = Offset(w * 0.5f, h * 0.72f)
        )
        drawLine(
            color = tint,
            start = Offset(w * 0.5f, h * 0.45f),
            end = Offset(w * 0.5f, h * 0.65f),
            strokeWidth = strokeWidth * 0.9f,
            cap = StrokeCap.Round
        )
    }
}

@Composable
fun LightbulbIcon(
    modifier: Modifier = Modifier,
    size: Dp = 20.dp,
    tint: Color = FlowMeterColors.CyanAccent
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = w * 0.09f

        // Bulb outline
        val bulbPath = Path().apply {
            moveTo(w * 0.35f, h * 0.70f)
            cubicTo(w * 0.15f, h * 0.55f, w * 0.15f, h * 0.28f, w * 0.50f, h * 0.20f)
            cubicTo(w * 0.85f, h * 0.28f, w * 0.85f, h * 0.55f, w * 0.65f, h * 0.70f)
            close()
        }
        drawPath(
            path = bulbPath,
            color = tint,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )

        // Bulb base
        drawLine(
            color = tint,
            start = Offset(w * 0.38f, h * 0.80f),
            end = Offset(w * 0.62f, h * 0.80f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = tint,
            start = Offset(w * 0.44f, h * 0.90f),
            end = Offset(w * 0.56f, h * 0.90f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )

        // Rays
        drawLine(
            color = tint,
            start = Offset(w * 0.50f, h * 0.05f),
            end = Offset(w * 0.50f, h * 0.12f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = tint,
            start = Offset(w * 0.10f, h * 0.25f),
            end = Offset(w * 0.18f, h * 0.30f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = tint,
            start = Offset(w * 0.90f, h * 0.25f),
            end = Offset(w * 0.82f, h * 0.30f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
    }
}

@Composable
fun ChevronUpIcon(
    modifier: Modifier = Modifier,
    size: Dp = 14.dp,
    tint: Color = FlowMeterColors.TextSecondary
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = w * 0.12f

        val path = Path().apply {
            moveTo(w * 0.20f, h * 0.65f)
            lineTo(w * 0.50f, h * 0.35f)
            lineTo(w * 0.80f, h * 0.65f)
        }
        drawPath(path = path, color = tint, style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round))
    }
}

@Composable
fun WifiIcon(
    modifier: Modifier = Modifier,
    size: Dp = 20.dp,
    tint: Color = FlowMeterColors.CyanAccent
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = w * 0.09f

        // 3 wifi arcs
        drawArc(
            color = tint,
            startAngle = 225f,
            sweepAngle = 90f,
            useCenter = false,
            topLeft = Offset(w * 0.10f, h * 0.20f),
            size = Size(w * 0.80f, h * 0.80f),
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )
        drawArc(
            color = tint,
            startAngle = 225f,
            sweepAngle = 90f,
            useCenter = false,
            topLeft = Offset(w * 0.25f, h * 0.35f),
            size = Size(w * 0.50f, h * 0.50f),
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )
        drawCircle(
            color = tint,
            radius = strokeWidth * 0.9f,
            center = Offset(w * 0.5f, h * 0.75f)
        )
    }
}

@Composable
fun InfoSquareIcon(
    modifier: Modifier = Modifier,
    size: Dp = 20.dp,
    tint: Color = FlowMeterColors.CyanAccent
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = w * 0.09f

        drawRoundRect(
            color = tint,
            topLeft = Offset(w * 0.15f, h * 0.15f),
            size = Size(w * 0.70f, h * 0.70f),
            cornerRadius = CornerRadius(w * 0.15f, w * 0.15f),
            style = Stroke(width = strokeWidth)
        )
        drawCircle(color = tint, radius = strokeWidth * 0.7f, center = Offset(w * 0.5f, h * 0.35f))
        drawLine(
            color = tint,
            start = Offset(w * 0.5f, h * 0.48f),
            end = Offset(w * 0.5f, h * 0.68f),
            strokeWidth = strokeWidth * 1.1f,
            cap = StrokeCap.Round
        )
    }
}

@Composable
fun PaletteIcon(
    modifier: Modifier = Modifier,
    size: Dp = 20.dp,
    tint: Color = FlowMeterColors.CyanAccent
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = w * 0.09f

        val path = Path().apply {
            moveTo(w * 0.5f, h * 0.12f)
            cubicTo(w * 0.85f, h * 0.12f, w * 0.90f, h * 0.45f, w * 0.75f, h * 0.75f)
            cubicTo(w * 0.68f, h * 0.88f, w * 0.50f, h * 0.88f, w * 0.45f, h * 0.78f)
            cubicTo(w * 0.42f, h * 0.72f, w * 0.32f, h * 0.72f, w * 0.28f, h * 0.78f)
            cubicTo(w * 0.20f, h * 0.90f, w * 0.10f, h * 0.70f, w * 0.10f, h * 0.50f)
            cubicTo(w * 0.10f, h * 0.25f, w * 0.28f, h * 0.12f, w * 0.5f, h * 0.12f)
            close()
        }
        drawPath(path = path, color = tint, style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round))

        drawCircle(color = tint, radius = strokeWidth * 0.6f, center = Offset(w * 0.32f, h * 0.35f))
        drawCircle(color = tint, radius = strokeWidth * 0.6f, center = Offset(w * 0.50f, h * 0.28f))
        drawCircle(color = tint, radius = strokeWidth * 0.6f, center = Offset(w * 0.68f, h * 0.38f))
    }
}

@Composable
fun SunThemeIcon(
    modifier: Modifier = Modifier,
    size: Dp = 20.dp,
    tint: Color = FlowMeterColors.CyanAccent
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = w * 0.09f

        drawCircle(color = tint, radius = w * 0.22f, center = Offset(w / 2f, h / 2f), style = Stroke(width = strokeWidth))

        for (i in 0 until 8) {
            val angle = (i * 45) * (Math.PI / 180.0)
            val cos = Math.cos(angle).toFloat()
            val sin = Math.sin(angle).toFloat()
            drawLine(
                color = tint,
                start = Offset(w / 2f + cos * w * 0.30f, h / 2f + sin * h * 0.30f),
                end = Offset(w / 2f + cos * w * 0.42f, h / 2f + sin * h * 0.42f),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round
            )
        }
    }
}

@Composable
fun FontSizeAaIcon(
    modifier: Modifier = Modifier,
    size: Dp = 20.dp,
    tint: Color = FlowMeterColors.CyanAccent
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = w * 0.10f

        // Large 'A'
        val pathA1 = Path().apply {
            moveTo(w * 0.15f, h * 0.75f)
            lineTo(w * 0.35f, h * 0.20f)
            lineTo(w * 0.55f, h * 0.75f)
        }
        drawPath(path = pathA1, color = tint, style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round))
        drawLine(color = tint, start = Offset(w * 0.23f, h * 0.56f), end = Offset(w * 0.47f, h * 0.56f), strokeWidth = strokeWidth * 0.9f)

        // Small 'a'
        drawCircle(color = tint, radius = w * 0.14f, center = Offset(w * 0.74f, h * 0.62f), style = Stroke(width = strokeWidth * 0.85f))
        drawLine(color = tint, start = Offset(w * 0.88f, h * 0.48f), end = Offset(w * 0.88f, h * 0.75f), strokeWidth = strokeWidth * 0.85f, cap = StrokeCap.Round)
    }
}

@Composable
fun DownloadTrayIcon(
    modifier: Modifier = Modifier,
    size: Dp = 20.dp,
    tint: Color = FlowMeterColors.CyanAccent
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = w * 0.09f

        // Tray
        val tray = Path().apply {
            moveTo(w * 0.18f, h * 0.55f)
            lineTo(w * 0.18f, h * 0.80f)
            lineTo(w * 0.82f, h * 0.80f)
            lineTo(w * 0.82f, h * 0.55f)
        }
        drawPath(path = tray, color = tint, style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round))

        // Arrow down
        drawLine(color = tint, start = Offset(w * 0.5f, h * 0.20f), end = Offset(w * 0.5f, h * 0.58f), strokeWidth = strokeWidth, cap = StrokeCap.Round)
        val arrowHead = Path().apply {
            moveTo(w * 0.32f, h * 0.42f)
            lineTo(w * 0.50f, h * 0.58f)
            lineTo(w * 0.68f, h * 0.42f)
        }
        drawPath(path = arrowHead, color = tint, style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round))
    }
}

@Composable
fun DocumentTextIcon(
    modifier: Modifier = Modifier,
    size: Dp = 20.dp,
    tint: Color = FlowMeterColors.CyanAccent
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = w * 0.09f

        val docPath = Path().apply {
            moveTo(w * 0.20f, h * 0.15f)
            lineTo(w * 0.60f, h * 0.15f)
            lineTo(w * 0.80f, h * 0.35f)
            lineTo(w * 0.80f, h * 0.85f)
            lineTo(w * 0.20f, h * 0.85f)
            close()
        }
        drawPath(path = docPath, color = tint, style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round))

        // Text lines
        drawLine(color = tint, start = Offset(w * 0.35f, h * 0.48f), end = Offset(w * 0.65f, h * 0.48f), strokeWidth = strokeWidth * 0.8f, cap = StrokeCap.Round)
        drawLine(color = tint, start = Offset(w * 0.35f, h * 0.62f), end = Offset(w * 0.65f, h * 0.62f), strokeWidth = strokeWidth * 0.8f, cap = StrokeCap.Round)
    }
}

@Composable
fun InfoCircleIcon(
    modifier: Modifier = Modifier,
    size: Dp = 20.dp,
    tint: Color = FlowMeterColors.CyanAccent
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = w * 0.09f

        drawCircle(color = tint, radius = (w / 2f) - (strokeWidth / 2f), center = Offset(w / 2f, h / 2f), style = Stroke(width = strokeWidth))
        drawCircle(color = tint, radius = strokeWidth * 0.7f, center = Offset(w * 0.5f, h * 0.34f))
        drawLine(color = tint, start = Offset(w * 0.5f, h * 0.46f), end = Offset(w * 0.5f, h * 0.68f), strokeWidth = strokeWidth * 1.1f, cap = StrokeCap.Round)
    }
}

@Composable
fun ShieldSecurityIcon(
    modifier: Modifier = Modifier,
    size: Dp = 20.dp,
    tint: Color = FlowMeterColors.CyanAccent
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = w * 0.09f

        val shieldPath = Path().apply {
            moveTo(w * 0.5f, h * 0.12f)
            lineTo(w * 0.85f, h * 0.25f)
            cubicTo(w * 0.85f, h * 0.62f, w * 0.68f, h * 0.82f, w * 0.5f, h * 0.90f)
            cubicTo(w * 0.32f, h * 0.82f, w * 0.15f, h * 0.62f, w * 0.15f, h * 0.25f)
            close()
        }
        drawPath(path = shieldPath, color = tint, style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round))
        drawCircle(color = tint, radius = strokeWidth * 0.8f, center = Offset(w * 0.5f, h * 0.48f))
    }
}

package com.arnav.flowmeter.components.cards

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arnav.flowmeter.branding.colors.FlowMeterColors
import com.arnav.flowmeter.branding.typography.FlowMeterTypography
import com.arnav.flowmeter.components.buttons.ConnectButton
import com.arnav.flowmeter.components.icons.AnalyticsNavIcon
import com.arnav.flowmeter.components.icons.CalendarIcon
import com.arnav.flowmeter.components.icons.ChevronDownIcon
import com.arnav.flowmeter.components.icons.ChevronRightIcon
import com.arnav.flowmeter.components.icons.ClockIcon
import com.arnav.flowmeter.components.icons.DeviceSensorIcon
import com.arnav.flowmeter.components.icons.FlowWavesIcon
import com.arnav.flowmeter.components.icons.HistoryIcon
import com.arnav.flowmeter.components.icons.PieChartIcon
import com.arnav.flowmeter.components.icons.ShieldCheckIcon
import com.arnav.flowmeter.components.icons.ThermometerIcon
import com.arnav.flowmeter.components.icons.TrendingFlowIcon
import com.arnav.flowmeter.components.icons.WaterDropIcon
import com.arnav.flowmeter.components.status.StatusChip
import com.arnav.flowmeter.components.status.TelemetryDataType
import com.arnav.flowmeter.components.status.TelemetryTagChip
import com.arnav.flowmeter.components.waves.CardAmbientWaveOverlay
import kotlin.math.cos
import kotlin.math.sin

/**
 * Base card container with glassmorphic dark navy styling and fine glowing border.
 */
@Composable
fun FlowMeterCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 22.dp,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val baseModifier = modifier
        .clip(RoundedCornerShape(cornerRadius))
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    FlowMeterColors.CardSurface,
                    FlowMeterColors.CardBackground
                )
            )
        )
        .border(
            width = 1.dp,
            brush = FlowMeterColors.CardBorderBrush,
            shape = RoundedCornerShape(cornerRadius)
        )

    val finalModifier = if (onClick != null) {
        baseModifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = ripple(color = FlowMeterColors.CyanAccent.copy(alpha = 0.18f)),
            onClick = onClick
        )
    } else {
        baseModifier
    }

    Box(
        modifier = finalModifier.padding(16.dp)
    ) {
        content()
    }
}

/**
 * Small decorative animated wave graphic for metric cards with smooth shimmer and translucent fill.
 */
@Composable
fun MetricWaveGraphic(
    modifier: Modifier = Modifier,
    height: Dp = 24.dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "metric_wave_anim")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * Math.PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "metric_wave_phase"
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        val w = size.width
        val h = size.height

        // Area fill under wave
        val fillPath = Path()
        fillPath.moveTo(0f, h)
        var x = 0f
        val step = 3f
        while (x <= w) {
            val progress = x / w
            val y = (h * 0.48f) + (sin((progress * 2.5 * Math.PI) + phase).toFloat() * (h * 0.28f))
            fillPath.lineTo(x, y)
            x += step
        }
        fillPath.lineTo(w, h)
        fillPath.close()

        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0x3300A3FF),
                    Color(0x080284C7),
                    Color.Transparent
                ),
                startY = 0f,
                endY = h
            )
        )

        // Crest stroke
        val path = Path()
        x = 0f
        var isFirst = true
        while (x <= w) {
            val progress = x / w
            val y = (h * 0.48f) + (sin((progress * 2.5 * Math.PI) + phase).toFloat() * (h * 0.28f))
            if (isFirst) {
                path.moveTo(x, y)
                isFirst = false
            } else {
                path.lineTo(x, y)
            }
            x += step
        }

        drawPath(
            path = path,
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color(0x2200A3FF),
                    Color(0x9938BDF8),
                    Color(0xFF00F5D4),
                    Color(0x4038BDF8)
                )
            ),
            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

/**
 * Circular flow meter gauge with glowing radial tick marks and placeholder value.
 */
@Composable
fun CircularFlowGauge(
    modifier: Modifier = Modifier,
    size: Dp = 160.dp,
    value: String = "—",
    unit: String = "L/min"
) {
    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val w = this.size.width
            val h = this.size.height
            val center = Offset(w / 2f, h / 2f)
            val radius = (w / 2f) - 16.dp.toPx()

            drawArc(
                brush = Brush.sweepGradient(
                    colors = listOf(
                        Color(0x1A00A3FF),
                        Color(0x4038BDF8),
                        Color(0x6600A3FF),
                        Color(0x1A00A3FF)
                    )
                ),
                startAngle = 140f,
                sweepAngle = 260f,
                useCenter = false,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = 2.5.dp.toPx(), cap = StrokeCap.Round)
            )

            val totalTicks = 40
            val startAngle = 140.0
            val sweepAngle = 260.0
            val step = sweepAngle / (totalTicks - 1)

            for (i in 0 until totalTicks) {
                val currentAngleDeg = startAngle + (i * step)
                val currentAngleRad = Math.toRadians(currentAngleDeg)

                val tickLength = if (i % 5 == 0) 10.dp.toPx() else 6.dp.toPx()
                val tickWidth = if (i % 5 == 0) 1.8.dp.toPx() else 1.dp.toPx()
                val tickAlpha = if (i % 5 == 0) 0.80f else 0.40f

                val innerRadius = radius - 5.dp.toPx() - tickLength
                val outerRadius = radius - 5.dp.toPx()

                val startX = (center.x + innerRadius * cos(currentAngleRad)).toFloat()
                val startY = (center.y + innerRadius * sin(currentAngleRad)).toFloat()
                val endX = (center.x + outerRadius * cos(currentAngleRad)).toFloat()
                val endY = (center.y + outerRadius * sin(currentAngleRad)).toFloat()

                drawLine(
                    color = FlowMeterColors.CyanAccent.copy(alpha = tickAlpha),
                    start = Offset(startX, startY),
                    end = Offset(endX, endY),
                    strokeWidth = tickWidth,
                    cap = StrokeCap.Round
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = FlowMeterTypography.MetricPlaceholderLarge.copy(fontSize = 38.sp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = unit,
                style = FlowMeterTypography.UnitLabel
            )
        }
    }
}

/**
 * Dropdown Pill chip used for date ranges (e.g. "Last 7 days", "Today").
 */
@Composable
fun DateDropdownChip(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(FlowMeterColors.DarkBlueAction)
            .border(
                width = 1.dp,
                color = FlowMeterColors.CardBorderSubtle,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = FlowMeterColors.CyanAccent.copy(alpha = 0.2f)),
                onClick = onClick
            )
            .padding(horizontal = 10.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            CalendarIcon(size = 14.dp, tint = FlowMeterColors.TextSecondary)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = text,
                style = FlowMeterTypography.CardFooterText.copy(
                    color = FlowMeterColors.TextPrimary,
                    fontSize = 12.sp
                )
            )
            Spacer(modifier = Modifier.width(4.dp))
            ChevronDownIcon(size = 12.dp, tint = FlowMeterColors.TextSecondary)
        }
    }
}

/**
 * Main Current-Flow Monitoring Card with tap-to-detail interaction.
 */
@Composable
fun CurrentFlowCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    FlowMeterCard(
        modifier = modifier.fillMaxWidth(),
        cornerRadius = 24.dp,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(FlowMeterColors.DarkBlueIconBg),
                        contentAlignment = Alignment.Center
                    ) {
                        FlowWavesIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Current Flow",
                                style = FlowMeterTypography.CardHeaderTitle
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            TelemetryTagChip(type = TelemetryDataType.MEASURED)
                        }
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StatusChip(text = "Waiting for data")
                    Spacer(modifier = Modifier.width(6.dp))
                    ChevronRightIcon(size = 14.dp, tint = FlowMeterColors.TextSecondary)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularFlowGauge(size = 160.dp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No live data available",
                    style = FlowMeterTypography.CardFooterText.copy(
                        color = FlowMeterColors.TextPrimary,
                        fontSize = 13.sp
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Tap to view flow dynamics & statistics",
                    style = FlowMeterTypography.CardFooterText.copy(
                        color = FlowMeterColors.CyanAccent,
                        fontSize = 11.sp
                    )
                )
            }
        }
    }
}

/**
 * Interactive Glanceable Metric Card with tap feedback and data type badge.
 */
@Composable
fun SummaryMetricCard(
    title: String,
    unit: String,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    dataType: TelemetryDataType? = null,
    value: String = "—",
    subtitle: String = "No data yet",
    onClick: (() -> Unit)? = null
) {
    FlowMeterCard(
        modifier = modifier,
        cornerRadius = 20.dp,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(FlowMeterColors.DarkBlueIconBg),
                        contentAlignment = Alignment.Center
                    ) {
                        icon()
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = title,
                        style = FlowMeterTypography.CardHeaderTitle.copy(fontSize = 12.sp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                if (dataType != null) {
                    TelemetryTagChip(type = dataType)
                } else if (onClick != null) {
                    ChevronRightIcon(size = 13.dp, tint = FlowMeterColors.TextSecondary)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = value,
                    style = FlowMeterTypography.MetricPlaceholderMedium
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = unit,
                    style = FlowMeterTypography.UnitLabel,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = subtitle,
                    style = FlowMeterTypography.CardFooterText.copy(
                        color = if (onClick != null) FlowMeterColors.CyanAccent else FlowMeterColors.TextMuted,
                        fontSize = 11.sp
                    )
                )
                if (onClick != null && dataType != null) {
                    ChevronRightIcon(size = 12.dp, tint = FlowMeterColors.TextSecondary)
                }
            }
        }
    }
}

/**
 * Detailed Metric Card (for Detail & Research screens).
 */
@Composable
fun DetailMetricCard(
    title: String,
    value: String,
    unit: String = "",
    subtitle: String = "",
    icon: @Composable () -> Unit,
    dataType: TelemetryDataType? = null,
    modifier: Modifier = Modifier
) {
    FlowMeterCard(
        modifier = modifier,
        cornerRadius = 18.dp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(FlowMeterColors.DarkBlueIconBg),
                        contentAlignment = Alignment.Center
                    ) {
                        icon()
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = title,
                        style = FlowMeterTypography.CardHeaderTitle.copy(fontSize = 12.sp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                if (dataType != null) {
                    TelemetryTagChip(type = dataType)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = value,
                    style = FlowMeterTypography.MetricPlaceholderMedium.copy(fontSize = 24.sp)
                )
                if (unit.isNotEmpty()) {
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = unit,
                        style = FlowMeterTypography.UnitLabel.copy(fontSize = 12.sp),
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                }
            }

            if (subtitle.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    style = FlowMeterTypography.CardFooterText.copy(fontSize = 11.sp),
                    color = FlowMeterColors.TextSecondary
                )
            }
        }
    }
}

/**
 * Clean Empty Chart Card with customizable title and message.
 */
@Composable
fun EmptyChartCard(
    title: String,
    subtitle: String,
    emptyMessage: String,
    hint: String,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    trailing: @Composable () -> Unit = {}
) {
    FlowMeterCard(
        modifier = modifier.fillMaxWidth(),
        cornerRadius = 24.dp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(FlowMeterColors.DarkBlueIconBg),
                        contentAlignment = Alignment.Center
                    ) {
                        icon()
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = title,
                            style = FlowMeterTypography.CardHeaderTitle
                        )
                        Text(
                            text = subtitle,
                            style = FlowMeterTypography.CardFooterText.copy(fontSize = 11.sp)
                        )
                    }
                }
                trailing()
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(FlowMeterColors.DarkBlueAction.copy(alpha = 0.45f))
                    .border(
                        width = 1.dp,
                        color = FlowMeterColors.CardBorderSubtle.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Subtle baseline reference line
                Canvas(modifier = Modifier.matchParentSize()) {
                    val w = size.width
                    val h = size.height
                    val lineY = h * 0.72f
                    drawLine(
                        color = Color(0x1A38BDF8),
                        start = Offset(20.dp.toPx(), lineY),
                        end = Offset(w - 20.dp.toPx(), lineY),
                        strokeWidth = 1.dp.toPx()
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 24.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(FlowMeterColors.DarkBlueIconBg),
                        contentAlignment = Alignment.Center
                    ) {
                        icon()
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = emptyMessage,
                        style = FlowMeterTypography.CardHeaderTitle.copy(
                            fontSize = 13.sp,
                            color = FlowMeterColors.TextPrimary
                        )
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = hint,
                        style = FlowMeterTypography.CardFooterText.copy(
                            color = FlowMeterColors.TextSecondary,
                            fontSize = 11.sp
                        )
                    )
                }
            }
        }
    }
}

/**
 * Total Runtime / Active Session Card.
 */
@Composable
fun TotalRuntimeCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    FlowMeterCard(
        modifier = modifier.fillMaxWidth(),
        cornerRadius = 20.dp,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(FlowMeterColors.DarkBlueIconBg),
                    contentAlignment = Alignment.Center
                ) {
                    ClockIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Runtime & Sessions",
                            style = FlowMeterTypography.CardHeaderTitle
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        TelemetryTagChip(type = TelemetryDataType.CALCULATED)
                    }
                    Text(
                        text = "Session activity & duration",
                        style = FlowMeterTypography.CardFooterText.copy(fontSize = 11.sp)
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "—",
                    style = FlowMeterTypography.MetricPlaceholderMedium.copy(fontSize = 20.sp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Hrs",
                    style = FlowMeterTypography.UnitLabel
                )
                if (onClick != null) {
                    Spacer(modifier = Modifier.width(6.dp))
                    ChevronRightIcon(size = 14.dp, tint = FlowMeterColors.TextSecondary)
                }
            }
        }
    }
}

/**
 * Device Connection Card.
 */
@Composable
fun DeviceConnectionCard(
    modifier: Modifier = Modifier,
    onConnectClick: () -> Unit = {}
) {
    FlowMeterCard(
        modifier = modifier.fillMaxWidth(),
        cornerRadius = 20.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(FlowMeterColors.DarkBlueIconBg),
                    contentAlignment = Alignment.Center
                ) {
                    DeviceSensorIcon(size = 20.dp, tint = FlowMeterColors.CyanAccent)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "No device connected",
                            style = FlowMeterTypography.CardHeaderTitle.copy(fontSize = 14.sp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        TelemetryTagChip(type = TelemetryDataType.DEVICE)
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Connect your FlowMeter hardware to start live telemetry.",
                        style = FlowMeterTypography.CardFooterText.copy(fontSize = 11.sp),
                        lineHeight = 15.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            ConnectButton(onClick = onConnectClick)
        }
    }
}

/**
 * Water Usage Chart Card with deliberate clean empty state.
 */
@Composable
fun WaterUsageChartCard(
    modifier: Modifier = Modifier,
    dateRangeText: String = "Last 7 days"
) {
    EmptyChartCard(
        title = "Water Usage",
        subtitle = "Total consumption over time",
        emptyMessage = "No data available",
        hint = "Connect your device to view usage trends",
        icon = { AnalyticsNavIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
        modifier = modifier,
        trailing = { DateDropdownChip(text = dateRangeText) }
    )
}

/**
 * Usage Distribution Card with Donut Chart and Time of Day Breakdown.
 */
@Composable
fun UsageDistributionCard(
    modifier: Modifier = Modifier,
    selectedDate: String = "Today"
) {
    FlowMeterCard(
        modifier = modifier.fillMaxWidth(),
        cornerRadius = 24.dp
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(FlowMeterColors.DarkBlueIconBg),
                        contentAlignment = Alignment.Center
                    ) {
                        PieChartIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Usage Distribution",
                            style = FlowMeterTypography.CardHeaderTitle
                        )
                        Text(
                            text = "Breakdown by time of day",
                            style = FlowMeterTypography.CardFooterText.copy(fontSize = 11.sp)
                        )
                    }
                }

                DateDropdownChip(text = selectedDate)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Donut Chart + Legend
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Donut Chart Placeholder
                Box(
                    modifier = Modifier.size(130.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.matchParentSize()) {
                        val w = size.width
                        val h = size.height
                        val strokeW = 14.dp.toPx()
                        val radius = (w / 2f) - (strokeW / 2f) - 4.dp.toPx()

                        // Dark base track
                        drawCircle(
                            color = Color(0xFF142034),
                            radius = radius,
                            center = Offset(w / 2f, h / 2f),
                            style = Stroke(width = strokeW)
                        )

                        // Subtle cyan arc glow hint
                        drawArc(
                            brush = Brush.sweepGradient(
                                colors = listOf(
                                    Color(0x2600A3FF),
                                    Color(0x4038BDF8),
                                    Color(0x2600A3FF)
                                )
                            ),
                            startAngle = 0f,
                            sweepAngle = 360f,
                            useCenter = false,
                            topLeft = Offset(w / 2f - radius, h / 2f - radius),
                            size = Size(radius * 2, radius * 2),
                            style = Stroke(width = strokeW)
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "—",
                            style = FlowMeterTypography.MetricPlaceholderMedium.copy(fontSize = 24.sp)
                        )
                        Text(
                            text = "Total",
                            style = FlowMeterTypography.CardFooterText.copy(
                                fontSize = 10.sp,
                                color = FlowMeterColors.TextSecondary
                            )
                        )
                        Text(
                            text = "Litres",
                            style = FlowMeterTypography.CardFooterText.copy(
                                fontSize = 10.sp,
                                color = FlowMeterColors.TextSecondary
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Time Breakdown List
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    DistributionLegendRow(
                        color = Color(0xFF00A3FF),
                        label = "Morning (6AM - 12PM)",
                        value = "—"
                    )
                    DistributionLegendRow(
                        color = Color(0xFF38BDF8),
                        label = "Afternoon (12PM - 6PM)",
                        value = "—"
                    )
                    DistributionLegendRow(
                        color = Color(0xFFFBBF24),
                        label = "Evening (6PM - 12AM)",
                        value = "—"
                    )
                    DistributionLegendRow(
                        color = Color(0xFFF87171),
                        label = "Night (12AM - 6AM)",
                        value = "—"
                    )
                }
            }
        }
    }
}

@Composable
private fun DistributionLegendRow(
    color: Color,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(color)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                style = FlowMeterTypography.CardFooterText.copy(
                    fontSize = 11.sp,
                    color = FlowMeterColors.TextPrimary
                )
            )
        }

        Text(
            text = value,
            style = FlowMeterTypography.CardFooterText.copy(
                fontSize = 12.sp,
                color = FlowMeterColors.TextPrimary
            )
        )
    }
}

/**
 * Main Status Card on Alerts screen with concentric glowing shield.
 */
@Composable
fun MainAlertStatusCard(
    modifier: Modifier = Modifier
) {
    FlowMeterCard(
        modifier = modifier.fillMaxWidth(),
        cornerRadius = 24.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Concentric Glowing Shield Icon Container
            Box(
                modifier = Modifier.size(140.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.matchParentSize()) {
                    val w = size.width
                    val h = size.height
                    val center = Offset(w / 2f, h / 2f)

                    // Outer ring
                    drawCircle(
                        color = Color(0x1F00A3FF),
                        radius = (w / 2f) - 6.dp.toPx(),
                        center = center,
                        style = Stroke(width = 1.dp.toPx())
                    )
                    // Middle glowing ring
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0x3300A3FF),
                                Color(0x1438BDF8),
                                Color(0x00000000)
                            ),
                            center = center,
                            radius = (w / 2f) - 18.dp.toPx()
                        ),
                        radius = (w / 2f) - 18.dp.toPx(),
                        center = center
                    )
                    drawCircle(
                        color = Color(0x4038BDF8),
                        radius = (w / 2f) - 18.dp.toPx(),
                        center = center,
                        style = Stroke(width = 1.2.dp.toPx())
                    )
                    // Inner filled disk
                    drawCircle(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF13233C),
                                Color(0xFF0F1A2C)
                            )
                        ),
                        radius = (w / 2f) - 30.dp.toPx(),
                        center = center
                    )
                    drawCircle(
                        color = Color(0x6600A3FF),
                        radius = (w / 2f) - 30.dp.toPx(),
                        center = center,
                        style = Stroke(width = 1.5.dp.toPx())
                    )
                }

                // Shield Icon in center
                ShieldCheckIcon(
                    size = 38.dp,
                    tint = FlowMeterColors.CyanAccent
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Wave overlay behind text
            CardAmbientWaveOverlay(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                height = 36.dp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "No active alerts",
                style = FlowMeterTypography.GreetingName.copy(fontSize = 20.sp),
                color = FlowMeterColors.TextPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Everything looks normal for now.",
                style = FlowMeterTypography.CardFooterText.copy(
                    fontSize = 13.sp,
                    color = FlowMeterColors.TextSecondary
                )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "We'll notify you if there are any unusual\nwater usage patterns.",
                style = FlowMeterTypography.CardFooterText.copy(
                    fontSize = 13.sp,
                    color = FlowMeterColors.TextSecondary
                ),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                lineHeight = 18.sp
            )
        }
    }
}

/**
 * Recent Alerts Section List Card.
 */
@Composable
fun RecentAlertsCard(
    modifier: Modifier = Modifier,
    onViewAllClick: () -> Unit = {}
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Alert Categories",
                style = FlowMeterTypography.GreetingName.copy(fontSize = 18.sp)
            )

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable(onClick = onViewAllClick)
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Diagnostics",
                    style = FlowMeterTypography.CardFooterText.copy(
                        color = FlowMeterColors.CyanAccent,
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp
                    )
                )
                Spacer(modifier = Modifier.width(3.dp))
                ChevronRightIcon(size = 13.dp, tint = FlowMeterColors.CyanAccent)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        FlowMeterCard(
            modifier = Modifier.fillMaxWidth(),
            cornerRadius = 22.dp
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                AlertCategoryRow(
                    icon = { WaterDropIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                    title = "High Volume & Leak Alerts",
                    subtitle = "No active leak detected",
                    dataType = TelemetryDataType.CALCULATED
                )
                AlertDivider()
                AlertCategoryRow(
                    icon = { TrendingFlowIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                    title = "Unusual Flow Rate",
                    subtitle = "Within normal thresholds",
                    dataType = TelemetryDataType.MEASURED
                )
                AlertDivider()
                AlertCategoryRow(
                    icon = { DeviceSensorIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                    title = "Device & Battery State",
                    subtitle = "No hardware faults reported",
                    dataType = TelemetryDataType.DEVICE
                )
                AlertDivider()
                AlertCategoryRow(
                    icon = { ThermometerIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                    title = "Thermal & Pressure Bounds",
                    subtitle = "Operating environment nominal",
                    dataType = TelemetryDataType.QUALITY
                )
            }
        }
    }
}

@Composable
private fun AlertCategoryRow(
    icon: @Composable () -> Unit,
    title: String,
    subtitle: String,
    dataType: TelemetryDataType? = null,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = FlowMeterColors.CyanAccent.copy(alpha = 0.2f)),
                onClick = onClick
            )
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(FlowMeterColors.DarkBlueIconBg),
                contentAlignment = Alignment.Center
            ) {
                icon()
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = title,
                    style = FlowMeterTypography.CardHeaderTitle.copy(fontSize = 14.sp)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = FlowMeterTypography.CardFooterText.copy(
                        fontSize = 12.sp,
                        color = FlowMeterColors.TextSecondary
                    )
                )
            }
        }

        if (dataType != null) {
            TelemetryTagChip(type = dataType)
        } else {
            ChevronRightIcon(size = 14.dp, tint = FlowMeterColors.TextSecondary)
        }
    }
}

@Composable
private fun AlertDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 48.dp)
            .height(1.dp)
            .background(Color(0x1438BDF8))
    )
}

/**
 * Telemetry Section Card and Register Row for Deep Telemetry & Research.
 */
@Composable
fun TelemetrySectionCard(
    title: String,
    icon: @Composable () -> Unit,
    dataType: TelemetryDataType,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    FlowMeterCard(
        modifier = modifier.fillMaxWidth(),
        cornerRadius = 20.dp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(FlowMeterColors.DarkBlueIconBg),
                        contentAlignment = Alignment.Center
                    ) {
                        icon()
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = title,
                        style = FlowMeterTypography.CardHeaderTitle.copy(fontSize = 15.sp)
                    )
                }

                TelemetryTagChip(type = dataType)
            }

            Spacer(modifier = Modifier.height(14.dp))

            content()
        }
    }
}

@Composable
fun TelemetryRegisterRow(
    label: String,
    value: String,
    unit: String = "",
    tag: TelemetryDataType? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = label,
                style = FlowMeterTypography.CardFooterText.copy(
                    fontSize = 12.sp,
                    color = FlowMeterColors.TextSecondary
                )
            )
            if (tag != null) {
                Spacer(modifier = Modifier.width(6.dp))
                TelemetryTagChip(type = tag)
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = value,
                style = FlowMeterTypography.CardHeaderTitle.copy(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = FlowMeterColors.TextPrimary
                )
            )
            if (unit.isNotEmpty()) {
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = unit,
                    style = FlowMeterTypography.CardFooterText.copy(
                        fontSize = 11.sp,
                        color = FlowMeterColors.TextMuted
                    )
                )
            }
        }
    }
}

@Composable
fun TelemetryRowDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp)
            .height(1.dp)
            .background(Color(0x1438BDF8))
    )
}

@Composable
fun AlertInfoCard(
    modifier: Modifier = Modifier
) {
    FlowMeterCard(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(FlowMeterColors.DarkBlueAction),
                contentAlignment = Alignment.Center
            ) {
                com.arnav.flowmeter.components.icons.InfoCircleIcon(size = 20.dp, tint = FlowMeterColors.CyanAccent)
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Smart Alert Thresholds",
                    style = FlowMeterTypography.CardHeaderTitle.copy(fontSize = 14.sp)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "FlowMeter continuously monitors real-time telemetry for pressure drops, flow anomalies, and sensor calibration drifts.",
                    style = FlowMeterTypography.CardFooterText,
                    color = FlowMeterColors.TextSecondary
                )
            }
        }
    }
}

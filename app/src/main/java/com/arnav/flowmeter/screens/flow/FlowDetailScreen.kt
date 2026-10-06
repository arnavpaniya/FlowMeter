package com.arnav.flowmeter.screens.flow

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arnav.flowmeter.branding.colors.FlowMeterColors
import com.arnav.flowmeter.branding.typography.FlowMeterTypography
import com.arnav.flowmeter.components.buttons.CircularIconButton
import com.arnav.flowmeter.components.buttons.PrimaryActionButton
import com.arnav.flowmeter.components.cards.CircularFlowGauge
import com.arnav.flowmeter.components.cards.DetailMetricCard
import com.arnav.flowmeter.components.cards.EmptyChartCard
import com.arnav.flowmeter.components.cards.FlowMeterCard
import com.arnav.flowmeter.components.cards.TelemetryRegisterRow
import com.arnav.flowmeter.components.cards.TelemetryRowDivider
import com.arnav.flowmeter.components.icons.ChevronLeftIcon
import com.arnav.flowmeter.components.icons.ClockIcon
import com.arnav.flowmeter.components.icons.FlowWavesIcon
import com.arnav.flowmeter.components.icons.HistoryIcon
import com.arnav.flowmeter.components.icons.InfoSquareIcon
import com.arnav.flowmeter.components.icons.ShieldSecurityIcon
import com.arnav.flowmeter.components.icons.TrendingFlowIcon
import com.arnav.flowmeter.components.status.StatusChip
import com.arnav.flowmeter.components.status.TelemetryDataType
import com.arnav.flowmeter.components.status.TelemetryTagChip
import com.arnav.flowmeter.components.waves.HeaderAtmosphericWaves

@Composable
fun FlowDetailScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onViewTelemetryClick: () -> Unit = {}
) {
    BackHandler(onBack = onBackClick)
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(FlowMeterColors.BackgroundDark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp)
                .padding(bottom = 150.dp) // Clearance for floating navigation
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // 1. TOP HEADER WITH BACK BUTTON
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularIconButton(
                    icon = { ChevronLeftIcon(size = 18.dp, tint = FlowMeterColors.TextPrimary) },
                    size = 38.dp,
                    onClick = onBackClick
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Current Flow",
                            style = FlowMeterTypography.GreetingName.copy(fontSize = 22.sp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        TelemetryTagChip(type = TelemetryDataType.MEASURED)
                    }
                    Text(
                        text = "Real-time fluid dynamics & rate statistics",
                        style = FlowMeterTypography.GreetingSubtext
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 2. ATMOSPHERIC FLOWING WAVE
            HeaderAtmosphericWaves(
                modifier = Modifier.fillMaxWidth(),
                height = 28.dp
            )

            Spacer(modifier = Modifier.height(14.dp))

            // 3. HERO FLOW GAUGE CARD
            FlowMeterCard(
                modifier = Modifier.fillMaxWidth(),
                cornerRadius = 24.dp
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
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
                                FlowWavesIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent)
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Instantaneous Rate",
                                style = FlowMeterTypography.CardHeaderTitle
                            )
                        }

                        StatusChip(text = "Waiting for data")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    CircularFlowGauge(
                        size = 180.dp,
                        value = "—",
                        unit = "L/min"
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Waiting for device telemetry...",
                        style = FlowMeterTypography.CardFooterText.copy(
                            color = FlowMeterColors.TextSecondary,
                            fontSize = 12.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 4. FLOW TREND EMPTY CHART CARD
            EmptyChartCard(
                title = "Flow Rate Curve",
                subtitle = "Fluid velocity over time",
                emptyMessage = "No live flow data recorded",
                hint = "Connect your FlowMeter hardware to render the live flow curve",
                icon = { TrendingFlowIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) }
            )

            Spacer(modifier = Modifier.height(14.dp))

            // 5. FLOW STATISTICS (2x2 Grid)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                DetailMetricCard(
                    title = "Average Flow",
                    value = "—",
                    unit = "L/min",
                    subtitle = "Session mean",
                    icon = { HistoryIcon(size = 16.dp, tint = FlowMeterColors.CyanAccent) },
                    dataType = TelemetryDataType.CALCULATED,
                    modifier = Modifier.weight(1f)
                )

                DetailMetricCard(
                    title = "Peak Flow",
                    value = "—",
                    unit = "L/min",
                    subtitle = "Max observed",
                    icon = { TrendingFlowIcon(size = 16.dp, tint = FlowMeterColors.CyanAccent) },
                    dataType = TelemetryDataType.CALCULATED,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                DetailMetricCard(
                    title = "Minimum Flow",
                    value = "—",
                    unit = "L/min",
                    subtitle = "Low baseline",
                    icon = { FlowWavesIcon(size = 16.dp, tint = FlowMeterColors.CyanAccent) },
                    dataType = TelemetryDataType.CALCULATED,
                    modifier = Modifier.weight(1f)
                )

                DetailMetricCard(
                    title = "Active Session",
                    value = "—",
                    unit = "mins",
                    subtitle = "Flow duration",
                    icon = { ClockIcon(size = 16.dp, tint = FlowMeterColors.CyanAccent) },
                    dataType = TelemetryDataType.CALCULATED,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 6. READING QUALITY & SAMPLING INFORMATION CARD
            FlowMeterCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .clip(CircleShape)
                                    .background(FlowMeterColors.DarkBlueIconBg),
                                contentAlignment = Alignment.Center
                            ) {
                                ShieldSecurityIcon(size = 16.dp, tint = FlowMeterColors.CyanAccent)
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Reading Quality & Hardware",
                                style = FlowMeterTypography.CardHeaderTitle
                            )
                        }

                        TelemetryTagChip(type = TelemetryDataType.QUALITY)
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    TelemetryRegisterRow(
                        label = "Sampling Interval",
                        value = "100 ms",
                        tag = TelemetryDataType.DEVICE
                    )
                    TelemetryRowDivider()
                    TelemetryRegisterRow(
                        label = "Last Sample Timestamp",
                        value = "—",
                        tag = TelemetryDataType.QUALITY
                    )
                    TelemetryRowDivider()
                    TelemetryRegisterRow(
                        label = "Sensor Stream Integrity",
                        value = "Waiting for device",
                        tag = TelemetryDataType.QUALITY
                    )
                    TelemetryRowDivider()
                    TelemetryRegisterRow(
                        label = "Calculation Mode",
                        value = "Continuous Trapezoidal Integration",
                        tag = TelemetryDataType.CALCULATED
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 7. DEEP TELEMETRY ACCESS BUTTON
            PrimaryActionButton(
                text = "View Raw Telemetry Registers",
                onClick = onViewTelemetryClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

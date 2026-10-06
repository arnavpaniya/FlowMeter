package com.arnav.flowmeter.screens.volume

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
import com.arnav.flowmeter.components.cards.DetailMetricCard
import com.arnav.flowmeter.components.cards.EmptyChartCard
import com.arnav.flowmeter.components.cards.FlowMeterCard
import com.arnav.flowmeter.components.cards.TelemetryRegisterRow
import com.arnav.flowmeter.components.cards.TelemetryRowDivider
import com.arnav.flowmeter.components.icons.CalendarIcon
import com.arnav.flowmeter.components.icons.ChevronLeftIcon
import com.arnav.flowmeter.components.icons.ClockIcon
import com.arnav.flowmeter.components.icons.HistoryIcon
import com.arnav.flowmeter.components.icons.WaterDropIcon
import com.arnav.flowmeter.components.status.TelemetryDataType
import com.arnav.flowmeter.components.status.TelemetryTagChip
import com.arnav.flowmeter.components.waves.HeaderAtmosphericWaves

@Composable
fun VolumeDetailScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onViewSessionsClick: () -> Unit = {},
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
                            text = "Water Usage & Volume",
                            style = FlowMeterTypography.GreetingName.copy(fontSize = 22.sp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        TelemetryTagChip(type = TelemetryDataType.CALCULATED)
                    }
                    Text(
                        text = "Cumulative volume & consumption breakdown",
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

            // 3. HERO TOTAL VOLUME CARD
            FlowMeterCard(
                modifier = Modifier.fillMaxWidth(),
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
                                WaterDropIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent)
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Total Cumulative Volume",
                                style = FlowMeterTypography.CardHeaderTitle
                            )
                        }

                        TelemetryTagChip(type = TelemetryDataType.CALCULATED)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "—",
                            style = FlowMeterTypography.MetricPlaceholderLarge.copy(fontSize = 44.sp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Litres",
                            style = FlowMeterTypography.UnitLabel.copy(fontSize = 18.sp),
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Total volume measured across all recorded sessions",
                        style = FlowMeterTypography.CardFooterText.copy(
                            color = FlowMeterColors.TextSecondary,
                            fontSize = 12.sp
                        ),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 4. PERIODIC BREAKDOWN (2x2 Grid)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                DetailMetricCard(
                    title = "Today",
                    value = "—",
                    unit = "L",
                    subtitle = "Since midnight",
                    icon = { WaterDropIcon(size = 16.dp, tint = FlowMeterColors.CyanAccent) },
                    dataType = TelemetryDataType.CALCULATED,
                    modifier = Modifier.weight(1f)
                )

                DetailMetricCard(
                    title = "This Week",
                    value = "—",
                    unit = "L",
                    subtitle = "Rolling 7 days",
                    icon = { CalendarIcon(size = 16.dp, tint = FlowMeterColors.CyanAccent) },
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
                    title = "This Month",
                    value = "—",
                    unit = "L",
                    subtitle = "Current cycle",
                    icon = { HistoryIcon(size = 16.dp, tint = FlowMeterColors.CyanAccent) },
                    dataType = TelemetryDataType.CALCULATED,
                    modifier = Modifier.weight(1f)
                )

                DetailMetricCard(
                    title = "Year to Date",
                    value = "—",
                    unit = "L",
                    subtitle = "Annual total",
                    icon = { ClockIcon(size = 16.dp, tint = FlowMeterColors.CyanAccent) },
                    dataType = TelemetryDataType.CALCULATED,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 5. USAGE TREND EMPTY CHART
            EmptyChartCard(
                title = "Consumption Trend",
                subtitle = "Volume accumulation over time",
                emptyMessage = "No historical consumption recorded",
                hint = "Volume telemetry will accumulate as fluid flows through the sensor",
                icon = { WaterDropIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) }
            )

            Spacer(modifier = Modifier.height(14.dp))

            // 6. VOLUME CALCULATION SPECIFICATIONS CARD
            FlowMeterCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Integration & Precision Specifications",
                        style = FlowMeterTypography.CardHeaderTitle
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    TelemetryRegisterRow(
                        label = "Integration Method",
                        value = "Discrete Riemann Pulse Summation",
                        tag = TelemetryDataType.CALCULATED
                    )
                    TelemetryRowDivider()
                    TelemetryRegisterRow(
                        label = "Calibration Factor (K-Factor)",
                        value = "— pulses/L",
                        tag = TelemetryDataType.DEVICE
                    )
                    TelemetryRowDivider()
                    TelemetryRegisterRow(
                        label = "Meter Resolution",
                        value = "0.01 Litres",
                        tag = TelemetryDataType.DEVICE
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 7. ACTIONS
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                PrimaryActionButton(
                    text = "View Sessions",
                    onClick = onViewSessionsClick,
                    modifier = Modifier.weight(1f)
                )

                PrimaryActionButton(
                    text = "Raw Telemetry",
                    onClick = onViewTelemetryClick,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

package com.arnav.flowmeter.screens.session

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
import com.arnav.flowmeter.components.cards.FlowMeterCard
import com.arnav.flowmeter.components.cards.TelemetryRegisterRow
import com.arnav.flowmeter.components.cards.TelemetryRowDivider
import com.arnav.flowmeter.components.icons.ChevronLeftIcon
import com.arnav.flowmeter.components.icons.ClockIcon
import com.arnav.flowmeter.components.icons.FlowWavesIcon
import com.arnav.flowmeter.components.icons.HistoryIcon
import com.arnav.flowmeter.components.icons.WaterDropIcon
import com.arnav.flowmeter.components.status.TelemetryDataType
import com.arnav.flowmeter.components.status.TelemetryTagChip
import com.arnav.flowmeter.components.waves.HeaderAtmosphericWaves

@Composable
fun SessionDetailScreen(
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
                            text = "Flow Sessions",
                            style = FlowMeterTypography.GreetingName.copy(fontSize = 22.sp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        TelemetryTagChip(type = TelemetryDataType.CALCULATED)
                    }
                    Text(
                        text = "Workout-style water event logs & session telemetry",
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

            // 3. SESSION SUMMARY (3 Metric Cards)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                DetailMetricCard(
                    title = "Total Sessions",
                    value = "0",
                    subtitle = "All-time",
                    icon = { HistoryIcon(size = 16.dp, tint = FlowMeterColors.CyanAccent) },
                    modifier = Modifier.weight(1f)
                )

                DetailMetricCard(
                    title = "Session Vol",
                    value = "—",
                    unit = "L",
                    subtitle = "Sum total",
                    icon = { WaterDropIcon(size = 16.dp, tint = FlowMeterColors.CyanAccent) },
                    modifier = Modifier.weight(1f)
                )

                DetailMetricCard(
                    title = "Avg Duration",
                    value = "—",
                    unit = "m",
                    subtitle = "Per event",
                    icon = { ClockIcon(size = 16.dp, tint = FlowMeterColors.CyanAccent) },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 4. MAIN RECORDED SESSIONS LIST CARD (Clean Empty State)
            FlowMeterCard(
                modifier = Modifier.fillMaxWidth(),
                cornerRadius = 24.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp, horizontal = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(FlowMeterColors.DarkBlueIconBg),
                        contentAlignment = Alignment.Center
                    ) {
                        HistoryIcon(size = 28.dp, tint = FlowMeterColors.CyanAccent)
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "No recorded sessions",
                        style = FlowMeterTypography.CardHeaderTitle.copy(fontSize = 16.sp)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "FlowMeter automatically detects and segments water draw events into distinct activity sessions when your device is active.",
                        style = FlowMeterTypography.CardFooterText,
                        fontSize = 12.sp,
                        color = FlowMeterColors.TextSecondary,
                        lineHeight = 16.sp,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 5. SESSION TELEMETRY SCHEMA SPECIFICATION
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
                                FlowWavesIcon(size = 16.dp, tint = FlowMeterColors.CyanAccent)
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Session Event Schema",
                                style = FlowMeterTypography.CardHeaderTitle
                            )
                        }

                        TelemetryTagChip(type = TelemetryDataType.CALCULATED)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    TelemetryRegisterRow(label = "Session Start / End Timestamp", value = "ISO 8601 UTC", tag = TelemetryDataType.DEVICE)
                    TelemetryRowDivider()
                    TelemetryRegisterRow(label = "Session Duration", value = "—", unit = "seconds", tag = TelemetryDataType.CALCULATED)
                    TelemetryRowDivider()
                    TelemetryRegisterRow(label = "Cumulative Session Volume", value = "—", unit = "Litres", tag = TelemetryDataType.CALCULATED)
                    TelemetryRowDivider()
                    TelemetryRegisterRow(label = "Average Flow Rate", value = "—", unit = "L/min", tag = TelemetryDataType.CALCULATED)
                    TelemetryRowDivider()
                    TelemetryRegisterRow(label = "Peak Flow Rate", value = "—", unit = "L/min", tag = TelemetryDataType.CALCULATED)
                    TelemetryRowDivider()
                    TelemetryRegisterRow(label = "Minimum Flow Rate", value = "—", unit = "L/min", tag = TelemetryDataType.CALCULATED)
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 6. ACTION
            PrimaryActionButton(
                text = "View Raw Telemetry",
                onClick = onViewTelemetryClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

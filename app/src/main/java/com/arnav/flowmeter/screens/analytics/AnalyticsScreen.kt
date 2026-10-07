package com.arnav.flowmeter.screens.analytics

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arnav.flowmeter.branding.colors.FlowMeterColors
import com.arnav.flowmeter.branding.logo.FlowMeterLogo
import com.arnav.flowmeter.branding.typography.FlowMeterTypography
import com.arnav.flowmeter.components.buttons.CircularIconButton
import com.arnav.flowmeter.components.buttons.TimeRangeSelector
import com.arnav.flowmeter.components.cards.DetailMetricCard
import com.arnav.flowmeter.components.cards.EmptyChartCard
import com.arnav.flowmeter.components.cards.FlowMeterCard
import com.arnav.flowmeter.components.cards.UsageDistributionCard
import com.arnav.flowmeter.components.cards.WaterUsageChartCard
import com.arnav.flowmeter.components.icons.AnalyticsNavIcon
import com.arnav.flowmeter.components.icons.BellIcon
import com.arnav.flowmeter.components.icons.ChevronRightIcon
import com.arnav.flowmeter.components.icons.ClockIcon
import com.arnav.flowmeter.components.icons.FlowWavesIcon
import com.arnav.flowmeter.components.icons.HistoryIcon
import com.arnav.flowmeter.components.icons.SettingsGearIcon
import com.arnav.flowmeter.components.icons.TrendingFlowIcon
import com.arnav.flowmeter.components.icons.WaterDropIcon
import com.arnav.flowmeter.components.status.TelemetryDataType
import com.arnav.flowmeter.components.status.TelemetryTagChip
import com.arnav.flowmeter.components.waves.HeaderAtmosphericWaves

@Composable
fun AnalyticsScreen(
    modifier: Modifier = Modifier,
    onNotificationsClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onFlowDetailClick: () -> Unit = {},
    onVolumeDetailClick: () -> Unit = {},
    onSessionDetailClick: () -> Unit = {},
    onTelemetryClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    var selectedTimeRangeIndex by remember { mutableIntStateOf(0) }
    val timeRangeOptions = listOf("7 days", "30 days", "3 months", "1 year")

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
                .padding(bottom = 150.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // 1. TOP HEADER: Brand Logo + Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FlowMeterLogo(
                        size = 36.dp,
                        showContainer = true
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "FlowMeter",
                        style = FlowMeterTypography.AppBrandTitle
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularIconButton(
                        icon = {
                            BellIcon(
                                size = 18.dp,
                                tint = FlowMeterColors.TextSecondary,
                                hasNotificationDot = true
                            )
                        },
                        size = 38.dp,
                        onClick = onNotificationsClick
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    CircularIconButton(
                        icon = { SettingsGearIcon(size = 18.dp, tint = FlowMeterColors.TextSecondary) },
                        size = 38.dp,
                        onClick = onSettingsClick
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 2. MAIN TITLE
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Analytics",
                    style = FlowMeterTypography.GreetingName
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Long-term trends, session statistics & patterns",
                    style = FlowMeterTypography.GreetingSubtext
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            HeaderAtmosphericWaves(
                modifier = Modifier.fillMaxWidth(),
                height = 28.dp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // SECTION: TREND ANALYSIS
            Text(
                text = "TREND ANALYSIS",
                style = FlowMeterTypography.CardSectionTitle,
                color = FlowMeterColors.TextSecondary
            )
            Spacer(modifier = Modifier.height(10.dp))

            TimeRangeSelector(
                options = timeRangeOptions,
                selectedIndex = selectedTimeRangeIndex,
                onSelect = { selectedTimeRangeIndex = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            WaterUsageChartCard(
                dateRangeText = when (selectedTimeRangeIndex) {
                    0 -> "Last 7 days"
                    1 -> "Last 30 days"
                    2 -> "Last 3 months"
                    else -> "Last 1 year"
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            EmptyChartCard(
                title = "Flow Velocity Trends",
                subtitle = "Historical flow rate envelope",
                emptyMessage = "No flow velocity history",
                hint = "Connect your device to build your long-term flow trends",
                icon = { TrendingFlowIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // SECTION: PERFORMANCE STATISTICS
            Text(
                text = "PERFORMANCE STATISTICS",
                style = FlowMeterTypography.CardSectionTitle,
                color = FlowMeterColors.TextSecondary
            )
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                DetailMetricCard(
                    title = "Avg Daily Usage",
                    value = "—",
                    unit = "L/day",
                    subtitle = "Historical mean",
                    icon = { WaterDropIcon(size = 16.dp, tint = FlowMeterColors.CyanAccent) },
                    dataType = TelemetryDataType.CALCULATED,
                    modifier = Modifier.weight(1f)
                )

                DetailMetricCard(
                    title = "Peak Flow Observed",
                    value = "—",
                    unit = "L/min",
                    subtitle = "All-time max",
                    icon = { TrendingFlowIcon(size = 16.dp, tint = FlowMeterColors.CyanAccent) },
                    dataType = TelemetryDataType.CALCULATED,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                DetailMetricCard(
                    title = "Total Sessions",
                    value = "0",
                    subtitle = "Recorded events",
                    icon = { HistoryIcon(size = 16.dp, tint = FlowMeterColors.CyanAccent) },
                    dataType = TelemetryDataType.CALCULATED,
                    modifier = Modifier.weight(1f)
                )

                DetailMetricCard(
                    title = "Avg Event Length",
                    value = "—",
                    unit = "mins",
                    subtitle = "Flow duration",
                    icon = { ClockIcon(size = 16.dp, tint = FlowMeterColors.CyanAccent) },
                    dataType = TelemetryDataType.CALCULATED,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            UsageDistributionCard()

            Spacer(modifier = Modifier.height(24.dp))

            // SECTION: ADVANCED LOGS
            Text(
                text = "ADVANCED LOGS",
                style = FlowMeterTypography.CardSectionTitle,
                color = FlowMeterColors.TextSecondary
            )
            Spacer(modifier = Modifier.height(10.dp))

            FlowMeterCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(color = FlowMeterColors.CyanAccent.copy(alpha = 0.2f)),
                        onClick = onTelemetryClick
                    ),
                cornerRadius = 20.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(FlowMeterColors.DarkBlueIconBg),
                            contentAlignment = Alignment.Center
                        ) {
                            AnalyticsNavIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Raw Telemetry & Logs",
                                    style = FlowMeterTypography.CardHeaderTitle.copy(fontSize = 14.sp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                TelemetryTagChip(type = TelemetryDataType.QUALITY)
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Export packet streams and examine raw sensor counts",
                                style = FlowMeterTypography.CardFooterText.copy(fontSize = 11.sp),
                                color = FlowMeterColors.TextSecondary
                            )
                        }
                    }

                    ChevronRightIcon(size = 16.dp, tint = FlowMeterColors.CyanAccent)
                }
            }
        }
    }
}

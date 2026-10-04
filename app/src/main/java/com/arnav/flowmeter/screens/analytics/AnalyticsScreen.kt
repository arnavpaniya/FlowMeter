package com.arnav.flowmeter.screens.analytics

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arnav.flowmeter.branding.colors.FlowMeterColors
import com.arnav.flowmeter.branding.logo.FlowMeterLogo
import com.arnav.flowmeter.branding.typography.FlowMeterTypography
import com.arnav.flowmeter.components.buttons.CircularIconButton
import com.arnav.flowmeter.components.buttons.TimeRangeSelector
import com.arnav.flowmeter.components.cards.AnalyticsMetricCard
import com.arnav.flowmeter.components.cards.TranslucentWaveRibbons
import com.arnav.flowmeter.components.cards.UsageDistributionCard
import com.arnav.flowmeter.components.cards.WaterUsageChartCard
import com.arnav.flowmeter.components.icons.AnalyticsNavIcon
import com.arnav.flowmeter.components.icons.BellIcon
import com.arnav.flowmeter.components.icons.HistoryIcon
import com.arnav.flowmeter.components.icons.SettingsGearIcon
import com.arnav.flowmeter.components.icons.UpArrowIcon
import com.arnav.flowmeter.components.icons.WaterDropIcon

@Composable
fun AnalyticsScreen(
    modifier: Modifier = Modifier,
    onNotificationsClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    var selectedTimeRangeIndex by remember { mutableStateOf(0) }
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
                .padding(bottom = 100.dp) // Clearance for floating bottom navbar
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // TOP HEADER: Brand Logo + Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FlowMeterLogo(
                        size = 34.dp,
                        showContainer = false
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

            Spacer(modifier = Modifier.height(18.dp))

            // MAIN TITLE WITH AMBIENT WATER WAVES
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Background translucent flowing wave ribbon with particles
                com.arnav.flowmeter.components.waves.EnhancedLuminousWaves(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterEnd),
                    height = 80.dp,
                    showParticles = true
                )

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Analytics",
                        style = FlowMeterTypography.GreetingName
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Explore your water usage patterns",
                        style = FlowMeterTypography.GreetingSubtext
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // TIME RANGE SELECTOR PILLS
            TimeRangeSelector(
                options = timeRangeOptions,
                selectedIndex = selectedTimeRangeIndex,
                onSelect = { selectedTimeRangeIndex = it }
            )

            Spacer(modifier = Modifier.height(18.dp))

            // MAIN WATER USAGE CHART CARD
            WaterUsageChartCard(
                dateRangeText = when (selectedTimeRangeIndex) {
                    0 -> "Last 7 days"
                    1 -> "Last 30 days"
                    2 -> "Last 3 months"
                    else -> "Last 1 year"
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 2 x 2 SUMMARY METRICS GRID
            // Row 1: Total Usage & Average Daily
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                AnalyticsMetricCard(
                    title = "Total Usage",
                    unit = "Litres",
                    icon = { WaterDropIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                    modifier = Modifier.weight(1f)
                )

                AnalyticsMetricCard(
                    title = "Average Daily",
                    unit = "Litres/day",
                    icon = { HistoryIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Row 2: Peak Usage & Lowest Usage
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                AnalyticsMetricCard(
                    title = "Peak Usage",
                    unit = "L/min",
                    icon = { UpArrowIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                    modifier = Modifier.weight(1f)
                )

                AnalyticsMetricCard(
                    title = "Lowest Usage",
                    unit = "L/min",
                    icon = { AnalyticsNavIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // USAGE DISTRIBUTION CARD
            UsageDistributionCard(
                selectedDate = "Today"
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

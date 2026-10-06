package com.arnav.flowmeter.screens.home

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arnav.flowmeter.branding.colors.FlowMeterColors
import com.arnav.flowmeter.branding.logo.FlowMeterLogo
import com.arnav.flowmeter.branding.typography.FlowMeterTypography
import com.arnav.flowmeter.components.buttons.CircularIconButton
import com.arnav.flowmeter.components.cards.CurrentFlowCard
import com.arnav.flowmeter.components.cards.DeviceConnectionCard
import com.arnav.flowmeter.components.cards.FlowMeterCard
import com.arnav.flowmeter.components.cards.SummaryMetricCard
import com.arnav.flowmeter.components.cards.TotalRuntimeCard
import com.arnav.flowmeter.components.icons.BellIcon
import com.arnav.flowmeter.components.icons.ChevronRightIcon
import com.arnav.flowmeter.components.icons.HistoryIcon
import com.arnav.flowmeter.components.icons.SettingsGearIcon
import com.arnav.flowmeter.components.icons.TrendingFlowIcon
import com.arnav.flowmeter.components.icons.WaterDropIcon
import com.arnav.flowmeter.components.status.TelemetryDataType
import com.arnav.flowmeter.components.status.TelemetryTagChip
import com.arnav.flowmeter.components.waves.HeaderAtmosphericWaves

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNotificationsClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onFlowDetailClick: () -> Unit = {},
    onVolumeDetailClick: () -> Unit = {},
    onSessionDetailClick: () -> Unit = {},
    onDeviceHealthClick: () -> Unit = {},
    onTelemetryClick: () -> Unit = {}
) {
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
                .padding(bottom = 150.dp) // Generous clearance for floating bottom navigation bar
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // 1. TOP HEADER: Logo + Wordmark & Action Controls (Clear, unobstructed)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // FlowMeter Logo + Brand Title
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

                // Right Actions: Notification Bell & Settings Gear
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularIconButton(
                        icon = { BellIcon(size = 18.dp, tint = FlowMeterColors.TextSecondary) },
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

            // 2. GREETING / TITLE SECTION
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Good morning,",
                    style = FlowMeterTypography.GreetingSmall
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Arnav 👋",
                    style = FlowMeterTypography.GreetingName
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Glanceable monitoring overview & telemetry",
                    style = FlowMeterTypography.GreetingSubtext
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 3. ATMOSPHERIC FLOWING WAVE (Elegantly placed between greeting and content)
            HeaderAtmosphericWaves(
                modifier = Modifier.fillMaxWidth(),
                height = 30.dp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 4. MAIN CURRENT FLOW CARD (Glanceable Gauge + Tap to Flow Detail)
            CurrentFlowCard(
                onClick = onFlowDetailClick
            )

            Spacer(modifier = Modifier.height(14.dp))

            // 5. SUMMARY METRIC CARDS (Today's Usage & Average Flow with tap-to-detail)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                SummaryMetricCard(
                    title = "Today's Usage",
                    unit = "Litres",
                    icon = { WaterDropIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                    dataType = TelemetryDataType.CALCULATED,
                    subtitle = "Tap for breakdown",
                    onClick = onVolumeDetailClick,
                    modifier = Modifier.weight(1f)
                )

                SummaryMetricCard(
                    title = "Average Flow",
                    unit = "L/min",
                    icon = { HistoryIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                    dataType = TelemetryDataType.CALCULATED,
                    subtitle = "Tap for stats",
                    onClick = onFlowDetailClick,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 6. TOTAL RUNTIME & SESSIONS CARD (Tap to Session Detail)
            TotalRuntimeCard(
                onClick = onSessionDetailClick
            )

            Spacer(modifier = Modifier.height(14.dp))

            // 7. DEVICE CONNECTION CARD (Tap to Device Health)
            DeviceConnectionCard(
                onConnectClick = onDeviceHealthClick
            )

            Spacer(modifier = Modifier.height(14.dp))

            // 8. TELEMETRY & RESEARCH QUICK ACCESS CARD
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
                            TrendingFlowIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Deep Telemetry & Registers",
                                    style = FlowMeterTypography.CardHeaderTitle.copy(fontSize = 14.sp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                TelemetryTagChip(type = TelemetryDataType.MEASURED)
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Raw pulse counts, data quality & hardware registers",
                                style = FlowMeterTypography.CardFooterText.copy(fontSize = 11.sp),
                                color = FlowMeterColors.TextSecondary
                            )
                        }
                    }

                    ChevronRightIcon(size = 14.dp, tint = FlowMeterColors.TextSecondary)
                }
            }
        }
    }
}

package com.arnav.flowmeter.screens.alerts

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
import com.arnav.flowmeter.components.cards.AlertInfoCard
import com.arnav.flowmeter.components.cards.FlowMeterCard
import com.arnav.flowmeter.components.cards.MainAlertStatusCard
import com.arnav.flowmeter.components.cards.RecentAlertsCard
import com.arnav.flowmeter.components.cards.TelemetryRegisterRow
import com.arnav.flowmeter.components.cards.TelemetryRowDivider
import com.arnav.flowmeter.components.icons.BellIcon
import com.arnav.flowmeter.components.icons.ChevronRightIcon
import com.arnav.flowmeter.components.icons.DeviceSensorIcon
import com.arnav.flowmeter.components.icons.HistoryIcon
import com.arnav.flowmeter.components.icons.SettingsGearIcon
import com.arnav.flowmeter.components.icons.ShieldCheckIcon
import com.arnav.flowmeter.components.icons.ShieldSecurityIcon
import com.arnav.flowmeter.components.icons.ThermometerIcon
import com.arnav.flowmeter.components.icons.TrendingFlowIcon
import com.arnav.flowmeter.components.icons.WaterDropIcon
import com.arnav.flowmeter.components.icons.WifiIcon
import com.arnav.flowmeter.components.status.TelemetryDataType
import com.arnav.flowmeter.components.status.TelemetryTagChip
import com.arnav.flowmeter.components.waves.HeaderAtmosphericWaves

@Composable
fun AlertsScreen(
    modifier: Modifier = Modifier,
    onNotificationsClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onViewTelemetryClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    var selectedFilterIndex by remember { mutableIntStateOf(0) }
    val filterOptions = listOf("Active", "History", "Diagnostics")

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
                .padding(bottom = 150.dp) // Generous clearance for floating bottom navigation
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // 1. TOP HEADER: Brand Logo + Action Buttons (Unobstructed)
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
                                hasNotificationDot = false
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

            // 2. MAIN TITLE
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Alerts & Diagnostics",
                    style = FlowMeterTypography.GreetingName
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "System health, anomaly detection & event logs",
                    style = FlowMeterTypography.GreetingSubtext
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 3. ATMOSPHERIC FLOWING WAVE
            HeaderAtmosphericWaves(
                modifier = Modifier.fillMaxWidth(),
                height = 30.dp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 4. FILTER TABS: Active, History, Diagnostics
            TimeRangeSelector(
                options = filterOptions,
                selectedIndex = selectedFilterIndex,
                onSelect = { selectedFilterIndex = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            when (selectedFilterIndex) {
                0 -> {
                    // TAB 0: ACTIVE ALERTS
                    MainAlertStatusCard()

                    Spacer(modifier = Modifier.height(16.dp))

                    RecentAlertsCard(
                        onViewAllClick = { selectedFilterIndex = 2 }
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    AlertInfoCard()
                }

                1 -> {
                    // TAB 1: ALERT HISTORY
                    FlowMeterCard(
                        modifier = Modifier.fillMaxWidth(),
                        cornerRadius = 24.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp, horizontal = 16.dp),
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
                                text = "No historical alerts",
                                style = FlowMeterTypography.CardHeaderTitle.copy(fontSize = 16.sp)
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "Past alert events, abnormal flow durations, and hardware notices will be archived here.",
                                style = FlowMeterTypography.CardFooterText,
                                fontSize = 12.sp,
                                color = FlowMeterColors.TextSecondary,
                                lineHeight = 16.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    AlertInfoCard()
                }

                2 -> {
                    // TAB 2: SYSTEM DIAGNOSTICS & DATA QUALITY
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
                                            .size(32.dp)
                                            .clip(CircleShape)
                                            .background(FlowMeterColors.DarkBlueIconBg),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        ShieldCheckIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent)
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = "Hardware Transducer Health",
                                        style = FlowMeterTypography.CardHeaderTitle
                                    )
                                }
                                TelemetryTagChip(type = TelemetryDataType.DIAGNOSTIC)
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            TelemetryRegisterRow(label = "Pulse Sensor Circuit", value = "Nominal (0 Faults)", tag = TelemetryDataType.DIAGNOSTIC)
                            TelemetryRowDivider()
                            TelemetryRegisterRow(label = "Thermistor Range Check", value = "Within Limits", tag = TelemetryDataType.DIAGNOSTIC)
                            TelemetryRowDivider()
                            TelemetryRegisterRow(label = "Sensor Zero-Drift", value = "0.00% (Calibrated)", tag = TelemetryDataType.CALCULATED)
                            TelemetryRowDivider()
                            TelemetryRegisterRow(label = "Pipe Dry/Empty Warning", value = "Inactive", tag = TelemetryDataType.DIAGNOSTIC)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

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
                                            .size(32.dp)
                                            .clip(CircleShape)
                                            .background(FlowMeterColors.DarkBlueIconBg),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        WifiIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent)
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = "Transmission Link Quality",
                                        style = FlowMeterTypography.CardHeaderTitle
                                    )
                                }
                                TelemetryTagChip(type = TelemetryDataType.QUALITY)
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            TelemetryRegisterRow(label = "CRC16 Error Rate", value = "0.00%", tag = TelemetryDataType.QUALITY)
                            TelemetryRowDivider()
                            TelemetryRegisterRow(label = "Missing Sequence Packets", value = "0", tag = TelemetryDataType.QUALITY)
                            TelemetryRowDivider()
                            TelemetryRegisterRow(label = "Stale Telemetry Timeout", value = "Inactive", tag = TelemetryDataType.QUALITY)
                            TelemetryRowDivider()
                            TelemetryRegisterRow(label = "BLE Buffer Congestion", value = "None", tag = TelemetryDataType.QUALITY)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Deep Telemetry Link
                    FlowMeterCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = ripple(color = FlowMeterColors.CyanAccent.copy(alpha = 0.2f)),
                                onClick = onViewTelemetryClick
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
                                    ShieldSecurityIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent)
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = "Raw Telemetry & Register Logs",
                                        style = FlowMeterTypography.CardHeaderTitle.copy(fontSize = 14.sp)
                                    )
                                    Text(
                                        text = "Examine raw telemetry registers and diagnostics",
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
    }
}

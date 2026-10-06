package com.arnav.flowmeter.screens.device

import androidx.activity.compose.BackHandler
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
import com.arnav.flowmeter.branding.typography.FlowMeterTypography
import com.arnav.flowmeter.components.buttons.CircularIconButton
import com.arnav.flowmeter.components.buttons.PrimaryActionButton
import com.arnav.flowmeter.components.cards.DeviceConnectionCard
import com.arnav.flowmeter.components.cards.FlowMeterCard
import com.arnav.flowmeter.components.cards.TelemetryRegisterRow
import com.arnav.flowmeter.components.cards.TelemetryRowDivider
import com.arnav.flowmeter.components.icons.ChevronLeftIcon
import com.arnav.flowmeter.components.icons.ChevronRightIcon
import com.arnav.flowmeter.components.icons.DeviceSensorIcon
import com.arnav.flowmeter.components.icons.HistoryIcon
import com.arnav.flowmeter.components.icons.InfoSquareIcon
import com.arnav.flowmeter.components.icons.ShieldSecurityIcon
import com.arnav.flowmeter.components.icons.WifiIcon
import com.arnav.flowmeter.components.status.TelemetryDataType
import com.arnav.flowmeter.components.status.TelemetryTagChip
import com.arnav.flowmeter.components.waves.HeaderAtmosphericWaves

@Composable
fun DeviceScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onConnectClick: () -> Unit = {},
    onViewAllDataClick: () -> Unit = {}
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
                .padding(bottom = 150.dp) // Clearance for floating bottom navigation
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
                            text = "Device Health",
                            style = FlowMeterTypography.GreetingName.copy(fontSize = 22.sp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        TelemetryTagChip(type = TelemetryDataType.DEVICE)
                    }
                    Text(
                        text = "Hardware state, sensor health & link quality",
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

            // 3. MAIN DEVICE CONNECTION CARD
            DeviceConnectionCard(
                modifier = Modifier.fillMaxWidth(),
                onConnectClick = onConnectClick
            )

            Spacer(modifier = Modifier.height(14.dp))

            // 4. DEVICE HARDWARE SPECIFICATIONS
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
                                InfoSquareIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent)
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Device System Information",
                                style = FlowMeterTypography.CardHeaderTitle
                            )
                        }

                        TelemetryTagChip(type = TelemetryDataType.DEVICE)
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    TelemetryRegisterRow(label = "Hardware Model", value = "FlowMeter Core-1", tag = TelemetryDataType.DEVICE)
                    TelemetryRowDivider()
                    TelemetryRegisterRow(label = "Device UUID", value = "—", tag = TelemetryDataType.DEVICE)
                    TelemetryRowDivider()
                    TelemetryRegisterRow(label = "Firmware Version", value = "—", tag = TelemetryDataType.DEVICE)
                    TelemetryRowDivider()
                    TelemetryRegisterRow(label = "System Uptime", value = "—", tag = TelemetryDataType.DEVICE)
                    TelemetryRowDivider()
                    TelemetryRegisterRow(label = "Battery Level", value = "—", unit = "%", tag = TelemetryDataType.DEVICE)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 5. SENSOR TRANSDUCER HEALTH STATUS
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
                                DeviceSensorIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent)
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Sensor Transducers",
                                style = FlowMeterTypography.CardHeaderTitle
                            )
                        }

                        TelemetryTagChip(type = TelemetryDataType.MEASURED)
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    TelemetryRegisterRow(label = "Flow Sensor (Hall/Pulse)", value = "Waiting for connection", tag = TelemetryDataType.MEASURED)
                    TelemetryRowDivider()
                    TelemetryRegisterRow(label = "Fluid Temperature Sensor", value = "—", unit = "°C", tag = TelemetryDataType.MEASURED)
                    TelemetryRowDivider()
                    TelemetryRegisterRow(label = "Line Pressure Transducer", value = "—", unit = "bar", tag = TelemetryDataType.MEASURED)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 6. CONNECTIVITY & LINK DIAGNOSTICS
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
                                text = "Wireless Link Quality",
                                style = FlowMeterTypography.CardHeaderTitle
                            )
                        }

                        TelemetryTagChip(type = TelemetryDataType.QUALITY)
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    TelemetryRegisterRow(label = "BLE Signal (RSSI)", value = "—", unit = "dBm", tag = TelemetryDataType.QUALITY)
                    TelemetryRowDivider()
                    TelemetryRegisterRow(label = "Last Valid Handshake", value = "—", tag = TelemetryDataType.QUALITY)
                    TelemetryRowDivider()
                    TelemetryRegisterRow(label = "Packet Error Rate", value = "0.0%", tag = TelemetryDataType.QUALITY)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 7. VIEW RAW TELEMETRY & REGISTERS ACTION
            FlowMeterCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(color = FlowMeterColors.CyanAccent.copy(alpha = 0.2f)),
                        onClick = onViewAllDataClick
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
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(FlowMeterColors.DarkBlueIconBg),
                            contentAlignment = Alignment.Center
                        ) {
                            HistoryIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Telemetry & Research Registers",
                                style = FlowMeterTypography.CardHeaderTitle.copy(fontSize = 14.sp)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Inspect raw sensor pulses, registers & packet logs",
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

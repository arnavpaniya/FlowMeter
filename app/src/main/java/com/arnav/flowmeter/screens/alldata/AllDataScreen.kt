package com.arnav.flowmeter.screens.alldata

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arnav.flowmeter.branding.colors.FlowMeterColors
import com.arnav.flowmeter.branding.typography.FlowMeterTypography
import com.arnav.flowmeter.components.buttons.CircularIconButton
import com.arnav.flowmeter.components.buttons.PrimaryActionButton
import com.arnav.flowmeter.components.cards.FlowMeterCard
import com.arnav.flowmeter.components.cards.TelemetryRegisterRow
import com.arnav.flowmeter.components.cards.TelemetryRowDivider
import com.arnav.flowmeter.components.icons.ChevronDownIcon
import com.arnav.flowmeter.components.icons.ChevronLeftIcon
import com.arnav.flowmeter.components.icons.ChevronUpIcon
import com.arnav.flowmeter.components.icons.ClockIcon
import com.arnav.flowmeter.components.icons.CloseIcon
import com.arnav.flowmeter.components.icons.DeviceSensorIcon
import com.arnav.flowmeter.components.icons.DocumentTextIcon
import com.arnav.flowmeter.components.icons.DownloadTrayIcon
import com.arnav.flowmeter.components.icons.FlowWavesIcon
import com.arnav.flowmeter.components.icons.HistoryIcon
import com.arnav.flowmeter.components.icons.ShieldSecurityIcon
import com.arnav.flowmeter.components.icons.WaterDropIcon
import com.arnav.flowmeter.components.icons.WifiIcon
import com.arnav.flowmeter.components.status.TelemetryDataType
import com.arnav.flowmeter.components.status.TelemetryTagChip
import com.arnav.flowmeter.components.waves.HeaderAtmosphericWaves
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllDataScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
) {
    BackHandler(onBack = onBackClick)
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showExportSheet by remember { mutableStateOf(false) }

    // Expandable section states
    var isFlowExpanded by remember { mutableStateOf(true) }
    var isVolumeExpanded by remember { mutableStateOf(true) }
    var isRawSensorExpanded by remember { mutableStateOf(true) }
    var isDeviceExpanded by remember { mutableStateOf(true) }
    var isConnectivityExpanded by remember { mutableStateOf(true) }
    var isDataQualityExpanded by remember { mutableStateOf(true) }
    var isDiagnosticsExpanded by remember { mutableStateOf(true) }

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

            // 1. TOP HEADER WITH BACK BUTTON & EXPORT ACTION
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CircularIconButton(
                        icon = { ChevronLeftIcon(size = 18.dp, tint = FlowMeterColors.TextPrimary) },
                        size = 38.dp,
                        onClick = onBackClick
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Telemetry & Research",
                            style = FlowMeterTypography.GreetingName.copy(fontSize = 22.sp)
                        )
                        Text(
                            text = "Raw sensor registers & data quality",
                            style = FlowMeterTypography.GreetingSubtext
                        )
                    }
                }

                CircularIconButton(
                    icon = { DownloadTrayIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                    size = 38.dp,
                    onClick = { showExportSheet = true }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 2. ATMOSPHERIC FLOWING WAVE
            HeaderAtmosphericWaves(
                modifier = Modifier.fillMaxWidth(),
                height = 28.dp
            )

            Spacer(modifier = Modifier.height(14.dp))

            // 3. DATA CLASSIFICATION LEGEND CARD
            FlowMeterCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "DATA TYPE CLASSIFICATION",
                        style = FlowMeterTypography.CardSectionTitle.copy(fontSize = 10.sp, letterSpacing = 1.sp),
                        color = FlowMeterColors.TextMuted
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        TelemetryDataType.values().forEach { type ->
                            TelemetryTagChip(type = type)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 4. SECTION 1: FLOW METRICS
            ExpandableTelemetrySection(
                title = "Flow Rate Metrics",
                icon = { FlowWavesIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                dataType = TelemetryDataType.MEASURED,
                isExpanded = isFlowExpanded,
                onToggle = { isFlowExpanded = !isFlowExpanded }
            ) {
                TelemetryRegisterRow(label = "Instantaneous Flow Rate", value = "—", unit = "L/min", tag = TelemetryDataType.MEASURED)
                TelemetryRowDivider()
                TelemetryRegisterRow(label = "Session Average Flow Rate", value = "—", unit = "L/min", tag = TelemetryDataType.CALCULATED)
                TelemetryRowDivider()
                TelemetryRegisterRow(label = "Peak Observed Flow Rate", value = "—", unit = "L/min", tag = TelemetryDataType.CALCULATED)
                TelemetryRowDivider()
                TelemetryRegisterRow(label = "Minimum Baseline Flow Rate", value = "—", unit = "L/min", tag = TelemetryDataType.CALCULATED)
                TelemetryRowDivider()
                TelemetryRegisterRow(label = "Estimated Pipe Velocity", value = "—", unit = "m/s", tag = TelemetryDataType.CALCULATED)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 5. SECTION 2: VOLUME METRICS
            ExpandableTelemetrySection(
                title = "Volume Aggregations",
                icon = { WaterDropIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                dataType = TelemetryDataType.CALCULATED,
                isExpanded = isVolumeExpanded,
                onToggle = { isVolumeExpanded = !isVolumeExpanded }
            ) {
                TelemetryRegisterRow(label = "Cumulative Lifetime Volume", value = "—", unit = "Litres", tag = TelemetryDataType.CALCULATED)
                TelemetryRowDivider()
                TelemetryRegisterRow(label = "Today's Consumption (from 00:00)", value = "—", unit = "Litres", tag = TelemetryDataType.CALCULATED)
                TelemetryRowDivider()
                TelemetryRegisterRow(label = "Active Draw Session Volume", value = "—", unit = "Litres", tag = TelemetryDataType.CALCULATED)
                TelemetryRowDivider()
                TelemetryRegisterRow(label = "Integration Time Delta (dt)", value = "100", unit = "ms", tag = TelemetryDataType.DEVICE)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 6. SECTION 3: RAW SENSOR REGISTERS
            ExpandableTelemetrySection(
                title = "Raw Sensor Transducers",
                icon = { DeviceSensorIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                dataType = TelemetryDataType.MEASURED,
                isExpanded = isRawSensorExpanded,
                onToggle = { isRawSensorExpanded = !isRawSensorExpanded }
            ) {
                TelemetryRegisterRow(label = "Hardware Pulse Accumulator", value = "—", unit = "counts", tag = TelemetryDataType.MEASURED)
                TelemetryRowDivider()
                TelemetryRegisterRow(label = "Hall Pulse Frequency", value = "—", unit = "Hz", tag = TelemetryDataType.MEASURED)
                TelemetryRowDivider()
                TelemetryRegisterRow(label = "Raw ADC Transducer Output", value = "—", unit = "mV", tag = TelemetryDataType.MEASURED)
                TelemetryRowDivider()
                TelemetryRegisterRow(label = "Fluid Thermistor Temp", value = "—", unit = "°C", tag = TelemetryDataType.MEASURED)
                TelemetryRowDivider()
                TelemetryRegisterRow(label = "Piezo Pressure Sensor", value = "—", unit = "bar", tag = TelemetryDataType.MEASURED)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 7. SECTION 4: DEVICE & SYSTEM HARDWARE
            ExpandableTelemetrySection(
                title = "Device System Specs",
                icon = { DocumentTextIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                dataType = TelemetryDataType.DEVICE,
                isExpanded = isDeviceExpanded,
                onToggle = { isDeviceExpanded = !isDeviceExpanded }
            ) {
                TelemetryRegisterRow(label = "Device Hardware UUID", value = "—", tag = TelemetryDataType.DEVICE)
                TelemetryRowDivider()
                TelemetryRegisterRow(label = "Firmware Build Number", value = "v1.0.0-rc", tag = TelemetryDataType.DEVICE)
                TelemetryRowDivider()
                TelemetryRegisterRow(label = "Microcontroller Uptime", value = "—", unit = "sec", tag = TelemetryDataType.DEVICE)
                TelemetryRowDivider()
                TelemetryRegisterRow(label = "Battery Supply Voltage", value = "—", unit = "V", tag = TelemetryDataType.DEVICE)
                TelemetryRowDivider()
                TelemetryRegisterRow(label = "Internal Flash Memory Used", value = "—", unit = "KB", tag = TelemetryDataType.DEVICE)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 8. SECTION 5: CONNECTIVITY & LINK
            ExpandableTelemetrySection(
                title = "Wireless Connectivity",
                icon = { WifiIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                dataType = TelemetryDataType.QUALITY,
                isExpanded = isConnectivityExpanded,
                onToggle = { isConnectivityExpanded = !isConnectivityExpanded }
            ) {
                TelemetryRegisterRow(label = "Protocol Interface", value = "BLE 5.0 GATT", tag = TelemetryDataType.DEVICE)
                TelemetryRowDivider()
                TelemetryRegisterRow(label = "RSSI Signal Strength", value = "—", unit = "dBm", tag = TelemetryDataType.QUALITY)
                TelemetryRowDivider()
                TelemetryRegisterRow(label = "Negotiated MTU Size", value = "247", unit = "bytes", tag = TelemetryDataType.DEVICE)
                TelemetryRowDivider()
                TelemetryRegisterRow(label = "Telemetry Transmission Rate", value = "10", unit = "Hz", tag = TelemetryDataType.DEVICE)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 9. SECTION 6: DATA QUALITY & INTEGRITY
            ExpandableTelemetrySection(
                title = "Data Validity & Stream Quality",
                icon = { ShieldSecurityIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                dataType = TelemetryDataType.QUALITY,
                isExpanded = isDataQualityExpanded,
                onToggle = { isDataQualityExpanded = !isDataQualityExpanded }
            ) {
                TelemetryRegisterRow(label = "Packet Loss Ratio", value = "0.00%", tag = TelemetryDataType.QUALITY)
                TelemetryRowDivider()
                TelemetryRegisterRow(label = "Stale Reading Count", value = "0", tag = TelemetryDataType.QUALITY)
                TelemetryRowDivider()
                TelemetryRegisterRow(label = "CRC16 Checksum Status", value = "Valid", tag = TelemetryDataType.QUALITY)
                TelemetryRowDivider()
                TelemetryRegisterRow(label = "Out-of-Range Outliers", value = "0", tag = TelemetryDataType.QUALITY)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 10. SECTION 7: DIAGNOSTICS & FAULT LOGS
            ExpandableTelemetrySection(
                title = "Diagnostics & Fault Flags",
                icon = { HistoryIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                dataType = TelemetryDataType.DIAGNOSTIC,
                isExpanded = isDiagnosticsExpanded,
                onToggle = { isDiagnosticsExpanded = !isDiagnosticsExpanded }
            ) {
                TelemetryRegisterRow(label = "Hardware Fault Code", value = "0x00 (Nominal)", tag = TelemetryDataType.DIAGNOSTIC)
                TelemetryRowDivider()
                TelemetryRegisterRow(label = "Dry Pipe / Air Detection", value = "Clear", tag = TelemetryDataType.DIAGNOSTIC)
                TelemetryRowDivider()
                TelemetryRegisterRow(label = "Sensor Zero-Drift Warning", value = "Nominal", tag = TelemetryDataType.DIAGNOSTIC)
                TelemetryRowDivider()
                TelemetryRegisterRow(label = "Thermal Over-Limit Flag", value = "Normal", tag = TelemetryDataType.DIAGNOSTIC)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Export button
            PrimaryActionButton(
                text = "Export Research Telemetry",
                onClick = { showExportSheet = true },
                modifier = Modifier.fillMaxWidth()
            )
        }

        // ==========================================
        // EXPORT TELEMETRY BOTTOM SHEET
        // ==========================================
        if (showExportSheet) {
            ModalBottomSheet(
                onDismissRequest = { showExportSheet = false },
                sheetState = bottomSheetState,
                containerColor = FlowMeterColors.CardSurface,
                scrimColor = Color.Black.copy(alpha = 0.65f),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                dragHandle = {
                    Box(
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                            .width(44.dp)
                            .height(4.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(FlowMeterColors.TextMuted.copy(alpha = 0.35f))
                    )
                }
            ) {
                ExportTelemetrySheetContent(
                    onClose = {
                        scope.launch {
                            bottomSheetState.hide()
                            showExportSheet = false
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun ExpandableTelemetrySection(
    title: String,
    icon: @Composable () -> Unit,
    dataType: TelemetryDataType,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    content: @Composable () -> Unit
) {
    FlowMeterCard(
        modifier = Modifier.fillMaxWidth(),
        cornerRadius = 20.dp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(color = FlowMeterColors.CyanAccent.copy(alpha = 0.2f)),
                        onClick = onToggle
                    )
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
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
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = title,
                        style = FlowMeterTypography.CardHeaderTitle.copy(fontSize = 14.sp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    TelemetryTagChip(type = dataType)
                }

                Box(
                    modifier = Modifier.size(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (isExpanded) {
                        ChevronUpIcon(size = 14.dp, tint = FlowMeterColors.TextSecondary)
                    } else {
                        ChevronDownIcon(size = 14.dp, tint = FlowMeterColors.TextSecondary)
                    }
                }
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    content()
                }
            }
        }
    }
}

@Composable
private fun ExportTelemetrySheetContent(
    onClose: () -> Unit
) {
    var selectedFormatIndex by remember { mutableIntStateOf(0) } // 0: CSV, 1: JSON
    var selectedScopeIndex by remember { mutableIntStateOf(0) } // 0: All Telemetry, 1: Flow Only, 2: Raw Pulses

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 20.dp)
            .padding(bottom = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Export Research Telemetry",
                    style = FlowMeterTypography.GreetingName.copy(fontSize = 18.sp)
                )
                Text(
                    text = "Prepare data packet logs for analysis",
                    style = FlowMeterTypography.GreetingSubtext.copy(fontSize = 12.sp)
                )
            }

            CircularIconButton(
                icon = { CloseIcon(size = 14.dp, tint = FlowMeterColors.TextSecondary) },
                size = 32.dp,
                onClick = onClose
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "FILE FORMAT",
            style = FlowMeterTypography.CardSectionTitle.copy(fontSize = 11.sp, letterSpacing = 1.sp),
            color = FlowMeterColors.TextSecondary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(FlowMeterColors.DarkBlueAction)
                .border(1.dp, FlowMeterColors.CardBorderSubtle, RoundedCornerShape(12.dp))
                .padding(3.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            val formats = listOf("CSV (Spreadsheet)", "JSON (Raw Packets)")
            formats.forEachIndexed { index, format ->
                val isSelected = index == selectedFormatIndex
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(9.dp))
                        .background(if (isSelected) FlowMeterColors.ElectricBlue.copy(alpha = 0.35f) else Color.Transparent)
                        .border(
                            width = if (isSelected) 1.dp else 0.dp,
                            color = if (isSelected) FlowMeterColors.CyanAccent.copy(alpha = 0.4f) else Color.Transparent,
                            shape = RoundedCornerShape(9.dp)
                        )
                        .clickable { selectedFormatIndex = index }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = format,
                        style = FlowMeterTypography.CardFooterText.copy(
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            color = if (isSelected) Color.White else FlowMeterColors.TextSecondary
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(FlowMeterColors.DarkBlueAction)
                .border(1.dp, FlowMeterColors.CardBorderSubtle, RoundedCornerShape(12.dp))
                .padding(14.dp)
        ) {
            Column {
                Text(
                    text = "DATA READY FOR EXPORT",
                    style = FlowMeterTypography.CardSectionTitle.copy(fontSize = 10.sp, letterSpacing = 1.sp),
                    color = FlowMeterColors.TextMuted
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "No telemetry packets recorded yet.",
                    style = FlowMeterTypography.CardHeaderTitle.copy(fontSize = 13.sp),
                    color = FlowMeterColors.TextPrimary
                )
                Text(
                    text = "Recorded registers and session streams will be packaged and exported here once device hardware is active.",
                    style = FlowMeterTypography.CardFooterText.copy(fontSize = 11.sp),
                    color = FlowMeterColors.TextSecondary
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        PrimaryActionButton(
            text = "Close",
            onClick = onClose,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

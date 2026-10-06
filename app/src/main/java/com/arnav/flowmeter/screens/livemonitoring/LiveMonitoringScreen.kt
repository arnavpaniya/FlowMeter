package com.arnav.flowmeter.screens.livemonitoring

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arnav.flowmeter.branding.colors.FlowMeterColors
import com.arnav.flowmeter.branding.typography.FlowMeterTypography
import com.arnav.flowmeter.components.buttons.CircularIconButton
import com.arnav.flowmeter.components.cards.CurrentFlowCard
import com.arnav.flowmeter.components.cards.FlowMeterCard
import com.arnav.flowmeter.components.icons.ChevronLeftIcon
import com.arnav.flowmeter.components.icons.ClockIcon
import com.arnav.flowmeter.components.icons.DeviceSensorIcon
import com.arnav.flowmeter.components.icons.WifiIcon
import com.arnav.flowmeter.components.status.StatusChip
import com.arnav.flowmeter.components.waves.HeaderAtmosphericWaves

@Composable
fun LiveMonitoringScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
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
                    Text(
                        text = "Live Monitoring",
                        style = FlowMeterTypography.GreetingName.copy(fontSize = 24.sp)
                    )
                    Text(
                        text = "Real-time flow sensor stream",
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

            // 3. MAIN LIVE GAUGE CARD
            CurrentFlowCard()

            Spacer(modifier = Modifier.height(14.dp))

            // 4. LIVE TELEMETRY STATUS CARDS
            FlowMeterCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Telemetry Diagnostics",
                            style = FlowMeterTypography.CardHeaderTitle
                        )
                        StatusChip(text = "Waiting for data")
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Diagnostic Item 1
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(FlowMeterColors.DarkBlueAction.copy(alpha = 0.5f), FlowMeterColors.CardShape)
                                .padding(12.dp)
                        ) {
                            Column {
                                WifiIcon(size = 16.dp, tint = FlowMeterColors.CyanAccent)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Signal",
                                    style = FlowMeterTypography.CardFooterText
                                )
                                Text(
                                    text = "—",
                                    style = FlowMeterTypography.MetricPlaceholderMedium.copy(fontSize = 18.sp)
                                )
                            }
                        }

                        // Diagnostic Item 2
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(FlowMeterColors.DarkBlueAction.copy(alpha = 0.5f), FlowMeterColors.CardShape)
                                .padding(12.dp)
                        ) {
                            Column {
                                DeviceSensorIcon(size = 16.dp, tint = FlowMeterColors.CyanAccent)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Sample Rate",
                                    style = FlowMeterTypography.CardFooterText
                                )
                                Text(
                                    text = "—",
                                    style = FlowMeterTypography.MetricPlaceholderMedium.copy(fontSize = 18.sp)
                                )
                            }
                        }

                        // Diagnostic Item 3
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(FlowMeterColors.DarkBlueAction.copy(alpha = 0.5f), FlowMeterColors.CardShape)
                                .padding(12.dp)
                        ) {
                            Column {
                                ClockIcon(size = 16.dp, tint = FlowMeterColors.CyanAccent)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Latency",
                                    style = FlowMeterTypography.CardFooterText
                                )
                                Text(
                                    text = "—",
                                    style = FlowMeterTypography.MetricPlaceholderMedium.copy(fontSize = 18.sp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

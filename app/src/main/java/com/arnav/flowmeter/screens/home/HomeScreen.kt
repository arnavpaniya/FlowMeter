package com.arnav.flowmeter.screens.home

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arnav.flowmeter.branding.colors.FlowMeterColors
import com.arnav.flowmeter.branding.logo.FlowMeterLogo
import com.arnav.flowmeter.branding.typography.FlowMeterTypography
import com.arnav.flowmeter.components.buttons.CircularIconButton
import com.arnav.flowmeter.components.cards.CurrentFlowCard
import com.arnav.flowmeter.components.cards.DeviceConnectionCard
import com.arnav.flowmeter.components.cards.SummaryMetricCard
import com.arnav.flowmeter.components.cards.TotalRuntimeCard
import com.arnav.flowmeter.components.icons.BellIcon
import com.arnav.flowmeter.components.icons.HistoryIcon
import com.arnav.flowmeter.components.icons.SettingsGearIcon
import com.arnav.flowmeter.components.icons.WaterDropIcon
import com.arnav.flowmeter.components.waves.EnhancedLuminousWaves
import com.arnav.flowmeter.components.waves.HeaderAtmosphericWaves

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNotificationsClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onConnectDeviceClick: () -> Unit = {}
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
                .padding(bottom = 120.dp) // Leave room for floating clay bottom bar
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // TOP HEADER: Logo + Wordmark and Action Buttons with Atmospheric Wave Background
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Background subtle atmospheric flowing waves in top right
                HeaderAtmosphericWaves(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterEnd),
                    height = 50.dp
                )

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

                    // Right Actions: Notification & Settings
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
            }

            Spacer(modifier = Modifier.height(22.dp))

            // GREETING SECTION (Unobstructed, crisp typography)
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
                    text = "Here's your water usage overview",
                    style = FlowMeterTypography.GreetingSubtext
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            // MAIN CURRENT FLOW CARD
            CurrentFlowCard(
                onMoreClick = {}
            )

            Spacer(modifier = Modifier.height(16.dp))

            // SUMMARY CARDS (Side-by-Side: Today's Usage & Average Flow)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                SummaryMetricCard(
                    title = "Today's Usage",
                    unit = "Litres",
                    icon = { WaterDropIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                    modifier = Modifier.weight(1f)
                )

                SummaryMetricCard(
                    title = "Average Flow",
                    unit = "L/min",
                    icon = { HistoryIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // TOTAL RUNTIME CARD
            TotalRuntimeCard()

            Spacer(modifier = Modifier.height(16.dp))

            // DEVICE CONNECTION CARD
            DeviceConnectionCard(
                onConnectClick = onConnectDeviceClick
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

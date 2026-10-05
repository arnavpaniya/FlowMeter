package com.arnav.flowmeter.screens.alerts

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
import androidx.compose.ui.unit.dp
import com.arnav.flowmeter.branding.colors.FlowMeterColors
import com.arnav.flowmeter.branding.logo.FlowMeterLogo
import com.arnav.flowmeter.branding.typography.FlowMeterTypography
import com.arnav.flowmeter.components.buttons.CircularIconButton
import com.arnav.flowmeter.components.buttons.TimeRangeSelector
import com.arnav.flowmeter.components.cards.AlertInfoCard
import com.arnav.flowmeter.components.cards.MainAlertStatusCard
import com.arnav.flowmeter.components.cards.RecentAlertsCard
import com.arnav.flowmeter.components.icons.BellIcon
import com.arnav.flowmeter.components.icons.SettingsGearIcon
import com.arnav.flowmeter.components.waves.EnhancedLuminousWaves
import com.arnav.flowmeter.components.waves.HeaderAtmosphericWaves

@Composable
fun AlertsScreen(
    modifier: Modifier = Modifier,
    onNotificationsClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onViewAllAlertsClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    var selectedFilterIndex by remember { mutableStateOf(0) }
    val filterOptions = listOf("All", "Active", "History")

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
                .padding(bottom = 130.dp) // Clearance for floating bottom navbar
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // TOP HEADER: Brand Logo + Action Buttons with Atmospheric Wave Background
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
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
            }

            Spacer(modifier = Modifier.height(20.dp))

            // MAIN TITLE (Unobstructed, crisp typography)
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Alerts",
                    style = FlowMeterTypography.GreetingName
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Stay informed about your water usage",
                    style = FlowMeterTypography.GreetingSubtext
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // FILTER TABS: All, Active, History
            TimeRangeSelector(
                options = filterOptions,
                selectedIndex = selectedFilterIndex,
                onSelect = { selectedFilterIndex = it }
            )

            Spacer(modifier = Modifier.height(18.dp))

            // MAIN STATUS CARD (Glowing Shield + Normal state)
            MainAlertStatusCard()

            Spacer(modifier = Modifier.height(20.dp))

            // RECENT ALERTS CATEGORY LIST
            RecentAlertsCard(
                onViewAllClick = onViewAllAlertsClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            // INFORMATION CARD
            AlertInfoCard()

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

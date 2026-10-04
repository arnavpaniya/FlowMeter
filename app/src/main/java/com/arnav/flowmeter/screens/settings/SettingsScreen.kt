package com.arnav.flowmeter.screens.settings

import androidx.compose.foundation.background
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
import com.arnav.flowmeter.components.cards.FlowMeterCard
import com.arnav.flowmeter.components.icons.SettingsGearIcon

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(FlowMeterColors.BackgroundDark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(bottom = 100.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Settings",
                style = FlowMeterTypography.GreetingName
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Preferences, units, and system configurations",
                style = FlowMeterTypography.GreetingSubtext
            )

            Spacer(modifier = Modifier.height(24.dp))

            FlowMeterCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SettingItemRow(
                        title = "Flow Units",
                        subtitle = "Litres per minute (L/min)"
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    SettingItemRow(
                        title = "Volume Units",
                        subtitle = "Litres (L)"
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    SettingItemRow(
                        title = "App Theme",
                        subtitle = "Deep Dark (Default)"
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    SettingItemRow(
                        title = "Firmware Version",
                        subtitle = "v1.0.0 (Latest)"
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingItemRow(
    title: String,
    subtitle: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(FlowMeterColors.DarkBlueIconBg),
            contentAlignment = Alignment.Center
        ) {
            SettingsGearIcon(size = 16.dp, tint = FlowMeterColors.CyanAccent)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = title,
                style = FlowMeterTypography.CardHeaderTitle.copy(fontSize = 14.sp)
            )
            Text(
                text = subtitle,
                style = FlowMeterTypography.CardFooterText
            )
        }
    }
}

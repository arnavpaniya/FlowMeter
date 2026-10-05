package com.arnav.flowmeter.screens.device

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arnav.flowmeter.branding.colors.FlowMeterColors
import com.arnav.flowmeter.branding.typography.FlowMeterTypography
import com.arnav.flowmeter.components.cards.DeviceConnectionCard

@Composable
fun DeviceScreen(
    modifier: Modifier = Modifier,
    onConnectClick: () -> Unit = {}
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
                .padding(bottom = 130.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Device Management",
                style = FlowMeterTypography.GreetingName
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Manage your IoT FlowMeter hardware and calibrations",
                style = FlowMeterTypography.GreetingSubtext
            )

            Spacer(modifier = Modifier.height(24.dp))

            DeviceConnectionCard(
                modifier = Modifier.fillMaxWidth(),
                onConnectClick = onConnectClick
            )
        }
    }
}

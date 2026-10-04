package com.arnav.flowmeter.screens.alldata

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arnav.flowmeter.branding.colors.FlowMeterColors
import com.arnav.flowmeter.branding.typography.FlowMeterTypography
import com.arnav.flowmeter.components.cards.FlowMeterCard
import com.arnav.flowmeter.components.icons.HistoryIcon

@Composable
fun AllDataScreen(
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
                text = "All Data",
                style = FlowMeterTypography.GreetingName
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Raw sensor logs, timestamps, and exportable data",
                style = FlowMeterTypography.GreetingSubtext
            )

            Spacer(modifier = Modifier.height(24.dp))

            FlowMeterCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    HistoryIcon(size = 36.dp, tint = FlowMeterColors.CyanAccent)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "No recorded log history",
                        style = FlowMeterTypography.CardHeaderTitle
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Once connected, telemetry packets will be archived here.",
                        style = FlowMeterTypography.CardFooterText,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

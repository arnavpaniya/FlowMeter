package com.arnav.flowmeter.components.status

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arnav.flowmeter.branding.colors.FlowMeterColors
import com.arnav.flowmeter.branding.typography.FlowMeterTypography

enum class TelemetryDataType(
    val label: String,
    val tag: String,
    val color: Color,
    val description: String
) {
    MEASURED("Measured", "MEASURED", Color(0xFF00E5FF), "Direct hardware & sensor readings"),
    CALCULATED("Calculated", "CALCULATED", Color(0xFF818CF8), "Derived metrics & telemetry calculations"),
    DEVICE("Device", "DEVICE", Color(0xFF34D399), "Hardware specs & system status"),
    QUALITY("Quality", "QUALITY", Color(0xFFFBBF24), "Signal strength & data validity"),
    DIAGNOSTIC("Diagnostic", "DIAGNOSTIC", Color(0xFFF43F5E), "Error events & hardware faults")
}

@Composable
fun StatusChip(
    text: String = "Waiting for data",
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(FlowMeterColors.StatusWaitingBg)
            .border(
                width = 1.dp,
                color = FlowMeterColors.StatusWaitingBorder,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 10.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = FlowMeterTypography.StatusBadgeText
        )
    }
}

@Composable
fun TelemetryTagChip(
    type: TelemetryDataType,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(type.color.copy(alpha = 0.12f))
            .border(
                width = 1.dp,
                color = type.color.copy(alpha = 0.35f),
                shape = RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 6.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = type.tag,
            style = FlowMeterTypography.CardFooterText.copy(
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.8.sp,
                color = type.color
            )
        )
    }
}

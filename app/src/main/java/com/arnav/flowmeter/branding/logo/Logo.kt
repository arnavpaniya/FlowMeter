package com.arnav.flowmeter.branding.logo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arnav.flowmeter.R
import com.arnav.flowmeter.branding.colors.FlowMeterColors

/**
 * Renders the official FlowMeter app logo squircle image everywhere across the application.
 */
@Composable
fun FlowMeterLogo(
    modifier: Modifier = Modifier,
    size: Dp = 34.dp,
    showContainer: Boolean = true
) {
    Box(
        modifier = modifier
            .size(size)
            .then(
                if (showContainer) {
                    Modifier
                        .shadow(
                            elevation = 6.dp,
                            shape = RoundedCornerShape(size * 0.26f),
                            spotColor = FlowMeterColors.ElectricBlue.copy(alpha = 0.4f),
                            ambientColor = FlowMeterColors.ElectricBlue.copy(alpha = 0.2f)
                        )
                        .clip(RoundedCornerShape(size * 0.26f))
                } else {
                    Modifier.clip(RoundedCornerShape(size * 0.26f))
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.flowmeter_logo),
            contentDescription = "FlowMeter Logo",
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )
    }
}

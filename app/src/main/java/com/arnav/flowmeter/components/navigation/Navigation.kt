package com.arnav.flowmeter.components.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arnav.flowmeter.branding.colors.FlowMeterColors
import com.arnav.flowmeter.branding.typography.FlowMeterTypography
import com.arnav.flowmeter.components.icons.AnalyticsNavIcon
import com.arnav.flowmeter.components.icons.BellIcon
import com.arnav.flowmeter.components.icons.HomeNavIcon
import com.arnav.flowmeter.components.icons.SettingsGearIcon

enum class NavigationTab(val title: String) {
    HOME("Home"),
    ANALYTICS("Analytics"),
    ALERTS("Alerts"),
    SETTINGS("Settings")
}

/**
 * High-fidelity Floating Pill Navigation Bar.
 * Floats gracefully above the bottom navigation/gesture area with dark navy surface,
 * subtle blue border, restrained glow, and refined active tab illumination.
 */
@Composable
fun FloatingBottomNavBar(
    selectedTab: NavigationTab = NavigationTab.HOME,
    onTabSelected: (NavigationTab) -> Unit = {},
    modifier: Modifier = Modifier
) {
    // Outer container with gentle background gradient scrim for seamless scroll transitions
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        FlowMeterColors.BackgroundDark.copy(alpha = 0.80f),
                        FlowMeterColors.BackgroundDark.copy(alpha = 0.98f)
                    )
                )
            )
            .padding(horizontal = 24.dp)
            .padding(top = 8.dp, bottom = 14.dp)
    ) {
        // Floating Pill Shell
        Box(
            modifier = Modifier
                .fillMaxWidth()
                // Ambient 3D drop shadow
                .shadow(
                    elevation = 16.dp,
                    shape = CircleShape,
                    spotColor = Color(0xCC000000),
                    ambientColor = Color(0x66000000)
                )
                // Soft blue glow shadow
                .shadow(
                    elevation = 6.dp,
                    shape = CircleShape,
                    spotColor = FlowMeterColors.ElectricBlue.copy(alpha = 0.22f),
                    ambientColor = FlowMeterColors.CyanAccent.copy(alpha = 0.12f)
                )
                .clip(CircleShape)
                // Solid Opaque Dark Navy Background
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF141F32),
                            Color(0xFF0F1728),
                            Color(0xFF0B111E)
                        )
                    )
                )
                // Subtle blue border
                .border(
                    width = 1.dp,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0x6638BDF8), // Top subtle highlight rim
                            Color(0x2900A3FF),
                            Color(0x14000000)
                        )
                    ),
                    shape = CircleShape
                )
                .padding(horizontal = 8.dp, vertical = 6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NavigationTab.values().forEach { tab ->
                    val isSelected = tab == selectedTab
                    FloatingNavBarItem(
                        tab = tab,
                        isSelected = isSelected,
                        onClick = { onTabSelected(tab) }
                    )
                }
            }
        }
    }
}

/**
 * Refined floating navigation item with restrained active illumination.
 */
@Composable
private fun FloatingNavBarItem(
    tab: NavigationTab,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val tint by animateColorAsState(
        targetValue = if (isSelected) Color.White else FlowMeterColors.TextSecondary,
        label = "nav_item_tint"
    )

    val elevation by animateDpAsState(
        targetValue = if (isSelected) 4.dp else 0.dp,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "nav_item_elevation"
    )

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .then(
                if (isSelected) {
                    Modifier
                        .shadow(
                            elevation = elevation,
                            shape = RoundedCornerShape(20.dp),
                            spotColor = FlowMeterColors.ElectricBlue.copy(alpha = 0.35f),
                            ambientColor = FlowMeterColors.CyanAccent.copy(alpha = 0.20f)
                        )
                        // Restrained active blue pill gradient
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF0D5499),
                                    Color(0xFF0A4078),
                                    Color(0xFF083260)
                                )
                            )
                        )
                        .border(
                            width = 1.dp,
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0x667DD3FC),
                                    Color(0x2638BDF8),
                                    Color.Transparent
                                )
                            ),
                            shape = RoundedCornerShape(20.dp)
                        )
                } else {
                    Modifier
                }
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = true, color = FlowMeterColors.CyanAccent.copy(alpha = 0.2f)),
                onClick = onClick
            )
            .padding(horizontal = 14.dp, vertical = 7.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (tab) {
                NavigationTab.HOME -> HomeNavIcon(
                    size = 19.dp,
                    tint = tint
                )
                NavigationTab.ANALYTICS -> AnalyticsNavIcon(
                    size = 19.dp,
                    tint = tint
                )
                NavigationTab.ALERTS -> BellIcon(
                    size = 19.dp,
                    tint = tint,
                    hasNotificationDot = (tab == NavigationTab.ALERTS && !isSelected)
                )
                NavigationTab.SETTINGS -> SettingsGearIcon(
                    size = 19.dp,
                    tint = tint
                )
            }

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = tab.title,
                style = FlowMeterTypography.NavLabel.copy(
                    color = tint,
                    fontSize = 10.sp,
                    fontWeight = if (isSelected) androidx.compose.ui.text.font.FontWeight.SemiBold else androidx.compose.ui.text.font.FontWeight.Medium
                )
            )
        }
    }
}

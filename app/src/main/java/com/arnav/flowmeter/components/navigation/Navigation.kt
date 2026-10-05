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
 * High-fidelity Claymorphic Floating Bottom Navigation Bar.
 * Completely opaque with soft 3D inflated pill aesthetic, inner bevel highlights,
 * tactile active tab pill, and background fade scrim to eliminate content bleed-through.
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
                        FlowMeterColors.BackgroundDark.copy(alpha = 0.75f),
                        FlowMeterColors.BackgroundDark.copy(alpha = 0.98f)
                    )
                )
            )
            .padding(horizontal = 20.dp)
            .padding(top = 10.dp, bottom = 12.dp)
    ) {
        // Claymorphic Pill Shell
        Box(
            modifier = Modifier
                .fillMaxWidth()
                // Deep ambient 3D drop shadow
                .shadow(
                    elevation = 22.dp,
                    shape = CircleShape,
                    spotColor = Color(0xE6000000),
                    ambientColor = Color(0x99000000)
                )
                // Secondary cyan glow shadow for clay depth
                .shadow(
                    elevation = 10.dp,
                    shape = CircleShape,
                    spotColor = FlowMeterColors.ElectricBlue.copy(alpha = 0.35f),
                    ambientColor = FlowMeterColors.CyanAccent.copy(alpha = 0.2f)
                )
                .clip(CircleShape)
                // Solid Opaque Clay Background Gradient
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF17243A),
                            Color(0xFF10192A),
                            Color(0xFF0C1322)
                        )
                    )
                )
                // Outer Bevel Highlight Border
                .border(
                    width = 1.2.dp,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0x807DD3FC), // Top illuminated rim
                            Color(0x3300A3FF),
                            Color(0x1F000000)  // Bottom darker bevel
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
                    ClayNavBarItem(
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
 * Tactile inflated Claymorphic navigation item.
 */
@Composable
private fun ClayNavBarItem(
    tab: NavigationTab,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val tint by animateColorAsState(
        targetValue = if (isSelected) Color.White else FlowMeterColors.TextSecondary,
        label = "nav_item_tint"
    )

    val elevation by animateDpAsState(
        targetValue = if (isSelected) 8.dp else 0.dp,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "nav_item_elevation"
    )

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .then(
                if (isSelected) {
                    Modifier
                        // Inflated 3D tactile button shadow
                        .shadow(
                            elevation = elevation,
                            shape = RoundedCornerShape(24.dp),
                            spotColor = FlowMeterColors.ElectricBlue.copy(alpha = 0.6f),
                            ambientColor = FlowMeterColors.ElectricBlue.copy(alpha = 0.4f)
                        )
                        // Active Clay Gradient (Solid, vibrant, volumetric)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF0284C7),
                                    Color(0xFF0091EA),
                                    Color(0xFF0077D4)
                                )
                            )
                        )
                        // Top inner bevel highlight
                        .border(
                            width = 1.dp,
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0x99FFFFFF), // Top edge highlight
                                    Color(0x4038BDF8),
                                    Color(0x00000000)
                                )
                            ),
                            shape = RoundedCornerShape(24.dp)
                        )
                } else {
                    Modifier
                }
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = true, color = FlowMeterColors.CyanAccent.copy(alpha = 0.25f)),
                onClick = onClick
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (tab) {
                NavigationTab.HOME -> HomeNavIcon(
                    size = 20.dp,
                    tint = tint
                )
                NavigationTab.ANALYTICS -> AnalyticsNavIcon(
                    size = 20.dp,
                    tint = tint
                )
                NavigationTab.ALERTS -> BellIcon(
                    size = 20.dp,
                    tint = tint,
                    hasNotificationDot = (tab == NavigationTab.ALERTS && !isSelected)
                )
                NavigationTab.SETTINGS -> SettingsGearIcon(
                    size = 20.dp,
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

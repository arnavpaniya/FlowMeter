package com.arnav.flowmeter.components.navigation

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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

@Composable
fun FloatingBottomNavBar(
    selectedTab: NavigationTab = NavigationTab.HOME,
    onTabSelected: (NavigationTab) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .shadow(
                elevation = 16.dp,
                shape = CircleShape,
                spotColor = FlowMeterColors.ElectricBlue.copy(alpha = 0.25f),
                ambientColor = FlowMeterColors.ElectricBlue.copy(alpha = 0.25f)
            )
            .clip(CircleShape)
            .background(FlowMeterColors.CardSurfaceGlass)
            .border(
                width = 1.dp,
                brush = FlowMeterColors.CardBorderBrush,
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
                NavBarItem(
                    tab = tab,
                    isSelected = isSelected,
                    onClick = { onTabSelected(tab) }
                )
            }
        }
    }
}

@Composable
private fun NavBarItem(
    tab: NavigationTab,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val tint = if (isSelected) Color.White else FlowMeterColors.TextSecondary

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(22.dp))
            .then(
                if (isSelected) {
                    Modifier
                        .background(FlowMeterColors.ActiveNavBrush)
                        .border(
                            width = 1.dp,
                            color = FlowMeterColors.CardBorder,
                            shape = RoundedCornerShape(22.dp)
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
                    tint = tint
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
                    fontSize = 10.sp
                )
            )
        }
    }
}

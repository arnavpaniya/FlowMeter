package com.arnav.flowmeter.screens.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arnav.flowmeter.branding.colors.FlowMeterColors
import com.arnav.flowmeter.branding.logo.FlowMeterLogo
import com.arnav.flowmeter.branding.typography.FlowMeterTypography
import com.arnav.flowmeter.components.buttons.CircularIconButton
import com.arnav.flowmeter.components.cards.FlowMeterCard
import com.arnav.flowmeter.components.icons.BellIcon
import com.arnav.flowmeter.components.icons.ChevronDownIcon
import com.arnav.flowmeter.components.icons.ChevronRightIcon
import com.arnav.flowmeter.components.icons.ChevronUpIcon
import com.arnav.flowmeter.components.icons.ConnectLinkIcon
import com.arnav.flowmeter.components.icons.DeviceSensorIcon
import com.arnav.flowmeter.components.icons.DocumentTextIcon
import com.arnav.flowmeter.components.icons.DownloadTrayIcon
import com.arnav.flowmeter.components.icons.FontSizeAaIcon
import com.arnav.flowmeter.components.icons.InfoCircleIcon
import com.arnav.flowmeter.components.icons.InfoSquareIcon
import com.arnav.flowmeter.components.icons.PaletteIcon
import com.arnav.flowmeter.components.icons.SettingsGearIcon
import com.arnav.flowmeter.components.icons.ShieldSecurityIcon
import com.arnav.flowmeter.components.icons.SunThemeIcon
import com.arnav.flowmeter.components.icons.WaterDropIcon
import com.arnav.flowmeter.components.icons.WifiIcon
import com.arnav.flowmeter.components.waves.EnhancedLuminousWaves
import com.arnav.flowmeter.components.waves.HeaderAtmosphericWaves

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onNotificationsClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    // Interactive states for switches and segmented selectors
    var alertNotificationsEnabled by remember { mutableStateOf(true) }
    var deviceNotificationsEnabled by remember { mutableStateOf(true) }
    var appUpdatesEnabled by remember { mutableStateOf(true) }
    var selectedThemeIndex by remember { mutableIntStateOf(1) } // 0: Light, 1: Dark (Default), 2: System

    // Section collapse states
    var isDeviceExpanded by remember { mutableStateOf(true) }
    var isAppearanceExpanded by remember { mutableStateOf(true) }
    var isNotificationsExpanded by remember { mutableStateOf(true) }
    var isAboutExpanded by remember { mutableStateOf(true) }

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
                    text = "Settings",
                    style = FlowMeterTypography.GreetingName
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Customize your app experience",
                    style = FlowMeterTypography.GreetingSubtext
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // SECTION 1 — DEVICE
            SettingsSectionCard(
                icon = { DeviceSensorIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                title = "Device",
                isExpanded = isDeviceExpanded,
                onToggleExpand = { isDeviceExpanded = !isDeviceExpanded }
            ) {
                SettingsNavigationRow(
                    icon = { ConnectLinkIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                    title = "Connect Device",
                    subtitle = "Pair and manage your FlowMeter"
                )
                SettingsRowDivider()
                SettingsNavigationRow(
                    icon = { InfoSquareIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                    title = "Device Information",
                    subtitle = "View device details and status"
                )
                SettingsRowDivider()
                SettingsNavigationRow(
                    icon = { WifiIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                    title = "Connection Settings",
                    subtitle = "Manage connection preferences"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // SECTION 2 — APPEARANCE
            SettingsSectionCard(
                icon = { PaletteIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                title = "Appearance",
                isExpanded = isAppearanceExpanded,
                onToggleExpand = { isAppearanceExpanded = !isAppearanceExpanded }
            ) {
                // Theme Segmented Row
                SettingsThemeSegmentedRow(
                    selectedIndex = selectedThemeIndex,
                    onThemeSelected = { selectedThemeIndex = it }
                )
                SettingsRowDivider()
                SettingsNavigationRow(
                    icon = { FontSizeAaIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                    title = "Font Size",
                    subtitle = "Adjust text size for better readability"
                )
                SettingsRowDivider()
                SettingsNavigationRow(
                    icon = { WaterDropIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                    title = "Color Accent",
                    subtitle = "Choose your preferred accent color"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // SECTION 3 — NOTIFICATIONS
            SettingsSectionCard(
                icon = { BellIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent, hasNotificationDot = false) },
                title = "Notifications",
                isExpanded = isNotificationsExpanded,
                onToggleExpand = { isNotificationsExpanded = !isNotificationsExpanded }
            ) {
                SettingsSwitchRow(
                    icon = { BellIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent, hasNotificationDot = false) },
                    title = "Alert Notifications",
                    subtitle = "Get notified about unusual usage",
                    checked = alertNotificationsEnabled,
                    onCheckedChange = { alertNotificationsEnabled = it }
                )
                SettingsRowDivider()
                SettingsSwitchRow(
                    icon = { InfoSquareIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                    title = "Device Notifications",
                    subtitle = "Connection and device status updates",
                    checked = deviceNotificationsEnabled,
                    onCheckedChange = { deviceNotificationsEnabled = it }
                )
                SettingsRowDivider()
                SettingsSwitchRow(
                    icon = { DownloadTrayIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                    title = "App Updates",
                    subtitle = "Receive important app updates",
                    checked = appUpdatesEnabled,
                    onCheckedChange = { appUpdatesEnabled = it }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // SECTION 4 — ABOUT
            SettingsSectionCard(
                icon = { InfoCircleIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                title = "About",
                isExpanded = isAboutExpanded,
                onToggleExpand = { isAboutExpanded = !isAboutExpanded }
            ) {
                SettingsNavigationRow(
                    icon = { DocumentTextIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                    title = "About FlowMeter",
                    subtitle = "App version, build info and legal"
                )
                SettingsRowDivider()
                SettingsNavigationRow(
                    icon = { ShieldSecurityIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                    title = "Privacy Policy",
                    subtitle = "How we handle your data"
                )
                SettingsRowDivider()
                SettingsNavigationRow(
                    icon = { DocumentTextIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                    title = "Terms of Service",
                    subtitle = "Terms and conditions"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

/**
 * Reusable Settings Section Card with header and expandable content list.
 */
@Composable
private fun SettingsSectionCard(
    icon: @Composable () -> Unit,
    title: String,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
    content: @Composable () -> Unit
) {
    FlowMeterCard(
        modifier = Modifier.fillMaxWidth(),
        cornerRadius = 22.dp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Section Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(color = FlowMeterColors.CyanAccent.copy(alpha = 0.2f)),
                        onClick = onToggleExpand
                    )
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(FlowMeterColors.DarkBlueIconBg),
                        contentAlignment = Alignment.Center
                    ) {
                        icon()
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = title,
                        style = FlowMeterTypography.CardHeaderTitle.copy(fontSize = 15.sp)
                    )
                }

                Box(
                    modifier = Modifier.size(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (isExpanded) {
                        ChevronUpIcon(size = 14.dp, tint = FlowMeterColors.TextSecondary)
                    } else {
                        ChevronDownIcon(size = 14.dp, tint = FlowMeterColors.TextSecondary)
                    }
                }
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    content()
                }
            }
        }
    }
}

@Composable
private fun SettingsNavigationRow(
    icon: @Composable () -> Unit,
    title: String,
    subtitle: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = FlowMeterColors.CyanAccent.copy(alpha = 0.2f)),
                onClick = onClick
            )
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(FlowMeterColors.DarkBlueIconBg),
                contentAlignment = Alignment.Center
            ) {
                icon()
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = title,
                    style = FlowMeterTypography.CardHeaderTitle.copy(fontSize = 14.sp)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = FlowMeterTypography.CardFooterText.copy(
                        fontSize = 12.sp,
                        color = FlowMeterColors.TextSecondary
                    )
                )
            }
        }

        ChevronRightIcon(size = 14.dp, tint = FlowMeterColors.TextSecondary)
    }
}

@Composable
private fun SettingsThemeSegmentedRow(
    selectedIndex: Int,
    onThemeSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(FlowMeterColors.DarkBlueIconBg),
                contentAlignment = Alignment.Center
            ) {
                SunThemeIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "Theme",
                    style = FlowMeterTypography.CardHeaderTitle.copy(fontSize = 14.sp)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Light, Dark or System default",
                    style = FlowMeterTypography.CardFooterText.copy(
                        fontSize = 12.sp,
                        color = FlowMeterColors.TextSecondary
                    )
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Segmented theme selector pill
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(FlowMeterColors.CardSurface)
                .border(
                    width = 1.dp,
                    color = FlowMeterColors.CardBorderSubtle,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(2.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                listOf("Light", "Dark", "System").forEachIndexed { index, name ->
                    val isSelected = index == selectedIndex
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .then(
                                if (isSelected) {
                                    Modifier.background(FlowMeterColors.ConnectButtonBrush)
                                } else {
                                    Modifier
                                }
                            )
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = ripple(color = FlowMeterColors.CyanAccent.copy(alpha = 0.2f)),
                                onClick = { onThemeSelected(index) }
                            )
                            .padding(horizontal = 8.dp, vertical = 5.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = name,
                            style = FlowMeterTypography.CardFooterText.copy(
                                color = if (isSelected) Color.White else FlowMeterColors.TextSecondary,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) androidx.compose.ui.text.font.FontWeight.SemiBold else androidx.compose.ui.text.font.FontWeight.Normal
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingsSwitchRow(
    icon: @Composable () -> Unit,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(FlowMeterColors.DarkBlueIconBg),
                contentAlignment = Alignment.Center
            ) {
                icon()
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = title,
                    style = FlowMeterTypography.CardHeaderTitle.copy(fontSize = 14.sp)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = FlowMeterTypography.CardFooterText.copy(
                        fontSize = 12.sp,
                        color = FlowMeterColors.TextSecondary
                    )
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = FlowMeterColors.ElectricBlue,
                uncheckedThumbColor = FlowMeterColors.TextSecondary,
                uncheckedTrackColor = FlowMeterColors.CardSurface,
                uncheckedBorderColor = FlowMeterColors.CardBorderSubtle
            )
        )
    }
}

@Composable
private fun SettingsRowDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 46.dp)
            .height(1.dp)
            .background(Color(0x1438BDF8))
    )
}

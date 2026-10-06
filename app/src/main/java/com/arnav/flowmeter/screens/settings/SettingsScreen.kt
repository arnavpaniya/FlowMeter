package com.arnav.flowmeter.screens.settings

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arnav.flowmeter.branding.colors.FlowMeterColors
import com.arnav.flowmeter.branding.logo.FlowMeterLogo
import com.arnav.flowmeter.branding.typography.FlowMeterTypography
import com.arnav.flowmeter.components.buttons.CircularIconButton
import com.arnav.flowmeter.components.buttons.PrimaryActionButton
import com.arnav.flowmeter.components.cards.FlowMeterCard
import com.arnav.flowmeter.components.icons.BellIcon
import com.arnav.flowmeter.components.icons.CheckmarkIcon
import com.arnav.flowmeter.components.icons.ChevronDownIcon
import com.arnav.flowmeter.components.icons.ChevronRightIcon
import com.arnav.flowmeter.components.icons.ChevronUpIcon
import com.arnav.flowmeter.components.icons.ClockIcon
import com.arnav.flowmeter.components.icons.CloseIcon
import com.arnav.flowmeter.components.icons.ConnectLinkIcon
import com.arnav.flowmeter.components.icons.DeviceSensorIcon
import com.arnav.flowmeter.components.icons.DocumentTextIcon
import com.arnav.flowmeter.components.icons.DownloadTrayIcon
import com.arnav.flowmeter.components.icons.FontSizeAaIcon
import com.arnav.flowmeter.components.icons.InfoCircleIcon
import com.arnav.flowmeter.components.icons.InfoSquareIcon
import com.arnav.flowmeter.components.icons.PaletteIcon
import com.arnav.flowmeter.components.icons.SettingsGearIcon
import com.arnav.flowmeter.components.icons.ShieldCheckIcon
import com.arnav.flowmeter.components.icons.ShieldSecurityIcon
import com.arnav.flowmeter.components.icons.SunThemeIcon
import com.arnav.flowmeter.components.icons.WifiIcon
import com.arnav.flowmeter.components.waves.HeaderAtmosphericWaves
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onNotificationsClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onConnectDeviceClick: () -> Unit = {},
    onDeviceInfoClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    // Interactive states for switches and preferences
    var alertNotificationsEnabled by remember { mutableStateOf(true) }
    var deviceNotificationsEnabled by remember { mutableStateOf(true) }
    var appUpdatesEnabled by remember { mutableStateOf(true) }
    var selectedThemeIndex by remember { mutableIntStateOf(1) } // 0: Light, 1: Dark (Default), 2: System

    // Appearance preferences
    var selectedFontSizeIndex by remember { mutableIntStateOf(1) } // 0: Small (90%), 1: Default (100%), 2: Large (115%)
    var selectedAccentIndex by remember { mutableIntStateOf(0) } // 0: Electric Azure, 1: Cyan Glow, 2: Ocean Deep, 3: Aquamarine

    // Connection settings preferences
    var autoReconnectEnabled by remember { mutableStateOf(true) }
    var backgroundSyncEnabled by remember { mutableStateOf(true) }
    var syncIntervalIndex by remember { mutableIntStateOf(1) } // 0: 5s, 1: 10s, 2: 30s, 3: 60s
    var connectionTimeoutIndex by remember { mutableIntStateOf(1) } // 0: 15s, 1: 30s, 2: 60s
    var rssiAlertEnabled by remember { mutableStateOf(false) }

    // Section collapse states
    var isDeviceExpanded by remember { mutableStateOf(true) }
    var isAppearanceExpanded by remember { mutableStateOf(true) }
    var isNotificationsExpanded by remember { mutableStateOf(true) }
    var isAboutExpanded by remember { mutableStateOf(true) }

    // Bottom Sheet states
    var showConnectionSettingsSheet by remember { mutableStateOf(false) }
    var showFontSizeSheet by remember { mutableStateOf(false) }
    var showColorAccentSheet by remember { mutableStateOf(false) }
    var showAboutSheet by remember { mutableStateOf(false) }
    var showPrivacySheet by remember { mutableStateOf(false) }
    var showTermsSheet by remember { mutableStateOf(false) }

    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Handle Back press to close any open bottom sheet
    val isAnySheetOpen = showConnectionSettingsSheet || showFontSizeSheet ||
            showColorAccentSheet || showAboutSheet || showPrivacySheet || showTermsSheet

    BackHandler(enabled = isAnySheetOpen) {
        scope.launch {
            bottomSheetState.hide()
            showConnectionSettingsSheet = false
            showFontSizeSheet = false
            showColorAccentSheet = false
            showAboutSheet = false
            showPrivacySheet = false
            showTermsSheet = false
        }
    }

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
                .padding(bottom = 150.dp) // Generous clearance for floating bottom navigation
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // 1. TOP HEADER: Brand Logo + Action Buttons (Unobstructed)
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
                                hasNotificationDot = false
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

            Spacer(modifier = Modifier.height(18.dp))

            // 2. MAIN TITLE
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Settings",
                    style = FlowMeterTypography.GreetingName
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Customize your FlowMeter experience",
                    style = FlowMeterTypography.GreetingSubtext
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 3. ATMOSPHERIC FLOWING WAVE (Clean subtle separator)
            HeaderAtmosphericWaves(
                modifier = Modifier.fillMaxWidth(),
                height = 28.dp
            )

            Spacer(modifier = Modifier.height(14.dp))

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
                    subtitle = "Pair and manage your FlowMeter",
                    onClick = onConnectDeviceClick
                )
                SettingsRowDivider()
                SettingsNavigationRow(
                    icon = { InfoSquareIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                    title = "Device Information",
                    subtitle = "View device details and status",
                    onClick = onDeviceInfoClick
                )
                SettingsRowDivider()
                SettingsNavigationRow(
                    icon = { WifiIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                    title = "Connection Settings",
                    subtitle = "Manage connection preferences",
                    onClick = { showConnectionSettingsSheet = true }
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

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
                    subtitle = when (selectedFontSizeIndex) {
                        0 -> "Small (90%)"
                        2 -> "Large (115%)"
                        else -> "Default (100%)"
                    },
                    onClick = { showFontSizeSheet = true }
                )
                SettingsRowDivider()
                SettingsNavigationRow(
                    icon = { PaletteIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                    title = "Color Accent",
                    subtitle = when (selectedAccentIndex) {
                        1 -> "Cyan Glow"
                        2 -> "Ocean Deep"
                        3 -> "Aquamarine"
                        else -> "Electric Azure (Default)"
                    },
                    onClick = { showColorAccentSheet = true }
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

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

            Spacer(modifier = Modifier.height(14.dp))

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
                    subtitle = "App version 1.0.0, build info and legal",
                    onClick = { showAboutSheet = true }
                )
                SettingsRowDivider()
                SettingsNavigationRow(
                    icon = { ShieldSecurityIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                    title = "Privacy Policy",
                    subtitle = "How we handle your data",
                    onClick = { showPrivacySheet = true }
                )
                SettingsRowDivider()
                SettingsNavigationRow(
                    icon = { DocumentTextIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
                    title = "Terms of Service",
                    subtitle = "Terms and conditions",
                    onClick = { showTermsSheet = true }
                )
            }
        }

        // ==========================================
        // MODAL BOTTOM SHEETS
        // ==========================================

        if (showConnectionSettingsSheet) {
            FlowMeterBottomSheetContainer(
                sheetState = bottomSheetState,
                onDismissRequest = { showConnectionSettingsSheet = false }
            ) {
                ConnectionSettingsSheetContent(
                    autoReconnect = autoReconnectEnabled,
                    onAutoReconnectChange = { autoReconnectEnabled = it },
                    backgroundSync = backgroundSyncEnabled,
                    onBackgroundSyncChange = { backgroundSyncEnabled = it },
                    syncIntervalIndex = syncIntervalIndex,
                    onSyncIntervalChange = { syncIntervalIndex = it },
                    timeoutIndex = connectionTimeoutIndex,
                    onTimeoutChange = { connectionTimeoutIndex = it },
                    rssiAlert = rssiAlertEnabled,
                    onRssiAlertChange = { rssiAlertEnabled = it },
                    onClose = {
                        scope.launch {
                            bottomSheetState.hide()
                            showConnectionSettingsSheet = false
                        }
                    }
                )
            }
        }

        if (showFontSizeSheet) {
            FlowMeterBottomSheetContainer(
                sheetState = bottomSheetState,
                onDismissRequest = { showFontSizeSheet = false }
            ) {
                FontSizeSheetContent(
                    selectedIndex = selectedFontSizeIndex,
                    onSelect = { selectedFontSizeIndex = it },
                    onClose = {
                        scope.launch {
                            bottomSheetState.hide()
                            showFontSizeSheet = false
                        }
                    }
                )
            }
        }

        if (showColorAccentSheet) {
            FlowMeterBottomSheetContainer(
                sheetState = bottomSheetState,
                onDismissRequest = { showColorAccentSheet = false }
            ) {
                ColorAccentSheetContent(
                    selectedIndex = selectedAccentIndex,
                    onSelect = { selectedAccentIndex = it },
                    onClose = {
                        scope.launch {
                            bottomSheetState.hide()
                            showColorAccentSheet = false
                        }
                    }
                )
            }
        }

        if (showAboutSheet) {
            FlowMeterBottomSheetContainer(
                sheetState = bottomSheetState,
                onDismissRequest = { showAboutSheet = false }
            ) {
                AboutSheetContent(
                    onClose = {
                        scope.launch {
                            bottomSheetState.hide()
                            showAboutSheet = false
                        }
                    }
                )
            }
        }

        if (showPrivacySheet) {
            FlowMeterBottomSheetContainer(
                sheetState = bottomSheetState,
                onDismissRequest = { showPrivacySheet = false }
            ) {
                PrivacyPolicySheetContent(
                    onClose = {
                        scope.launch {
                            bottomSheetState.hide()
                            showPrivacySheet = false
                        }
                    }
                )
            }
        }

        if (showTermsSheet) {
            FlowMeterBottomSheetContainer(
                sheetState = bottomSheetState,
                onDismissRequest = { showTermsSheet = false }
            ) {
                TermsSheetContent(
                    onClose = {
                        scope.launch {
                            bottomSheetState.hide()
                            showTermsSheet = false
                        }
                    }
                )
            }
        }
    }
}

// =========================================================================
// REUSABLE BOTTOM SHEET CONTAINER & STYLING
// =========================================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FlowMeterBottomSheetContainer(
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = FlowMeterColors.CardSurface,
        scrimColor = Color.Black.copy(alpha = 0.65f),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .width(44.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(FlowMeterColors.TextMuted.copy(alpha = 0.35f))
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(bottom = 24.dp)
        ) {
            content()
        }
    }
}

// =========================================================================
// BOTTOM SHEET CONTENTS
// =========================================================================

/**
 * Connection Settings Sheet
 */
@Composable
private fun ConnectionSettingsSheetContent(
    autoReconnect: Boolean,
    onAutoReconnectChange: (Boolean) -> Unit,
    backgroundSync: Boolean,
    onBackgroundSyncChange: (Boolean) -> Unit,
    syncIntervalIndex: Int,
    onSyncIntervalChange: (Int) -> Unit,
    timeoutIndex: Int,
    onTimeoutChange: (Int) -> Unit,
    rssiAlert: Boolean,
    onRssiAlertChange: (Boolean) -> Unit,
    onClose: () -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp)
    ) {
        BottomSheetHeader(
            title = "Connection Settings",
            subtitle = "Manage IoT telemetry and BLE link behavior",
            onClose = onClose
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Switches
        SettingsSwitchRow(
            icon = { WifiIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
            title = "Auto-Reconnect",
            subtitle = "Automatically pair with known FlowMeter on launch",
            checked = autoReconnect,
            onCheckedChange = onAutoReconnectChange
        )
        SettingsRowDivider()

        SettingsSwitchRow(
            icon = { ClockIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
            title = "Background Sync",
            subtitle = "Maintain live flow rate sync when app is minimized",
            checked = backgroundSync,
            onCheckedChange = onBackgroundSyncChange
        )
        SettingsRowDivider()

        SettingsSwitchRow(
            icon = { ShieldSecurityIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent) },
            title = "Signal Loss Alert",
            subtitle = "Notify when Bluetooth RSSI drops below threshold",
            checked = rssiAlert,
            onCheckedChange = onRssiAlertChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Telemetry Polling Rate Selector
        Text(
            text = "TELEMETRY SYNC INTERVAL",
            style = FlowMeterTypography.CardSectionTitle.copy(fontSize = 11.sp, letterSpacing = 1.sp),
            color = FlowMeterColors.TextSecondary
        )
        Spacer(modifier = Modifier.height(8.dp))
        val syncOptions = listOf("5s", "10s", "30s", "60s")
        SegmentedSelector(
            options = syncOptions,
            selectedIndex = syncIntervalIndex,
            onSelect = onSyncIntervalChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Connection Timeout Selector
        Text(
            text = "CONNECTION TIMEOUT",
            style = FlowMeterTypography.CardSectionTitle.copy(fontSize = 11.sp, letterSpacing = 1.sp),
            color = FlowMeterColors.TextSecondary
        )
        Spacer(modifier = Modifier.height(8.dp))
        val timeoutOptions = listOf("15s", "30s", "60s")
        SegmentedSelector(
            options = timeoutOptions,
            selectedIndex = timeoutIndex,
            onSelect = onTimeoutChange
        )

        Spacer(modifier = Modifier.height(24.dp))

        PrimaryActionButton(
            text = "Save Preferences",
            onClick = onClose,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

/**
 * Font Size Sheet
 */
@Composable
private fun FontSizeSheetContent(
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    onClose: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        BottomSheetHeader(
            title = "Font Size",
            subtitle = "Adjust text scale for comfortable reading",
            onClose = onClose
        )

        Spacer(modifier = Modifier.height(16.dp))

        val options = listOf(
            Triple(0, "Small (90%)", "Compact view with higher data density"),
            Triple(1, "Default (100%)", "Standard balanced typography"),
            Triple(2, "Large (115%)", "Enhanced readability across all cards")
        )

        options.forEach { (index, title, desc) ->
            SelectionRadioRow(
                title = title,
                subtitle = desc,
                isSelected = index == selectedIndex,
                onClick = { onSelect(index) }
            )
            if (index < options.size - 1) {
                SettingsRowDivider()
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Live Text Preview Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(FlowMeterColors.DarkBlueAction)
                .border(1.dp, FlowMeterColors.CardBorderSubtle, RoundedCornerShape(14.dp))
                .padding(14.dp)
        ) {
            Column {
                Text(
                    text = "LIVE PREVIEW",
                    style = FlowMeterTypography.CardSectionTitle.copy(fontSize = 10.sp, letterSpacing = 1.sp),
                    color = FlowMeterColors.TextMuted
                )
                Spacer(modifier = Modifier.height(4.dp))
                val previewScale = when (selectedIndex) {
                    0 -> 0.9f
                    2 -> 1.15f
                    else -> 1.0f
                }
                Text(
                    text = "Current Flow: 14.8 L/min",
                    style = FlowMeterTypography.CardHeaderTitle.copy(fontSize = (15 * previewScale).sp),
                    color = FlowMeterColors.CyanAccent
                )
                Text(
                    text = "All telemetry sensors calibrated and operating nominally.",
                    style = FlowMeterTypography.CardFooterText.copy(fontSize = (12 * previewScale).sp),
                    color = FlowMeterColors.TextSecondary
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        PrimaryActionButton(
            text = "Apply Font Size",
            onClick = onClose,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

/**
 * Color Accent Sheet
 */
@Composable
private fun ColorAccentSheetContent(
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    onClose: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        BottomSheetHeader(
            title = "Color Accent",
            subtitle = "Choose your preferred telemetry highlight style",
            onClose = onClose
        )

        Spacer(modifier = Modifier.height(16.dp))

        val accents = listOf(
            Triple(0, "Electric Azure", Color(0xFF00A3FF)),
            Triple(1, "Cyan Glow", Color(0xFF00F0FF)),
            Triple(2, "Ocean Deep", Color(0xFF0284C7)),
            Triple(3, "Aquamarine", Color(0xFF10B981))
        )

        accents.forEach { (index, name, color) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(color = color.copy(alpha = 0.2f)),
                        onClick = { onSelect(index) }
                    )
                    .padding(vertical = 10.dp, horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(color)
                            .border(2.dp, FlowMeterColors.DarkBlueIconBg, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Text(
                        text = name,
                        style = FlowMeterTypography.CardHeaderTitle.copy(fontSize = 14.sp),
                        color = if (index == selectedIndex) Color.White else FlowMeterColors.TextSecondary
                    )
                }

                if (index == selectedIndex) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(color.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        CheckmarkIcon(size = 14.dp, tint = color)
                    }
                }
            }
            if (index < accents.size - 1) {
                SettingsRowDivider()
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        PrimaryActionButton(
            text = "Apply Accent",
            onClick = onClose,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

/**
 * About FlowMeter Sheet
 */
@Composable
private fun AboutSheetContent(
    onClose: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        BottomSheetHeader(
            title = "About FlowMeter",
            subtitle = "Smart water metering application",
            onClose = onClose
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Hero Brand Badge
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(FlowMeterColors.DarkBlueAction)
                .border(1.dp, FlowMeterColors.CardBorderSubtle, RoundedCornerShape(16.dp))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FlowMeterLogo(size = 48.dp, showContainer = true)
            Spacer(modifier = Modifier.width(14.dp))
            Column {
                Text(
                    text = "FlowMeter",
                    style = FlowMeterTypography.AppBrandTitle.copy(fontSize = 18.sp)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Version 1.0.0 (Build 101)",
                    style = FlowMeterTypography.CardFooterText.copy(fontSize = 12.sp),
                    color = FlowMeterColors.CyanAccent
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // System Specs Details
        val specs = listOf(
            "Hardware Protocol" to "BLE 5.0 / UART Sensor Interface",
            "Supported Meters" to "Hall-Effect & Ultrasonic Pulse",
            "Platform Engine" to "Jetpack Compose Modern IoT Architecture",
            "Security Status" to "Hardware Cryptographic Key Ready",
            "Build Date" to "October 2026"
        )

        specs.forEach { (label, value) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = label,
                    style = FlowMeterTypography.CardFooterText.copy(fontSize = 12.sp),
                    color = FlowMeterColors.TextSecondary
                )
                Text(
                    text = value,
                    style = FlowMeterTypography.CardHeaderTitle.copy(fontSize = 12.sp),
                    color = FlowMeterColors.TextPrimary
                )
            }
            SettingsRowDivider()
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "© 2026 FlowMeter Technologies. All rights reserved.",
            style = FlowMeterTypography.CardFooterText.copy(fontSize = 11.sp),
            color = FlowMeterColors.TextMuted,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        PrimaryActionButton(
            text = "Close",
            onClick = onClose,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

/**
 * Privacy Policy Sheet
 */
@Composable
private fun PrivacyPolicySheetContent(
    onClose: () -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp)
    ) {
        BottomSheetHeader(
            title = "Privacy Policy",
            subtitle = "Local-first privacy architecture",
            onClose = onClose
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(FlowMeterColors.DarkBlueAction)
                .border(1.dp, FlowMeterColors.CyanAccent.copy(alpha = 0.25f), RoundedCornerShape(14.dp))
                .padding(14.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                ShieldCheckIcon(size = 22.dp, tint = FlowMeterColors.CyanAccent)
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "100% Local-First Telemetry Guarantee",
                    style = FlowMeterTypography.CardHeaderTitle.copy(fontSize = 13.sp),
                    color = FlowMeterColors.CyanAccent
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        val policySections = listOf(
            "Zero Cloud Telemetry Harvesting" to "FlowMeter never transmits your water consumption habits, sensor readings, or device identifiers to external third-party servers without your explicit manual export action.",
            "Local BLE & On-Device Storage" to "All live measurements, daily aggregations, and alert history are stored and calculated strictly on your local device hardware.",
            "Device Permissions" to "Bluetooth Nearby Devices permissions are utilized exclusively to discover and maintain communication with your local FlowMeter sensor node."
        )

        policySections.forEach { (heading, desc) ->
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                Text(
                    text = heading,
                    style = FlowMeterTypography.CardHeaderTitle.copy(fontSize = 14.sp),
                    color = FlowMeterColors.TextPrimary
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = desc,
                    style = FlowMeterTypography.CardFooterText.copy(fontSize = 12.sp, lineHeight = 17.sp),
                    color = FlowMeterColors.TextSecondary
                )
            }
            SettingsRowDivider()
        }

        Spacer(modifier = Modifier.height(16.dp))

        PrimaryActionButton(
            text = "Got It",
            onClick = onClose,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

/**
 * Terms of Service Sheet
 */
@Composable
private fun TermsSheetContent(
    onClose: () -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp)
    ) {
        BottomSheetHeader(
            title = "Terms of Service",
            subtitle = "Usage guidelines and calibration disclaimer",
            onClose = onClose
        )

        Spacer(modifier = Modifier.height(16.dp))

        val terms = listOf(
            "1. IoT Telemetry Monitoring" to "FlowMeter is designed for monitoring water flow rate, cumulative volume, and system alerts. Sensor accuracy depends on proper hardware calibration and fluid viscosity.",
            "2. Critical & Industrial Use" to "For commercial or critical infrastructure, ensure secondary physical verification meters and appropriate fail-safe backflow prevention systems are in place.",
            "3. Software Updates & Firmware" to "Periodic firmware updates may be required to maintain peak sensor timing accuracy and secure BLE communication."
        )

        terms.forEach { (heading, desc) ->
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                Text(
                    text = heading,
                    style = FlowMeterTypography.CardHeaderTitle.copy(fontSize = 14.sp),
                    color = FlowMeterColors.TextPrimary
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = desc,
                    style = FlowMeterTypography.CardFooterText.copy(fontSize = 12.sp, lineHeight = 17.sp),
                    color = FlowMeterColors.TextSecondary
                )
            }
            SettingsRowDivider()
        }

        Spacer(modifier = Modifier.height(16.dp))

        PrimaryActionButton(
            text = "Accept & Close",
            onClick = onClose,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// =========================================================================
// HELPER COMPONENTS
// =========================================================================

@Composable
private fun BottomSheetHeader(
    title: String,
    subtitle: String,
    onClose: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = FlowMeterTypography.GreetingName.copy(fontSize = 18.sp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                style = FlowMeterTypography.GreetingSubtext.copy(fontSize = 12.sp)
            )
        }
        CircularIconButton(
            icon = { CloseIcon(size = 14.dp, tint = FlowMeterColors.TextSecondary) },
            size = 32.dp,
            onClick = onClose
        )
    }
}

@Composable
private fun SelectionRadioRow(
    title: String,
    subtitle: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = FlowMeterColors.CyanAccent.copy(alpha = 0.2f)),
                onClick = onClick
            )
            .padding(vertical = 10.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = FlowMeterTypography.CardHeaderTitle.copy(fontSize = 14.sp),
                color = if (isSelected) FlowMeterColors.CyanAccent else FlowMeterColors.TextPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                style = FlowMeterTypography.CardFooterText.copy(fontSize = 11.sp),
                color = FlowMeterColors.TextSecondary
            )
        }

        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(CircleShape)
                .border(
                    width = if (isSelected) 2.dp else 1.5.dp,
                    color = if (isSelected) FlowMeterColors.CyanAccent else FlowMeterColors.TextMuted,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(FlowMeterColors.CyanAccent)
                )
            }
        }
    }
}

@Composable
private fun SegmentedSelector(
    options: List<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(FlowMeterColors.DarkBlueAction)
            .border(1.dp, FlowMeterColors.CardBorderSubtle, RoundedCornerShape(12.dp))
            .padding(3.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        options.forEachIndexed { index, option ->
            val isSelected = index == selectedIndex
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(9.dp))
                    .background(
                        if (isSelected) FlowMeterColors.ElectricBlue.copy(alpha = 0.35f) else Color.Transparent
                    )
                    .border(
                        width = if (isSelected) 1.dp else 0.dp,
                        color = if (isSelected) FlowMeterColors.CyanAccent.copy(alpha = 0.4f) else Color.Transparent,
                        shape = RoundedCornerShape(9.dp)
                    )
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(color = FlowMeterColors.CyanAccent.copy(alpha = 0.2f)),
                        onClick = { onSelect(index) }
                    )
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = option,
                    style = FlowMeterTypography.CardFooterText.copy(
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                        color = if (isSelected) Color.White else FlowMeterColors.TextSecondary
                    )
                )
            }
        }
    }
}

/**
 * Reusable Settings Section Card with clean calm styling.
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
        cornerRadius = 20.dp
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
                        .padding(top = 8.dp)
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
                    .size(32.dp)
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
                Spacer(modifier = Modifier.height(1.dp))
                Text(
                    text = subtitle,
                    style = FlowMeterTypography.CardFooterText.copy(fontSize = 11.sp),
                    color = FlowMeterColors.TextSecondary
                )
            }
        }

        ChevronRightIcon(size = 14.dp, tint = FlowMeterColors.TextMuted)
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
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = FlowMeterColors.CyanAccent.copy(alpha = 0.2f)),
                onClick = { onCheckedChange(!checked) }
            )
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
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
            Column {
                Text(
                    text = title,
                    style = FlowMeterTypography.CardHeaderTitle.copy(fontSize = 14.sp)
                )
                Spacer(modifier = Modifier.height(1.dp))
                Text(
                    text = subtitle,
                    style = FlowMeterTypography.CardFooterText.copy(fontSize = 11.sp),
                    color = FlowMeterColors.TextSecondary
                )
            }
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = FlowMeterColors.ElectricBlue,
                uncheckedThumbColor = FlowMeterColors.TextSecondary,
                uncheckedTrackColor = FlowMeterColors.DarkBlueAction,
                uncheckedBorderColor = Color.Transparent
            )
        )
    }
}

@Composable
private fun SettingsThemeSegmentedRow(
    selectedIndex: Int,
    onThemeSelected: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
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
                SunThemeIcon(size = 18.dp, tint = FlowMeterColors.CyanAccent)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "Theme",
                    style = FlowMeterTypography.CardHeaderTitle.copy(fontSize = 14.sp)
                )
                Text(
                    text = "Light, Dark or System default",
                    style = FlowMeterTypography.CardFooterText.copy(fontSize = 11.sp),
                    color = FlowMeterColors.TextSecondary
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Compact segmented theme selector
        SegmentedSelector(
            options = listOf("Light", "Dark", "System"),
            selectedIndex = selectedIndex,
            onSelect = onThemeSelected
        )
    }
}

@Composable
private fun SettingsRowDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .height(1.dp)
            .background(FlowMeterColors.DividerDark)
    )
}

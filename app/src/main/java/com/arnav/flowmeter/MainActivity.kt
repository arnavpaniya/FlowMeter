package com.arnav.flowmeter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arnav.flowmeter.branding.colors.FlowMeterColors
import com.arnav.flowmeter.components.navigation.FloatingBottomNavBar
import com.arnav.flowmeter.components.navigation.NavigationTab
import com.arnav.flowmeter.screens.alerts.AlertsScreen
import com.arnav.flowmeter.screens.analytics.AnalyticsScreen
import com.arnav.flowmeter.screens.home.HomeScreen
import com.arnav.flowmeter.screens.settings.SettingsScreen
import com.arnav.flowmeter.ui.theme.FlowMeterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlowMeterTheme {
                FlowMeterApp()
            }
        }
    }
}

@Composable
fun FlowMeterApp() {
    var selectedTab by remember { mutableStateOf(NavigationTab.HOME) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FlowMeterColors.BackgroundDark)
    ) {
        // Main Screen Content
        when (selectedTab) {
            NavigationTab.HOME -> HomeScreen(
                onNotificationsClick = { selectedTab = NavigationTab.ALERTS },
                onSettingsClick = { selectedTab = NavigationTab.SETTINGS },
                onConnectDeviceClick = { /* Handle BLE / Wi-Fi pairing */ }
            )
            NavigationTab.ANALYTICS -> AnalyticsScreen(
                onNotificationsClick = { selectedTab = NavigationTab.ALERTS },
                onSettingsClick = { selectedTab = NavigationTab.SETTINGS }
            )
            NavigationTab.ALERTS -> AlertsScreen()
            NavigationTab.SETTINGS -> SettingsScreen()
        }

        // Floating Bottom Navigation Bar
        FloatingBottomNavBar(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0B0F19)
@Composable
fun FlowMeterAppPreview() {
    FlowMeterTheme {
        FlowMeterApp()
    }
}
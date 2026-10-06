package com.arnav.flowmeter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import com.arnav.flowmeter.screens.alldata.AllDataScreen
import com.arnav.flowmeter.screens.analytics.AnalyticsScreen
import com.arnav.flowmeter.screens.device.DeviceScreen
import com.arnav.flowmeter.screens.flow.FlowDetailScreen
import com.arnav.flowmeter.screens.home.HomeScreen
import com.arnav.flowmeter.screens.livemonitoring.LiveMonitoringScreen
import com.arnav.flowmeter.screens.session.SessionDetailScreen
import com.arnav.flowmeter.screens.settings.SettingsScreen
import com.arnav.flowmeter.screens.splash.SplashScreen
import com.arnav.flowmeter.screens.volume.VolumeDetailScreen
import com.arnav.flowmeter.ui.theme.FlowMeterTheme

sealed interface AppDestination {
    data class MainTab(val tab: NavigationTab) : AppDestination
    data class FlowDetail(val fromTab: NavigationTab = NavigationTab.HOME) : AppDestination
    data class VolumeDetail(val fromTab: NavigationTab = NavigationTab.HOME) : AppDestination
    data class SessionDetail(val fromTab: NavigationTab = NavigationTab.HOME) : AppDestination
    data class DeviceHealth(val fromTab: NavigationTab = NavigationTab.HOME) : AppDestination
    data class Telemetry(val fromTab: NavigationTab = NavigationTab.HOME) : AppDestination
    data class LiveMonitoring(val fromTab: NavigationTab = NavigationTab.HOME) : AppDestination
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlowMeterTheme {
                FlowMeterRoot()
            }
        }
    }
}

@Composable
fun FlowMeterRoot() {
    var isSplashVisible by remember { mutableStateOf(true) }

    AnimatedContent(
        targetState = isSplashVisible,
        transitionSpec = {
            fadeIn(animationSpec = tween(500)) togetherWith fadeOut(animationSpec = tween(500))
        },
        label = "SplashToMainTransition"
    ) { showSplash ->
        if (showSplash) {
            SplashScreen(
                onSplashFinished = { isSplashVisible = false }
            )
        } else {
            FlowMeterApp()
        }
    }
}

@Composable
fun FlowMeterApp() {
    var currentDestination by remember { mutableStateOf<AppDestination>(AppDestination.MainTab(NavigationTab.HOME)) }
    var selectedTab by remember { mutableStateOf(NavigationTab.HOME) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FlowMeterColors.BackgroundDark)
    ) {
        // Screen Content Router with Smooth Crossfade
        AnimatedContent(
            targetState = currentDestination,
            transitionSpec = {
                fadeIn(animationSpec = tween(280)) togetherWith fadeOut(animationSpec = tween(280))
            },
            label = "ScreenTransition"
        ) { destination ->
            when (destination) {
                is AppDestination.MainTab -> {
                    when (destination.tab) {
                        NavigationTab.HOME -> HomeScreen(
                            onNotificationsClick = {
                                selectedTab = NavigationTab.ALERTS
                                currentDestination = AppDestination.MainTab(NavigationTab.ALERTS)
                            },
                            onSettingsClick = {
                                selectedTab = NavigationTab.SETTINGS
                                currentDestination = AppDestination.MainTab(NavigationTab.SETTINGS)
                            },
                            onFlowDetailClick = {
                                currentDestination = AppDestination.FlowDetail(fromTab = NavigationTab.HOME)
                            },
                            onVolumeDetailClick = {
                                currentDestination = AppDestination.VolumeDetail(fromTab = NavigationTab.HOME)
                            },
                            onSessionDetailClick = {
                                currentDestination = AppDestination.SessionDetail(fromTab = NavigationTab.HOME)
                            },
                            onDeviceHealthClick = {
                                currentDestination = AppDestination.DeviceHealth(fromTab = NavigationTab.HOME)
                            },
                            onTelemetryClick = {
                                currentDestination = AppDestination.Telemetry(fromTab = NavigationTab.HOME)
                            }
                        )
                        NavigationTab.ANALYTICS -> AnalyticsScreen(
                            onNotificationsClick = {
                                selectedTab = NavigationTab.ALERTS
                                currentDestination = AppDestination.MainTab(NavigationTab.ALERTS)
                            },
                            onSettingsClick = {
                                selectedTab = NavigationTab.SETTINGS
                                currentDestination = AppDestination.MainTab(NavigationTab.SETTINGS)
                            },
                            onFlowDetailClick = {
                                currentDestination = AppDestination.FlowDetail(fromTab = NavigationTab.ANALYTICS)
                            },
                            onVolumeDetailClick = {
                                currentDestination = AppDestination.VolumeDetail(fromTab = NavigationTab.ANALYTICS)
                            },
                            onSessionDetailClick = {
                                currentDestination = AppDestination.SessionDetail(fromTab = NavigationTab.ANALYTICS)
                            },
                            onTelemetryClick = {
                                currentDestination = AppDestination.Telemetry(fromTab = NavigationTab.ANALYTICS)
                            }
                        )
                        NavigationTab.ALERTS -> AlertsScreen(
                            onNotificationsClick = {
                                selectedTab = NavigationTab.ALERTS
                                currentDestination = AppDestination.MainTab(NavigationTab.ALERTS)
                            },
                            onSettingsClick = {
                                selectedTab = NavigationTab.SETTINGS
                                currentDestination = AppDestination.MainTab(NavigationTab.SETTINGS)
                            },
                            onViewTelemetryClick = {
                                currentDestination = AppDestination.Telemetry(fromTab = NavigationTab.ALERTS)
                            }
                        )
                        NavigationTab.SETTINGS -> SettingsScreen(
                            onNotificationsClick = {
                                selectedTab = NavigationTab.ALERTS
                                currentDestination = AppDestination.MainTab(NavigationTab.ALERTS)
                            },
                            onSettingsClick = {
                                selectedTab = NavigationTab.SETTINGS
                                currentDestination = AppDestination.MainTab(NavigationTab.SETTINGS)
                            },
                            onConnectDeviceClick = {
                                currentDestination = AppDestination.DeviceHealth(fromTab = NavigationTab.SETTINGS)
                            },
                            onDeviceInfoClick = {
                                currentDestination = AppDestination.DeviceHealth(fromTab = NavigationTab.SETTINGS)
                            }
                        )
                    }
                }
                is AppDestination.FlowDetail -> FlowDetailScreen(
                    onBackClick = {
                        currentDestination = AppDestination.MainTab(destination.fromTab)
                    },
                    onViewTelemetryClick = {
                        currentDestination = AppDestination.Telemetry(fromTab = destination.fromTab)
                    }
                )
                is AppDestination.VolumeDetail -> VolumeDetailScreen(
                    onBackClick = {
                        currentDestination = AppDestination.MainTab(destination.fromTab)
                    },
                    onViewSessionsClick = {
                        currentDestination = AppDestination.SessionDetail(fromTab = destination.fromTab)
                    },
                    onViewTelemetryClick = {
                        currentDestination = AppDestination.Telemetry(fromTab = destination.fromTab)
                    }
                )
                is AppDestination.SessionDetail -> SessionDetailScreen(
                    onBackClick = {
                        currentDestination = AppDestination.MainTab(destination.fromTab)
                    },
                    onViewTelemetryClick = {
                        currentDestination = AppDestination.Telemetry(fromTab = destination.fromTab)
                    }
                )
                is AppDestination.DeviceHealth -> DeviceScreen(
                    onBackClick = {
                        currentDestination = AppDestination.MainTab(destination.fromTab)
                    },
                    onConnectClick = {
                        /* Ready for future BLE pairing */
                    },
                    onViewAllDataClick = {
                        currentDestination = AppDestination.Telemetry(fromTab = destination.fromTab)
                    }
                )
                is AppDestination.Telemetry -> AllDataScreen(
                    onBackClick = {
                        currentDestination = AppDestination.MainTab(destination.fromTab)
                    }
                )
                is AppDestination.LiveMonitoring -> LiveMonitoringScreen(
                    onBackClick = {
                        currentDestination = AppDestination.MainTab(destination.fromTab)
                    }
                )
            }
        }

        // Floating Bottom Navigation Bar (Visible on Main Tabs)
        if (currentDestination is AppDestination.MainTab) {
            FloatingBottomNavBar(
                selectedTab = selectedTab,
                onTabSelected = { tab ->
                    selectedTab = tab
                    currentDestination = AppDestination.MainTab(tab)
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding()
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0B0F19)
@Composable
fun FlowMeterAppPreview() {
    FlowMeterTheme {
        FlowMeterApp()
    }
}
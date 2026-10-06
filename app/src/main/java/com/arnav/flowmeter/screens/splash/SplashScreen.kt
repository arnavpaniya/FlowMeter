package com.arnav.flowmeter.screens.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arnav.flowmeter.branding.colors.FlowMeterColors
import com.arnav.flowmeter.branding.logo.FlowMeterLogo
import com.arnav.flowmeter.branding.typography.FlowMeterTypography
import com.arnav.flowmeter.ui.theme.FlowMeterTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Premium "Fluid Droplet Bloom" Splash Preloader Screen.
 * Features concurrent fluid animation, expanding concentric hydro-ripples,
 * luminous backlit bloom, crystal brand typography, and a modern progress indicator.
 */
@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit,
    modifier: Modifier = Modifier,
    durationMillis: Long = 2600L
) {
    val logoScale = remember { Animatable(0.75f) }
    val logoAlpha = remember { Animatable(0f) }
    val textAlpha = remember { Animatable(0f) }
    val progressAnim = remember { Animatable(0f) }

    val infiniteTransition = rememberInfiniteTransition(label = "splash_ripples")

    // Ripple 1
    val ripple1Phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2600, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ripple1"
    )

    // Ripple 2 (Offset phase)
    val ripple2Phase by infiniteTransition.animateFloat(
        initialValue = 0.33f,
        targetValue = 1.33f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2600, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ripple2"
    )

    // Ripple 3 (Offset phase)
    val ripple3Phase by infiniteTransition.animateFloat(
        initialValue = 0.66f,
        targetValue = 1.66f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2600, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ripple3"
    )

    // Ambient glow pulse
    val glowPulse by infiniteTransition.animateFloat(
        initialValue = 0.65f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_pulse"
    )

    LaunchedEffect(Unit) {
        // Parallel concurrent animation triggers
        launch {
            logoScale.animateTo(1f, animationSpec = tween(550, easing = FastOutSlowInEasing))
        }
        launch {
            logoAlpha.animateTo(1f, animationSpec = tween(400, easing = FastOutSlowInEasing))
        }
        launch {
            delay(120)
            textAlpha.animateTo(1f, animationSpec = tween(450, easing = FastOutSlowInEasing))
        }
        launch {
            progressAnim.animateTo(1f, animationSpec = tween((durationMillis - 400).toInt(), easing = FastOutSlowInEasing))
        }

        delay(durationMillis)
        onSplashFinished()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(FlowMeterColors.BackgroundDark),
        contentAlignment = Alignment.Center
    ) {
        // --- BACKGROUND EXPANDING CONCENTRIC WATER RIPPLES ---
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val center = Offset(w / 2f, h * 0.45f)
            val maxRadius = w * 0.60f

            val ripples = listOf(ripple1Phase % 1f, ripple2Phase % 1f, ripple3Phase % 1f)

            ripples.forEach { phase ->
                val radius = 70.dp.toPx() + (phase * (maxRadius - 70.dp.toPx()))
                val alpha = ((1f - phase) * 0.40f).coerceIn(0f, 0.40f)
                val strokeW = (2.2.dp.toPx() * (1f - phase * 0.5f)).coerceAtLeast(1f)

                // Concentric cyan/electric blue hydro-ripple
                drawCircle(
                    color = FlowMeterColors.CyanAccent.copy(alpha = alpha),
                    radius = radius,
                    center = center,
                    style = Stroke(width = strokeW)
                )
            }

            // Radial ambient blue back-light glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        FlowMeterColors.ElectricBlue.copy(alpha = 0.35f * glowPulse),
                        FlowMeterColors.CyanAccent.copy(alpha = 0.15f * glowPulse),
                        Color.Transparent
                    ),
                    center = center,
                    radius = 170.dp.toPx()
                ),
                radius = 170.dp.toPx(),
                center = center
            )
        }

        // --- FOREGROUND CONTENT (Centered Logo + Wordmark + Bottom Progress) ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // CENTER BRAND GROUP (Perfect Optical Center)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Official Squircle Logo with Scale & Ambient Elevation Glow
                Box(
                    modifier = Modifier
                        .scale(logoScale.value)
                        .alpha(logoAlpha.value)
                        .shadow(
                            elevation = 28.dp,
                            shape = RoundedCornerShape(32.dp),
                            spotColor = FlowMeterColors.ElectricBlue.copy(alpha = 0.7f * glowPulse),
                            ambientColor = FlowMeterColors.CyanAccent.copy(alpha = 0.35f)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    FlowMeterLogo(
                        size = 118.dp,
                        showContainer = true
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                // Brand Wordmark + Subtitle
                Column(
                    modifier = Modifier
                        .alpha(textAlpha.value),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "FlowMeter",
                        style = FlowMeterTypography.AppBrandTitle.copy(
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.4.sp,
                            color = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Intelligent Flow Monitoring",
                        style = FlowMeterTypography.GreetingSubtext.copy(
                            fontSize = 14.sp,
                            letterSpacing = 0.3.sp,
                            color = FlowMeterColors.TextSecondary
                        )
                    )
                }
            }

            // --- BOTTOM LIQUID PROGRESS CAPSULE ---
            Column(
                modifier = Modifier
                    .padding(bottom = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(20.dp)
                        .shadow(
                            elevation = 8.dp,
                            shape = CircleShape,
                            spotColor = FlowMeterColors.ElectricBlue.copy(alpha = 0.5f)
                        )
                        .clip(CircleShape)
                        .background(FlowMeterColors.CardSurface)
                        .border(
                            width = 1.dp,
                            color = FlowMeterColors.CardBorderSubtle,
                            shape = CircleShape
                        )
                        .padding(3.dp)
                ) {
                    // Fluid animated progress fill
                    Box(
                        modifier = Modifier
                            .fillMaxSize(fraction = 1f)
                            .clip(CircleShape)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(fraction = 1f)
                                .scale(scaleX = progressAnim.value.coerceIn(0.04f, 1f), scaleY = 1f)
                                .clip(CircleShape)
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(
                                            Color(0xFF0284C7),
                                            Color(0xFF00A3FF),
                                            Color(0xFF38BDF8)
                                        )
                                    )
                                )
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0B0F19)
@Composable
fun SplashScreenPreview() {
    FlowMeterTheme {
        SplashScreen(onSplashFinished = {})
    }
}

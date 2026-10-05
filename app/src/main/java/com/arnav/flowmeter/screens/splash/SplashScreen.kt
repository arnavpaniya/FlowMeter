package com.arnav.flowmeter.screens.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arnav.flowmeter.branding.colors.FlowMeterColors
import com.arnav.flowmeter.branding.logo.FlowMeterLogo
import com.arnav.flowmeter.branding.typography.FlowMeterTypography
import kotlinx.coroutines.delay

/**
 * High-fidelity "Fluid Droplet Bloom" Splash Preloader Screen.
 * Features expanding concentric hydro-ripples, floating logo bloom,
 * ambient backlit glow, and a smooth fluid progress capsule.
 */
@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit,
    modifier: Modifier = Modifier,
    durationMillis: Long = 2600L
) {
    val logoScale = remember { Animatable(0.7f) }
    val logoAlpha = remember { Animatable(0f) }
    val textAlpha = remember { Animatable(0f) }
    val progressAnim = remember { Animatable(0f) }

    val infiniteTransition = rememberInfiniteTransition(label = "splash_ripples")

    // Ripple 1
    val ripple1Phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ripple1"
    )

    // Ripple 2 (Offset phase)
    val ripple2Phase by infiniteTransition.animateFloat(
        initialValue = 0.33f,
        targetValue = 1.33f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ripple2"
    )

    // Ripple 3 (Offset phase)
    val ripple3Phase by infiniteTransition.animateFloat(
        initialValue = 0.66f,
        targetValue = 1.66f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ripple3"
    )

    // Ambient glow pulse
    val glowPulse by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_pulse"
    )

    LaunchedEffect(Unit) {
        // Step 1: Fade and bloom the center logo
        logoAlpha.animateTo(1f, animationSpec = tween(600, easing = FastOutSlowInEasing))
        logoScale.animateTo(1f, animationSpec = tween(700, easing = FastOutSlowInEasing))

        // Step 2: Reveal wordmark typography
        delay(200)
        textAlpha.animateTo(1f, animationSpec = tween(600, easing = FastOutSlowInEasing))

        // Step 3: Animate loading progress capsule
        progressAnim.animateTo(1f, animationSpec = tween(1400, easing = FastOutSlowInEasing))

        // Step 4: Finish splash
        delay(300)
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
            val center = Offset(w / 2f, h * 0.44f)
            val maxRadius = w * 0.58f

            val ripples = listOf(ripple1Phase % 1f, ripple2Phase % 1f, ripple3Phase % 1f)

            ripples.forEach { phase ->
                val radius = 80.dp.toPx() + (phase * (maxRadius - 80.dp.toPx()))
                val alpha = ((1f - phase) * 0.35f).coerceIn(0f, 0.35f)
                val strokeW = (2.dp.toPx() * (1f - phase * 0.5f)).coerceAtLeast(1f)

                // Soft outer ring
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
                        FlowMeterColors.ElectricBlue.copy(alpha = 0.30f * glowPulse),
                        FlowMeterColors.CyanAccent.copy(alpha = 0.12f * glowPulse),
                        Color.Transparent
                    ),
                    center = center,
                    radius = 160.dp.toPx()
                ),
                radius = 160.dp.toPx(),
                center = center
            )
        }

        // --- CENTER LOGO + WORDMARK ---
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Main Brand Display
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Official Squircle Logo with Scale & Elevation
                Box(
                    modifier = Modifier
                        .scale(logoScale.value)
                        .shadow(
                            elevation = 24.dp,
                            shape = RoundedCornerShape(36.dp),
                            spotColor = FlowMeterColors.ElectricBlue.copy(alpha = 0.6f * glowPulse),
                            ambientColor = FlowMeterColors.CyanAccent.copy(alpha = 0.3f)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    FlowMeterLogo(
                        size = 130.dp,
                        showContainer = true
                    )
                }

                Spacer(modifier = Modifier.height(36.dp))

                // FlowMeter Title
                Text(
                    text = "FlowMeter",
                    style = FlowMeterTypography.AppBrandTitle.copy(
                        fontSize = 36.sp,
                        letterSpacing = 0.5.sp,
                        color = Color.White.copy(alpha = textAlpha.value)
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Subtitle
                Text(
                    text = "Intelligent Flow Monitoring",
                    style = FlowMeterTypography.GreetingSubtext.copy(
                        fontSize = 15.sp,
                        letterSpacing = 0.3.sp,
                        color = FlowMeterColors.TextSecondary.copy(alpha = textAlpha.value)
                    )
                )
            }

            // --- BOTTOM LIQUID PROGRESS CAPSULE ---
            Column(
                modifier = Modifier
                    .padding(bottom = 60.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .width(110.dp)
                        .height(22.dp)
                        .shadow(
                            elevation = 10.dp,
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
                                .scale(scaleX = progressAnim.value.coerceIn(0.05f, 1f), scaleY = 1f)
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

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, backgroundColor = 0xFF0B0F19)
@Composable
fun SplashScreenPreview() {
    com.arnav.flowmeter.ui.theme.FlowMeterTheme {
        SplashScreen(onSplashFinished = {})
    }
}


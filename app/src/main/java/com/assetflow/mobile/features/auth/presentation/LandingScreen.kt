package com.assetflow.mobile.features.auth.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.EventAvailable
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.assetflow.mobile.core.ui.components.AssetFlowButton
import com.assetflow.mobile.core.ui.components.AssetFlowButtonVariant
import com.assetflow.mobile.core.ui.theme.AssetFlowSpacing
import com.assetflow.mobile.core.ui.theme.AssetFlowTheme

@Composable
fun LandingRoute(
    onLoginClick: () -> Unit,
    onCreateAccountClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LandingScreen(
        onLoginClick = onLoginClick,
        onCreateAccountClick = onCreateAccountClick,
        modifier = modifier,
    )
}

@Composable
fun LandingScreen(
    onLoginClick: () -> Unit,
    onCreateAccountClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colorScheme = MaterialTheme.colorScheme

    Scaffold(
        modifier = modifier,
        containerColor = colorScheme.background,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
        ) {
            LandingHero()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = AssetFlowSpacing.MarginMobile,
                        vertical = AssetFlowSpacing.Xl,
                    ),
            ) {
                Text(
                    text = "Operate shared assets with clarity",
                    style = MaterialTheme.typography.headlineMedium,
                    color = colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(AssetFlowSpacing.Sm))

                Text(
                    text = "See availability, coordinate bookings, and stay ahead of maintenance from one mobile workspace.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = colorScheme.onSurfaceVariant,
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(AssetFlowSpacing.Xl))

                LandingCapabilityRow(
                    icon = Icons.Outlined.Inventory2,
                    title = "Browse assets",
                    description = "Find equipment by category, location, and availability status.",
                )

                Spacer(modifier = Modifier.height(AssetFlowSpacing.Md))

                LandingCapabilityRow(
                    icon = Icons.Outlined.EventAvailable,
                    title = "Book resources",
                    description = "Request time slots, track approvals, and avoid conflicts.",
                )

                Spacer(modifier = Modifier.height(AssetFlowSpacing.Md))

                LandingCapabilityRow(
                    icon = Icons.Outlined.Build,
                    title = "Track maintenance",
                    description = "Monitor due, overdue, and completed work in one place.",
                )

                Spacer(modifier = Modifier.height(AssetFlowSpacing.Xl))

                AssetFlowButton(
                    text = "Log in",
                    onClick = onLoginClick,
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(AssetFlowSpacing.Sm))

                AssetFlowButton(
                    text = "Create account",
                    onClick = onCreateAccountClick,
                    modifier = Modifier.fillMaxWidth(),
                    variant = AssetFlowButtonVariant.Secondary,
                )

                Spacer(modifier = Modifier.height(AssetFlowSpacing.Lg))

                Text(
                    text = "Built for teams that share equipment across campus and the workplace.",
                    style = MaterialTheme.typography.bodySmall,
                    color = colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
private fun LandingHero(
    modifier: Modifier = Modifier,
) {
    val colorScheme = MaterialTheme.colorScheme
    val heroBrush = Brush.verticalGradient(
        colors = listOf(
            colorScheme.primary,
            colorScheme.primaryContainer,
        ),
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(heroBrush)
            .padding(
                horizontal = AssetFlowSpacing.MarginMobile,
                vertical = AssetFlowSpacing.Xl + AssetFlowSpacing.Md,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(
                modifier = Modifier.size(72.dp),
                shape = CircleShape,
                color = colorScheme.onPrimary.copy(alpha = 0.16f),
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Surface(
                        modifier = Modifier.size(56.dp),
                        shape = CircleShape,
                        color = colorScheme.onPrimary,
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "AF",
                                style = MaterialTheme.typography.titleLarge,
                                color = colorScheme.primary,
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(AssetFlowSpacing.Md))

            Text(
                text = "AssetFlow",
                style = MaterialTheme.typography.headlineLarge,
                color = colorScheme.onPrimary,
            )

            Spacer(modifier = Modifier.height(AssetFlowSpacing.Xs))

            Text(
                text = "Shared asset management",
                style = MaterialTheme.typography.labelLarge,
                color = colorScheme.onPrimary.copy(alpha = 0.88f),
            )
        }
    }
}

@Composable
private fun LandingCapabilityRow(
    icon: ImageVector,
    title: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    val colorScheme = MaterialTheme.colorScheme

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        color = colorScheme.surfaceContainerLow,
    ) {
        Row(
            modifier = Modifier.padding(AssetFlowSpacing.Md),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Surface(
                modifier = Modifier.size(AssetFlowSpacing.Xl + AssetFlowSpacing.Sm),
                shape = CircleShape,
                color = colorScheme.primaryContainer,
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(AssetFlowSpacing.Lg),
                    )
                }
            }

            Spacer(modifier = Modifier.width(AssetFlowSpacing.Md))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = colorScheme.onSurface,
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = AssetFlowSpacing.Xs),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LandingScreenPreview() {
    AssetFlowTheme(darkTheme = false) {
        LandingScreen(
            onLoginClick = {},
            onCreateAccountClick = {},
        )
    }
}

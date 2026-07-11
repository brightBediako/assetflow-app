package com.assetflow.mobile.features.profile.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.assetflow.mobile.core.domain.model.UserRole
import com.assetflow.mobile.core.ui.components.AssetFlowButton
import com.assetflow.mobile.core.ui.components.AssetFlowButtonVariant
import com.assetflow.mobile.core.ui.components.AssetFlowCard
import com.assetflow.mobile.core.ui.components.AssetFlowTopAppBar
import com.assetflow.mobile.core.ui.components.SectionHeader
import com.assetflow.mobile.core.ui.components.UserAvatar
import com.assetflow.mobile.core.ui.theme.AssetFlowCornerRadius
import com.assetflow.mobile.core.ui.theme.AssetFlowSpacing
import com.assetflow.mobile.core.ui.theme.AssetFlowTheme

@Composable
fun ProfileRoute(
    onLogoutClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState = remember { loadProfileUiState() }

    ProfileScreen(
        uiState = uiState,
        onLogoutClick = onLogoutClick,
        onSettingsClick = onSettingsClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileShellScreen(
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            AssetFlowTopAppBar(
                title = "Profile",
                onBackClick = onBackClick,
            )
        },
    ) { paddingValues ->
        ProfileRoute(
            onLogoutClick = onLogoutClick,
            onSettingsClick = onSettingsClick,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        )
    }
}

@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onLogoutClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = AssetFlowSpacing.MarginMobile),
        contentPadding = PaddingValues(vertical = AssetFlowSpacing.MarginMobile),
        verticalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Md),
    ) {
        item {
            ProfileHeader(uiState = uiState)
        }

        item {
            SectionHeader(title = "Account")
        }

        item {
            AssetFlowCard {
                ProfileDetailLine(label = "Organization", value = uiState.organizationName)
                ProfileDetailLine(label = "Email", value = uiState.email)
                ProfileDetailLine(label = "Role", value = uiState.roleLabel)
                ProfileDetailLine(label = "User ID", value = uiState.userId)
            }
        }

        item {
            SectionHeader(title = "Activity")
        }

        item {
            AssetFlowCard {
                ProfileDetailLine(
                    label = "Upcoming bookings",
                    value = uiState.upcomingBookingsCount.toString(),
                )
                ProfileDetailLine(
                    label = "Past bookings",
                    value = uiState.pastBookingsCount.toString(),
                )
                Text(
                    text = if (uiState.canManageApprovals) {
                        "You can review ${uiState.pendingApprovalsCount} pending approval requests."
                    } else {
                        "Booking approvals are managed by admins and managers."
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = AssetFlowSpacing.Sm),
                )
            }
        }

        item {
            SectionHeader(title = "App")
        }

        item {
            AssetFlowCard(
                onClick = onSettingsClick,
            ) {
                androidx.compose.foundation.layout.Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    androidx.compose.foundation.layout.Row(
                        horizontalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Sm),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                        )
                        Column {
                            Text(
                                text = "Settings",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                            Text(
                                text = "Notifications, reminders, and preferences",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                    Icon(
                        imageVector = Icons.Outlined.ChevronRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }

        item {
            AssetFlowCard {
                ProfileDetailLine(label = "Version", value = "AssetFlow Mobile ${uiState.appVersion}")
                Text(
                    text = "Need help? Email ${uiState.supportEmail}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = AssetFlowSpacing.Sm),
                )
            }
        }

        item {
            AssetFlowButton(
                text = "Log out",
                onClick = onLogoutClick,
                modifier = Modifier.fillMaxWidth(),
                variant = AssetFlowButtonVariant.Destructive,
            )
        }
    }
}

@Composable
private fun ProfileHeader(
    uiState: ProfileUiState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Sm),
    ) {
        UserAvatar(fullName = uiState.fullName, size = 88.dp)
        Text(
            text = uiState.fullName,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            text = uiState.email,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        UserRoleBadge(role = uiState.role, label = uiState.roleLabel)
        Text(
            text = uiState.organizationName,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
private fun UserRoleBadge(
    role: UserRole,
    label: String,
    modifier: Modifier = Modifier,
) {
    val (containerColor, contentColor) = when (role) {
        UserRole.Admin -> MaterialTheme.colorScheme.tertiaryContainer to MaterialTheme.colorScheme.onTertiaryContainer
        UserRole.Manager -> MaterialTheme.colorScheme.primaryContainer to MaterialTheme.colorScheme.onPrimaryContainer
        UserRole.Staff -> MaterialTheme.colorScheme.secondaryContainer to MaterialTheme.colorScheme.onSecondaryContainer
    }

    Surface(
        modifier = modifier,
        color = containerColor,
        shape = RoundedCornerShape(AssetFlowCornerRadius.Full),
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(
                horizontal = AssetFlowSpacing.Md - AssetFlowSpacing.Xs,
                vertical = AssetFlowSpacing.Xs,
            ),
            style = MaterialTheme.typography.labelMedium,
            color = contentColor,
        )
    }
}

@Composable
private fun ProfileDetailLine(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = AssetFlowSpacing.Xs),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = AssetFlowSpacing.Xs),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    AssetFlowTheme {
        ProfileScreen(
            uiState = loadProfileUiState(),
            onLogoutClick = {},
            onSettingsClick = {},
        )
    }
}

package com.assetflow.mobile.features.settings.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.assetflow.mobile.core.data.mock.MockSettings
import com.assetflow.mobile.core.data.mock.MockSettingsStore
import com.assetflow.mobile.core.domain.model.ThemePreference
import com.assetflow.mobile.core.domain.model.displayLabel
import com.assetflow.mobile.core.ui.components.AssetFlowCard
import com.assetflow.mobile.core.ui.components.SectionHeader
import com.assetflow.mobile.core.ui.components.SettingsToggleRow
import com.assetflow.mobile.core.ui.theme.AssetFlowSpacing
import com.assetflow.mobile.core.ui.theme.AssetFlowTheme

@Composable
fun SettingsRoute(
    modifier: Modifier = Modifier,
) {
    val settings by MockSettingsStore::settings
    val uiState = remember(settings) { loadSettingsUiState(settings) }

    SettingsScreen(
        uiState = uiState,
        onBookingApprovalNotificationsChange = MockSettingsStore::setBookingApprovalNotifications,
        onReturnReminderNotificationsChange = MockSettingsStore::setReturnReminderNotifications,
        onMaintenanceAlertNotificationsChange = MockSettingsStore::setMaintenanceAlertNotifications,
        onSystemAnnouncementNotificationsChange = MockSettingsStore::setSystemAnnouncementNotifications,
        onReturnDueRemindersChange = MockSettingsStore::setReturnDueReminders,
        onMaintenanceDueRemindersChange = MockSettingsStore::setMaintenanceDueReminders,
        onBookingStartRemindersChange = MockSettingsStore::setBookingStartReminders,
        onThemePreferenceChange = MockSettingsStore::setThemePreference,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsShellScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Settings") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
            )
        },
    ) { paddingValues ->
        SettingsRoute(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SettingsScreen(
    uiState: SettingsUiState,
    onBookingApprovalNotificationsChange: (Boolean) -> Unit,
    onReturnReminderNotificationsChange: (Boolean) -> Unit,
    onMaintenanceAlertNotificationsChange: (Boolean) -> Unit,
    onSystemAnnouncementNotificationsChange: (Boolean) -> Unit,
    onReturnDueRemindersChange: (Boolean) -> Unit,
    onMaintenanceDueRemindersChange: (Boolean) -> Unit,
    onBookingStartRemindersChange: (Boolean) -> Unit,
    onThemePreferenceChange: (ThemePreference) -> Unit,
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
            SectionHeader(title = "Notifications")
        }

        item {
            AssetFlowCard {
                SettingsToggleRow(
                    title = "Booking approvals",
                    description = "Alerts when a booking needs your review.",
                    checked = uiState.bookingApprovalNotifications,
                    onCheckedChange = onBookingApprovalNotificationsChange,
                )
                SettingsToggleRow(
                    title = "Return reminders",
                    description = "Notifications for assets due back soon.",
                    checked = uiState.returnReminderNotifications,
                    onCheckedChange = onReturnReminderNotificationsChange,
                )
                SettingsToggleRow(
                    title = "Maintenance alerts",
                    description = "Updates for scheduled or overdue maintenance.",
                    checked = uiState.maintenanceAlertNotifications,
                    onCheckedChange = onMaintenanceAlertNotificationsChange,
                )
                SettingsToggleRow(
                    title = "System announcements",
                    description = "Organization-wide updates from admins.",
                    checked = uiState.systemAnnouncementNotifications,
                    onCheckedChange = onSystemAnnouncementNotificationsChange,
                )
            }
        }

        item {
            SectionHeader(title = "Reminders")
        }

        item {
            AssetFlowCard {
                SettingsToggleRow(
                    title = "Return due reminders",
                    description = "Remind borrowers before an asset return date.",
                    checked = uiState.returnDueReminders,
                    onCheckedChange = onReturnDueRemindersChange,
                )
                SettingsToggleRow(
                    title = "Maintenance due reminders",
                    description = "Advance notice before maintenance work is due.",
                    checked = uiState.maintenanceDueReminders,
                    onCheckedChange = onMaintenanceDueRemindersChange,
                )
                SettingsToggleRow(
                    title = "Booking start reminders",
                    description = "Notify when an approved booking is about to begin.",
                    checked = uiState.bookingStartReminders,
                    onCheckedChange = onBookingStartRemindersChange,
                )
            }
        }

        item {
            SectionHeader(title = "Appearance")
        }

        item {
            AssetFlowCard {
                Text(
                    text = "Theme",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = "Choose how AssetFlow looks on this device.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = AssetFlowSpacing.Xs),
                )
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = AssetFlowSpacing.Sm),
                    horizontalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Sm),
                    verticalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Sm),
                ) {
                    ThemePreference.entries.forEach { preference ->
                        FilterChip(
                            selected = uiState.themePreference == preference,
                            onClick = { onThemePreferenceChange(preference) },
                            label = { Text(text = preference.displayLabel()) },
                        )
                    }
                }
                Text(
                    text = "Theme preference is saved for this prototype session only.",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = AssetFlowSpacing.Sm),
                )
            }
        }

        item {
            SectionHeader(title = "About AssetFlow")
        }

        item {
            AssetFlowCard {
                Text(
                    text = "AssetFlow Mobile",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = "Version ${uiState.appVersion}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = AssetFlowSpacing.Xs),
                )
                Text(
                    text = uiState.aboutDescription,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = AssetFlowSpacing.Sm),
                )
                Text(
                    text = "Support: ${uiState.supportEmail}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = AssetFlowSpacing.Sm),
                )
                Text(
                    text = "Privacy policy and terms of use will be linked here after backend launch.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = AssetFlowSpacing.Sm),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    AssetFlowTheme {
        SettingsScreen(
            uiState = loadSettingsUiState(MockSettings.default),
            onBookingApprovalNotificationsChange = {},
            onReturnReminderNotificationsChange = {},
            onMaintenanceAlertNotificationsChange = {},
            onSystemAnnouncementNotificationsChange = {},
            onReturnDueRemindersChange = {},
            onMaintenanceDueRemindersChange = {},
            onBookingStartRemindersChange = {},
            onThemePreferenceChange = {},
        )
    }
}

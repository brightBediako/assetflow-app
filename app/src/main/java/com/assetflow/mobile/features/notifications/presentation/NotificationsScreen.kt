package com.assetflow.mobile.features.notifications.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.assetflow.mobile.core.data.mock.MockContentStateStore
import com.assetflow.mobile.core.data.mock.MockNotificationStore
import com.assetflow.mobile.core.domain.model.AppNotification
import com.assetflow.mobile.core.domain.model.NotificationType
import com.assetflow.mobile.core.ui.components.AssetFlowButton
import com.assetflow.mobile.core.ui.components.AssetFlowButtonVariant
import com.assetflow.mobile.core.ui.components.AssetFlowCard
import com.assetflow.mobile.core.ui.components.EmptyState
import com.assetflow.mobile.core.ui.components.NotificationCard
import com.assetflow.mobile.core.ui.components.SectionHeader
import com.assetflow.mobile.core.ui.components.SupportStateHost
import com.assetflow.mobile.core.ui.theme.AssetFlowSpacing
import com.assetflow.mobile.core.ui.theme.AssetFlowTheme

@Composable
fun NotificationsRoute(
    onBookingApprovalsClick: () -> Unit,
    onBookingsClick: () -> Unit,
    onMaintenanceClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val notifications by MockNotificationStore::notifications
    val contentState by MockContentStateStore::contentState
    val uiState = remember(notifications) { loadNotificationsUiState() }

    SupportStateHost(
        state = contentState,
        emptyTitle = "No notifications",
        emptyDescription = "Booking, return, maintenance, and system updates will appear here.",
        onRetry = { MockContentStateStore.reset() },
        modifier = modifier,
    ) {
        NotificationsScreen(
            uiState = uiState,
            onMarkAllReadClick = { MockNotificationStore.markAllAsRead() },
            onNotificationClick = { notification ->
                MockNotificationStore.markAsRead(notification.id)
                when (notification.type) {
                    NotificationType.BookingApproval -> {
                        if (!notification.title.contains("approved", ignoreCase = true)) {
                            onBookingApprovalsClick()
                        }
                    }
                    NotificationType.ReturnReminder -> onBookingsClick()
                    NotificationType.MaintenanceAlert -> onMaintenanceClick()
                    NotificationType.SystemAnnouncement -> Unit
                }
            },
        )
    }
}

@Composable
fun NotificationsScreen(
    uiState: NotificationsUiState,
    onMarkAllReadClick: () -> Unit,
    onNotificationClick: (AppNotification) -> Unit,
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
            NotificationsSummaryCard(
                unreadCount = uiState.unreadCount,
                totalCount = uiState.totalCount,
                onMarkAllReadClick = onMarkAllReadClick,
            )
        }

        if (uiState.totalCount == 0) {
            item {
                EmptyState(
                    title = "No notifications",
                    description = "Booking, return, maintenance, and system updates will appear here.",
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        } else {
            notificationSection(
                title = "Booking approvals",
                description = "Requests and decisions that need your attention.",
                items = uiState.bookingApprovalNotifications,
                onNotificationClick = onNotificationClick,
            )
            notificationSection(
                title = "Return reminders",
                description = "Assets due back soon or overdue for return.",
                items = uiState.returnReminderNotifications,
                onNotificationClick = onNotificationClick,
            )
            notificationSection(
                title = "Maintenance alerts",
                description = "Upcoming or overdue maintenance work.",
                items = uiState.maintenanceAlertNotifications,
                onNotificationClick = onNotificationClick,
            )
            notificationSection(
                title = "System announcements",
                description = "Organization-wide updates and reminders.",
                items = uiState.systemAnnouncementNotifications,
                onNotificationClick = onNotificationClick,
            )
        }
    }
}

private fun androidx.compose.foundation.lazy.LazyListScope.notificationSection(
    title: String,
    description: String,
    items: List<AppNotification>,
    onNotificationClick: (AppNotification) -> Unit,
) {
    if (items.isEmpty()) return

    item {
        SectionHeader(title = title)
        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }

    items(
        items = items,
        key = { notification -> notification.id },
    ) { notification ->
        NotificationCard(
            notification = notification,
            onClick = { onNotificationClick(notification) },
        )
    }
}

@Composable
private fun NotificationsSummaryCard(
    unreadCount: Int,
    totalCount: Int,
    onMarkAllReadClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AssetFlowCard(modifier = modifier.fillMaxWidth()) {
        Text(
            text = if (unreadCount > 0) {
                "$unreadCount unread of $totalCount notifications"
            } else {
                "All caught up"
            },
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = if (unreadCount > 0) {
                "Review action-required items below."
            } else {
                "You have read all $totalCount notifications."
            },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = AssetFlowSpacing.Xs),
        )
        if (unreadCount > 0) {
            AssetFlowButton(
                text = "Mark all as read",
                onClick = onMarkAllReadClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AssetFlowSpacing.Sm),
                variant = AssetFlowButtonVariant.Secondary,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NotificationsScreenPreview() {
    AssetFlowTheme {
        NotificationsScreen(
            uiState = loadNotificationsUiState(),
            onMarkAllReadClick = {},
            onNotificationClick = {},
        )
    }
}

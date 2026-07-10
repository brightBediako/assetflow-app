package com.assetflow.mobile.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Campaign
import androidx.compose.material.icons.outlined.EventAvailable
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.assetflow.mobile.core.data.mock.MockDataRepository
import com.assetflow.mobile.core.domain.model.AppNotification
import com.assetflow.mobile.core.domain.model.NotificationType
import com.assetflow.mobile.core.domain.model.displayLabel
import com.assetflow.mobile.core.ui.theme.AssetFlowCornerRadius
import com.assetflow.mobile.core.ui.theme.AssetFlowSpacing
import com.assetflow.mobile.core.ui.theme.AssetFlowStatusColors
import com.assetflow.mobile.core.ui.theme.AssetFlowTheme

@Composable
fun NotificationCard(
    notification: AppNotification,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val typeColors = notificationTypeColors(notification.type)
    val containerColor = if (notification.isRead) {
        MaterialTheme.colorScheme.surfaceContainerLow
    } else {
        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
    }

    Surface(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        shape = RoundedCornerShape(AssetFlowCornerRadius.Md),
        color = containerColor,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AssetFlowSpacing.Md),
            horizontalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Md),
            verticalAlignment = Alignment.Top,
        ) {
            NotificationTypeIcon(
                type = notification.type,
                containerColor = typeColors.first,
                contentColor = typeColors.second,
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Xs),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = notification.type.displayLabel(),
                        style = MaterialTheme.typography.labelMedium,
                        color = typeColors.second,
                    )
                    if (!notification.isRead) {
                        UnreadIndicator()
                    }
                }
                Text(
                    text = notification.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = if (notification.isRead) FontWeight.Normal else FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = notification.message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = notification.timestamp,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun NotificationTypeIcon(
    type: NotificationType,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .background(
                color = containerColor,
                shape = RoundedCornerShape(AssetFlowCornerRadius.Md),
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = notificationTypeIcon(type),
            contentDescription = type.displayLabel(),
            tint = contentColor,
            modifier = Modifier.size(20.dp),
        )
    }
}

@Composable
private fun UnreadIndicator(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(8.dp)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
            ),
    )
}

private fun notificationTypeIcon(type: NotificationType): ImageVector = when (type) {
    NotificationType.BookingApproval -> Icons.Outlined.EventAvailable
    NotificationType.ReturnReminder -> Icons.Outlined.Schedule
    NotificationType.MaintenanceAlert -> Icons.Outlined.Build
    NotificationType.SystemAnnouncement -> Icons.Outlined.Campaign
}

private fun notificationTypeColors(type: NotificationType): Pair<Color, Color> = when (type) {
    NotificationType.BookingApproval ->
        AssetFlowStatusColors.PendingContainer to AssetFlowStatusColors.OnPending
    NotificationType.ReturnReminder ->
        AssetFlowStatusColors.BookedContainer to AssetFlowStatusColors.Booked
    NotificationType.MaintenanceAlert ->
        AssetFlowStatusColors.MaintenanceDueContainer to AssetFlowStatusColors.MaintenanceDue
    NotificationType.SystemAnnouncement ->
        AssetFlowStatusColors.CompletedContainer to AssetFlowStatusColors.Completed
}

@Preview(showBackground = true)
@Composable
private fun NotificationCardPreview() {
    AssetFlowTheme {
        NotificationCard(
            notification = MockDataRepository.notifications.first(),
            onClick = {},
            modifier = Modifier.padding(AssetFlowSpacing.Md),
        )
    }
}

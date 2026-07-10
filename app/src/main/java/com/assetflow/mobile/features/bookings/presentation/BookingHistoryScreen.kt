package com.assetflow.mobile.features.bookings.presentation

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.assetflow.mobile.core.data.mock.MockBookingStore
import com.assetflow.mobile.core.data.mock.MockDataRepository
import com.assetflow.mobile.core.ui.components.AssetFlowButton
import com.assetflow.mobile.core.ui.components.BookingCard
import com.assetflow.mobile.core.ui.components.EmptyState
import com.assetflow.mobile.core.ui.components.SectionHeader
import com.assetflow.mobile.core.ui.theme.AssetFlowSpacing
import com.assetflow.mobile.core.ui.theme.AssetFlowTheme

@Composable
fun BookingHistoryRoute(
    onPendingApprovalsClick: () -> Unit,
    onBrowseAssetsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val bookings by MockBookingStore::bookings
    val uiState = loadBookingHistoryUiState()

    BookingHistoryScreen(
        uiState = uiState,
        onPendingApprovalsClick = onPendingApprovalsClick,
        onBrowseAssetsClick = onBrowseAssetsClick,
        onCancelBookingClick = { bookingId ->
            MockBookingStore.cancelBooking(bookingId)
        },
        modifier = modifier,
    )
}

@Composable
fun BookingHistoryScreen(
    uiState: BookingHistoryUiState,
    onPendingApprovalsClick: () -> Unit,
    onBrowseAssetsClick: () -> Unit,
    onCancelBookingClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val hasBookings = uiState.upcomingBookings.isNotEmpty() || uiState.pastBookings.isNotEmpty()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = AssetFlowSpacing.MarginMobile),
        contentPadding = PaddingValues(vertical = AssetFlowSpacing.MarginMobile),
        verticalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Md),
    ) {
        if (uiState.canManageApprovals) {
            item {
                AssetFlowCardAction(
                    title = "${uiState.pendingApprovalCount} pending approvals",
                    description = "Review and approve booking requests from staff.",
                    actionLabel = "Review approvals",
                    onClick = onPendingApprovalsClick,
                )
            }
        }

        item {
            SectionHeader(
                title = "Upcoming bookings",
                actionLabel = "Book asset",
                onActionClick = onBrowseAssetsClick,
            )
        }

        if (uiState.upcomingBookings.isEmpty()) {
            item {
                EmptyState(
                    title = "No upcoming bookings",
                    description = "Browse assets to request your next reservation.",
                    actionLabel = "Browse assets",
                    onAction = onBrowseAssetsClick,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        } else {
            items(
                items = uiState.upcomingBookings,
                key = { item -> item.booking.id },
            ) { item ->
                BookingCard(
                    assetName = item.assetName,
                    assetLocation = item.assetLocation,
                    booking = item.booking,
                    actionLabel = if (canCancelBooking(item.booking)) "Cancel booking" else null,
                    onActionClick = if (canCancelBooking(item.booking)) {
                        { onCancelBookingClick(item.booking.id) }
                    } else {
                        null
                    },
                )
            }
        }

        item {
            SectionHeader(title = "Past bookings")
        }

        if (uiState.pastBookings.isEmpty()) {
            item {
                Text(
                    text = "Completed and cancelled bookings will appear here.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        } else {
            items(
                items = uiState.pastBookings,
                key = { item -> item.booking.id },
            ) { item ->
                BookingCard(
                    assetName = item.assetName,
                    assetLocation = item.assetLocation,
                    booking = item.booking,
                )
            }
        }

        if (!hasBookings) {
            item {
                AssetFlowButton(
                    text = "Browse assets",
                    onClick = onBrowseAssetsClick,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
private fun AssetFlowCardAction(
    title: String,
    description: String,
    actionLabel: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    com.assetflow.mobile.core.ui.components.AssetFlowCard(modifier = modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = AssetFlowSpacing.Xs),
        )
        AssetFlowButton(
            text = actionLabel,
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AssetFlowSpacing.Sm),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BookingHistoryScreenPreview() {
    AssetFlowTheme {
        BookingHistoryScreen(
            uiState = loadBookingHistoryUiState(),
            onPendingApprovalsClick = {},
            onBrowseAssetsClick = {},
            onCancelBookingClick = {},
        )
    }
}

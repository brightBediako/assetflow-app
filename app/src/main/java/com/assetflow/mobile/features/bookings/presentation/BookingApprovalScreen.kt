package com.assetflow.mobile.features.bookings.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.assetflow.mobile.core.data.mock.MockBookingStore
import com.assetflow.mobile.core.data.mock.MockDataRepository
import com.assetflow.mobile.core.domain.model.Booking
import com.assetflow.mobile.core.ui.components.AssetFlowButton
import com.assetflow.mobile.core.ui.components.AssetFlowButtonVariant
import com.assetflow.mobile.core.ui.components.BookingCard
import com.assetflow.mobile.core.ui.components.EmptyState
import com.assetflow.mobile.core.ui.components.SectionHeader
import com.assetflow.mobile.core.ui.theme.AssetFlowSpacing
import com.assetflow.mobile.core.ui.theme.AssetFlowTheme

data class BookingApprovalUiState(
    val pendingApprovals: List<BookingListItem>,
)

@Composable
fun BookingApprovalRoute(
    modifier: Modifier = Modifier,
) {
    val bookings by MockBookingStore::bookings
    val pendingApprovals = MockDataRepository.getPendingApprovals().toListItems()
    var selectedBooking by remember { mutableStateOf<BookingListItem?>(null) }

    BookingApprovalScreen(
        uiState = BookingApprovalUiState(pendingApprovals = pendingApprovals),
        selectedBooking = selectedBooking,
        onBookingClick = { item -> selectedBooking = item },
        onDismissDetail = { selectedBooking = null },
        onApproveClick = { bookingId ->
            MockBookingStore.approveBooking(bookingId)
            selectedBooking = null
        },
        onRejectClick = { bookingId ->
            MockBookingStore.rejectBooking(bookingId)
            selectedBooking = null
        },
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingApprovalScreen(
    uiState: BookingApprovalUiState,
    selectedBooking: BookingListItem?,
    onBookingClick: (BookingListItem) -> Unit,
    onDismissDetail: () -> Unit,
    onApproveClick: (String) -> Unit,
    onRejectClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = AssetFlowSpacing.MarginMobile),
        contentPadding = PaddingValues(vertical = AssetFlowSpacing.MarginMobile),
        verticalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Md),
    ) {
        item {
            SectionHeader(title = "Pending approvals")
        }

        if (uiState.pendingApprovals.isEmpty()) {
            item {
                EmptyState(
                    title = "No pending approvals",
                    description = "New booking requests from staff will appear here for review.",
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        } else {
            items(
                items = uiState.pendingApprovals,
                key = { item -> item.booking.id },
            ) { item ->
                BookingCard(
                    assetName = item.assetName,
                    assetLocation = item.assetLocation,
                    booking = item.booking,
                    onClick = { onBookingClick(item) },
                )
            }
        }
    }

    if (selectedBooking != null) {
        ModalBottomSheet(
            onDismissRequest = onDismissDetail,
            sheetState = sheetState,
        ) {
            ApprovalDetailSheet(
                item = selectedBooking,
                onApproveClick = { onApproveClick(selectedBooking.booking.id) },
                onRejectClick = { onRejectClick(selectedBooking.booking.id) },
            )
        }
    }
}

@Composable
private fun ApprovalDetailSheet(
    item: BookingListItem,
    onApproveClick: () -> Unit,
    onRejectClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val booking = item.booking

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = AssetFlowSpacing.MarginMobile)
            .padding(bottom = AssetFlowSpacing.Xl),
        verticalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Md),
    ) {
        Text(
            text = "Approval request",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
        DetailRow(label = "Requester", value = booking.requesterName)
        DetailRow(label = "Asset", value = item.assetName)
        DetailRow(label = "Location", value = item.assetLocation)
        DetailRow(
            label = "Date & time",
            value = "${booking.startDate} to ${booking.endDate} · ${booking.startTime} – ${booking.endTime}",
        )
        DetailRow(label = "Purpose", value = booking.purpose)
        if (booking.notes.isNotBlank()) {
            DetailRow(label = "Notes", value = booking.notes)
        }

        AssetFlowButton(
            text = "Approve",
            onClick = onApproveClick,
            modifier = Modifier.fillMaxWidth(),
        )
        AssetFlowButton(
            text = "Reject",
            onClick = onRejectClick,
            modifier = Modifier.fillMaxWidth(),
            variant = AssetFlowButtonVariant.Destructive,
        )
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
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
private fun BookingApprovalScreenPreview() {
    AssetFlowTheme {
        BookingApprovalScreen(
            uiState = BookingApprovalUiState(
                pendingApprovals = MockDataRepository.getPendingApprovals().toListItems(),
            ),
            selectedBooking = null,
            onBookingClick = {},
            onDismissDetail = {},
            onApproveClick = {},
            onRejectClick = {},
        )
    }
}

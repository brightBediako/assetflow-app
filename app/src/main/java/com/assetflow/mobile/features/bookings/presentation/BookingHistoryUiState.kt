package com.assetflow.mobile.features.bookings.presentation

import com.assetflow.mobile.core.data.mock.MockDataRepository

data class BookingHistoryUiState(
    val upcomingBookings: List<BookingListItem>,
    val pastBookings: List<BookingListItem>,
    val pendingApprovalCount: Int,
    val canManageApprovals: Boolean,
    val cancelledBookingIds: Set<String> = emptySet(),
)

fun loadBookingHistoryUiState(): BookingHistoryUiState {
    val upcoming = MockDataRepository.getUpcomingBookingsForCurrentUser().toListItems()
    val past = MockDataRepository.getPastBookingsForCurrentUser().toListItems()

    return BookingHistoryUiState(
        upcomingBookings = upcoming,
        pastBookings = past,
        pendingApprovalCount = MockDataRepository.getPendingApprovals().size,
        canManageApprovals = MockDataRepository.canManageApprovals(),
    )
}

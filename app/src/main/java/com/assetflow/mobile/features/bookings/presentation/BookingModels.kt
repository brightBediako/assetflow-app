package com.assetflow.mobile.features.bookings.presentation

import com.assetflow.mobile.core.data.mock.MockDataRepository
import com.assetflow.mobile.core.domain.model.Asset
import com.assetflow.mobile.core.domain.model.Booking

data class BookingListItem(
    val booking: Booking,
    val assetName: String,
    val assetLocation: String,
)

fun Booking.toListItem(): BookingListItem {
    val asset = MockDataRepository.getAssetById(assetId)
    return BookingListItem(
        booking = this,
        assetName = asset?.name ?: "Unknown asset",
        assetLocation = asset?.location ?: "Unknown location",
    )
}

fun List<Booking>.toListItems(): List<BookingListItem> = map { it.toListItem() }

fun canCancelBooking(booking: Booking): Boolean =
    booking.status == com.assetflow.mobile.core.domain.model.OperationalStatus.Pending ||
        booking.status == com.assetflow.mobile.core.domain.model.OperationalStatus.Approved

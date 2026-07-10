package com.assetflow.mobile.features.assets.presentation

import com.assetflow.mobile.core.data.mock.MockDataRepository
import com.assetflow.mobile.core.domain.model.Asset
import com.assetflow.mobile.core.domain.model.Booking
import com.assetflow.mobile.core.domain.model.MaintenanceRecord
import com.assetflow.mobile.core.domain.model.MaintenanceStatus
import com.assetflow.mobile.core.domain.model.OperationalStatus
import com.assetflow.mobile.core.domain.model.displayLabel

data class AssetDetailsUiState(
    val asset: Asset,
    val currentBooking: Booking?,
    val maintenanceRecords: List<MaintenanceRecord>,
    val canBook: Boolean,
    val bookingUnavailableReason: String?,
) {
    val openMaintenanceRecords: List<MaintenanceRecord> =
        maintenanceRecords.filter { it.status != MaintenanceStatus.Completed }

    val hasMaintenanceHistory: Boolean = maintenanceRecords.isNotEmpty()
}

fun loadAssetDetailsUiState(assetId: String): AssetDetailsUiState? {
    val asset = MockDataRepository.getAssetById(assetId) ?: return null
    val bookings = MockDataRepository.getBookingsForAsset(assetId)
    val currentBooking = asset.activeBookingId?.let(MockDataRepository::getBookingById)
        ?: bookings.firstOrNull { booking ->
            booking.status == OperationalStatus.Active ||
                booking.status == OperationalStatus.Approved ||
                booking.status == OperationalStatus.Pending
        }
    val maintenanceRecords = MockDataRepository.getMaintenanceForAsset(assetId)
        .sortedBy { record -> maintenanceSortOrder(record.status) }

    val canBook = asset.status == OperationalStatus.Available
    val bookingUnavailableReason = if (canBook) {
        null
    } else {
        bookingUnavailableMessage(asset, currentBooking)
    }

    return AssetDetailsUiState(
        asset = asset,
        currentBooking = currentBooking,
        maintenanceRecords = maintenanceRecords,
        canBook = canBook,
        bookingUnavailableReason = bookingUnavailableReason,
    )
}

private fun maintenanceSortOrder(status: MaintenanceStatus): Int = when (status) {
    MaintenanceStatus.Overdue -> 0
    MaintenanceStatus.DueSoon -> 1
    MaintenanceStatus.Scheduled -> 2
    MaintenanceStatus.Completed -> 3
}

private fun bookingUnavailableMessage(asset: Asset, currentBooking: Booking?): String {
    val bookingDetail = currentBooking?.let { booking ->
        " by ${booking.requesterName} (${booking.startDate}, ${booking.startTime}–${booking.endTime})"
    }.orEmpty()

    return when (asset.status) {
        OperationalStatus.Booked ->
            "This asset is currently booked$bookingDetail."
        OperationalStatus.MaintenanceDue ->
            "Booking is paused until maintenance is completed."
        OperationalStatus.Unavailable ->
            "This asset is temporarily unavailable."
        OperationalStatus.Pending ->
            "A booking request is awaiting approval."
        OperationalStatus.Approved ->
            "An approved booking is scheduled$bookingDetail."
        OperationalStatus.Active ->
            "This asset is currently in active use$bookingDetail."
        else ->
            "This asset cannot be booked right now (${asset.status.displayLabel()})."
    }
}

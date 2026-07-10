package com.assetflow.mobile.core.data.mock

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.assetflow.mobile.core.domain.model.Booking
import com.assetflow.mobile.core.domain.model.OperationalStatus

/**
 * Mutable booking store for UI prototype interactions (submit, cancel, approve, reject).
 */
object MockBookingStore {
    var bookings by mutableStateOf(MockBookings.bookings)
        private set

    fun submitBooking(booking: Booking) {
        bookings = bookings + booking
    }

    fun cancelBooking(bookingId: String) {
        bookings = bookings.map { booking ->
            if (booking.id == bookingId) {
                booking.copy(status = OperationalStatus.Cancelled)
            } else {
                booking
            }
        }
    }

    fun approveBooking(bookingId: String) {
        bookings = bookings.map { booking ->
            if (booking.id == bookingId) {
                booking.copy(status = OperationalStatus.Approved)
            } else {
                booking
            }
        }
    }

    fun rejectBooking(bookingId: String) {
        bookings = bookings.map { booking ->
            if (booking.id == bookingId) {
                booking.copy(status = OperationalStatus.Cancelled)
            } else {
                booking
            }
        }
    }

    fun hasConflict(
        assetId: String,
        startDate: String,
        endDate: String,
        excludeBookingId: String? = null,
    ): Boolean = bookings.any { booking ->
        booking.id != excludeBookingId &&
            booking.assetId == assetId &&
            booking.status in conflictStatuses &&
            datesOverlap(
                existingStart = booking.startDate,
                existingEnd = booking.endDate,
                requestedStart = startDate,
                requestedEnd = endDate,
            )
    }

    fun nextBookingId(): String {
        val maxId = bookings.mapNotNull { booking ->
            booking.id.removePrefix("booking-").toIntOrNull()
        }.maxOrNull() ?: 0
        return "booking-${maxId + 1}"
    }

    private val conflictStatuses = setOf(
        OperationalStatus.Pending,
        OperationalStatus.Approved,
        OperationalStatus.Active,
    )

    private fun datesOverlap(
        existingStart: String,
        existingEnd: String,
        requestedStart: String,
        requestedEnd: String,
    ): Boolean = requestedStart <= existingEnd && requestedEnd >= existingStart
}

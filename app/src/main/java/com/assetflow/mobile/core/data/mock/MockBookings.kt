package com.assetflow.mobile.core.data.mock

import com.assetflow.mobile.core.domain.model.Booking
import com.assetflow.mobile.core.domain.model.OperationalStatus

object MockBookings {
    val bookings = listOf(
        Booking(
            id = "booking-001",
            assetId = "asset-013",
            requesterId = "user-002",
            requesterName = "Kwesi Boateng",
            status = OperationalStatus.Pending,
            startDate = "2026-07-15",
            endDate = "2026-07-15",
            startTime = "09:00",
            endTime = "12:00",
            purpose = "Student orientation kiosk",
            notes = "Needs Apple Pencil in carrying case.",
        ),
        Booking(
            id = "booking-002",
            assetId = "asset-002",
            requesterId = "user-004",
            requesterName = "Daniel Osei",
            status = OperationalStatus.Approved,
            startDate = "2026-07-12",
            endDate = "2026-07-14",
            startTime = "08:30",
            endTime = "17:00",
            purpose = "Video editing workshop",
        ),
        Booking(
            id = "booking-003",
            assetId = "asset-006",
            requesterId = "user-001",
            requesterName = "Ama Mensah",
            status = OperationalStatus.Active,
            startDate = "2026-07-10",
            endDate = "2026-07-11",
            startTime = "07:00",
            endTime = "18:00",
            purpose = "Faculty field research transport",
            notes = "Driver: Daniel Osei",
        ),
        Booking(
            id = "booking-004",
            assetId = "asset-010",
            requesterId = "user-002",
            requesterName = "Kwesi Boateng",
            status = OperationalStatus.Active,
            startDate = "2026-07-10",
            endDate = "2026-07-10",
            startTime = "10:00",
            endTime = "16:00",
            purpose = "Graduation ceremony photography",
        ),
        Booking(
            id = "booking-005",
            assetId = "asset-003",
            requesterId = "user-001",
            requesterName = "Ama Mensah",
            status = OperationalStatus.Completed,
            startDate = "2026-07-03",
            endDate = "2026-07-03",
            startTime = "13:00",
            endTime = "17:00",
            purpose = "Department open day coverage",
        ),
        Booking(
            id = "booking-006",
            assetId = "asset-004",
            requesterId = "user-003",
            requesterName = "Efua Adjei",
            status = OperationalStatus.Cancelled,
            startDate = "2026-07-08",
            endDate = "2026-07-08",
            startTime = "14:00",
            endTime = "16:00",
            purpose = "Board meeting presentation",
            notes = "Cancelled due to maintenance hold on projector.",
        ),
        Booking(
            id = "booking-007",
            assetId = "asset-008",
            requesterId = "user-004",
            requesterName = "Daniel Osei",
            status = OperationalStatus.Pending,
            startDate = "2026-07-18",
            endDate = "2026-07-18",
            startTime = "18:00",
            endTime = "22:00",
            purpose = "Student talent showcase",
        ),
        Booking(
            id = "booking-008",
            assetId = "asset-011",
            requesterId = "user-002",
            requesterName = "Kwesi Boateng",
            status = OperationalStatus.Approved,
            startDate = "2026-07-20",
            endDate = "2026-07-20",
            startTime = "08:00",
            endTime = "20:00",
            purpose = "Career fair seating",
        ),
    )

    fun getBookingById(bookingId: String): Booking? = bookings.firstOrNull { it.id == bookingId }

    fun getBookingsForAsset(assetId: String): List<Booking> =
        bookings.filter { it.assetId == assetId }

    fun getBookingsForUser(userId: String): List<Booking> =
        bookings.filter { it.requesterId == userId }

    fun getPendingApprovals(): List<Booking> =
        bookings.filter { it.status == OperationalStatus.Pending }

    fun getUpcomingBookings(): List<Booking> = bookings.filter {
        it.status == OperationalStatus.Pending ||
            it.status == OperationalStatus.Approved ||
            it.status == OperationalStatus.Active
    }

    fun getPastBookings(): List<Booking> = bookings.filter {
        it.status == OperationalStatus.Completed ||
            it.status == OperationalStatus.Cancelled
    }
}

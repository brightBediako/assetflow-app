package com.assetflow.mobile.core.data.mock

import com.assetflow.mobile.core.domain.model.AppNotification
import com.assetflow.mobile.core.domain.model.Asset
import com.assetflow.mobile.core.domain.model.Booking
import com.assetflow.mobile.core.domain.model.BookingTrendPoint
import com.assetflow.mobile.core.domain.model.CategorySummary
import com.assetflow.mobile.core.domain.model.DashboardActivity
import com.assetflow.mobile.core.domain.model.DashboardSummary
import com.assetflow.mobile.core.domain.model.MaintenanceStatusCount
import com.assetflow.mobile.core.domain.model.OperationalStatusCount
import com.assetflow.mobile.core.domain.model.MaintenanceHistoryEntry
import com.assetflow.mobile.core.domain.model.MaintenanceRecord
import com.assetflow.mobile.core.domain.model.MaintenanceStatus
import com.assetflow.mobile.core.domain.model.OperationalStatus
import com.assetflow.mobile.core.domain.model.Organization
import com.assetflow.mobile.core.domain.model.User
import com.assetflow.mobile.core.domain.model.UserRole

/**
 * Central access point for UI prototype dummy data.
 * Feature screens should read from here instead of inventing local sample records.
 */
object MockDataRepository {
    val currentUser: User get() = MockUsers.currentUser

    val organization: Organization get() = MockUsers.organization

    val assets: List<Asset> get() = MockAssets.assets

    val bookings: List<Booking> get() = MockBookingStore.bookings

    val maintenanceRecords: List<MaintenanceRecord> get() = MockMaintenanceStore.records

    val notifications: List<AppNotification> get() = MockNotificationStore.notifications

    val dashboardSummary: DashboardSummary get() = MockDashboard.summary

    val recentActivity: List<DashboardActivity> get() = MockDashboard.recentActivity

    val categoryDistribution: List<CategorySummary> get() = MockDashboard.categoryDistribution

    val bookingTrend: List<BookingTrendPoint> get() = MockDashboard.bookingTrend

    val assetAvailabilityBreakdown: List<OperationalStatusCount> get() =
        MockDashboard.assetAvailabilityBreakdown

    val maintenanceStatusCounts: List<MaintenanceStatusCount> get() =
        MockDashboard.maintenanceStatusCounts

    fun getAssetById(assetId: String): Asset? = MockAssets.getAssetById(assetId)

    fun getBookingById(bookingId: String): Booking? =
        MockBookingStore.bookings.firstOrNull { it.id == bookingId }

    fun getMaintenanceById(maintenanceId: String): MaintenanceRecord? =
        MockMaintenanceStore.records.firstOrNull { it.id == maintenanceId }

    fun getNotificationById(notificationId: String): AppNotification? =
        MockNotificationStore.notifications.firstOrNull { it.id == notificationId }

    fun getUserById(userId: String): User? = MockUsers.getUserById(userId)

    fun getBookingsForAsset(assetId: String): List<Booking> =
        MockBookingStore.bookings.filter { it.assetId == assetId }

    fun getBookingsForCurrentUser(): List<Booking> =
        MockBookingStore.bookings.filter { it.requesterId == currentUser.id }

    fun getPendingApprovals(): List<Booking> =
        MockBookingStore.bookings.filter { it.status == OperationalStatus.Pending }

    fun getUpcomingBookings(): List<Booking> = MockBookingStore.bookings.filter {
        it.status == OperationalStatus.Pending ||
            it.status == OperationalStatus.Approved ||
            it.status == OperationalStatus.Active
    }

    fun getPastBookings(): List<Booking> = MockBookingStore.bookings.filter {
        it.status == OperationalStatus.Completed ||
            it.status == OperationalStatus.Cancelled
    }

    fun getUpcomingBookingsForCurrentUser(): List<Booking> =
        getBookingsForCurrentUser().filter {
            it.status == OperationalStatus.Pending ||
                it.status == OperationalStatus.Approved ||
                it.status == OperationalStatus.Active
        }

    fun getPastBookingsForCurrentUser(): List<Booking> =
        getBookingsForCurrentUser().filter {
            it.status == OperationalStatus.Completed ||
                it.status == OperationalStatus.Cancelled
        }

    fun canManageApprovals(): Boolean =
        currentUser.role == UserRole.Admin || currentUser.role == UserRole.Manager

    fun getMaintenanceForAsset(assetId: String): List<MaintenanceRecord> =
        MockMaintenanceStore.records.filter { it.assetId == assetId }

    fun getMaintenanceHistory(maintenanceId: String): List<MaintenanceHistoryEntry> =
        MockMaintenanceStore.history.filter { it.maintenanceId == maintenanceId }

    fun getAssetsByCategory(category: String): List<Asset> =
        MockAssets.getAssetsByCategory(category)

    fun getAssetsByStatus(status: OperationalStatus): List<Asset> =
        MockAssets.getAssetsByStatus(status)

    fun getMaintenanceByStatus(status: MaintenanceStatus): List<MaintenanceRecord> =
        MockMaintenanceStore.records.filter { it.status == status }

    fun getUnreadNotificationCount(): Int = MockNotificationStore.getUnreadCount()

    val assetCategories: List<String> = listOf("All") + MockAssets.categories
}

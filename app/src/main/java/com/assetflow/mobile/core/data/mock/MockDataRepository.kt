package com.assetflow.mobile.core.data.mock

import com.assetflow.mobile.core.domain.model.AppNotification
import com.assetflow.mobile.core.domain.model.Asset
import com.assetflow.mobile.core.domain.model.Booking
import com.assetflow.mobile.core.domain.model.CategorySummary
import com.assetflow.mobile.core.domain.model.DashboardActivity
import com.assetflow.mobile.core.domain.model.DashboardSummary
import com.assetflow.mobile.core.domain.model.MaintenanceHistoryEntry
import com.assetflow.mobile.core.domain.model.MaintenanceRecord
import com.assetflow.mobile.core.domain.model.MaintenanceStatus
import com.assetflow.mobile.core.domain.model.OperationalStatus
import com.assetflow.mobile.core.domain.model.Organization
import com.assetflow.mobile.core.domain.model.User

/**
 * Central access point for UI prototype dummy data.
 * Feature screens should read from here instead of inventing local sample records.
 */
object MockDataRepository {
    val currentUser: User get() = MockUsers.currentUser

    val organization: Organization get() = MockUsers.organization

    val assets: List<Asset> get() = MockAssets.assets

    val bookings: List<Booking> get() = MockBookings.bookings

    val maintenanceRecords: List<MaintenanceRecord> get() = MockMaintenance.records

    val notifications: List<AppNotification> get() = MockNotifications.notifications

    val dashboardSummary: DashboardSummary get() = MockDashboard.summary

    val recentActivity: List<DashboardActivity> get() = MockDashboard.recentActivity

    val categoryDistribution: List<CategorySummary> get() = MockDashboard.categoryDistribution

    fun getAssetById(assetId: String): Asset? = MockAssets.getAssetById(assetId)

    fun getBookingById(bookingId: String): Booking? = MockBookings.getBookingById(bookingId)

    fun getMaintenanceById(maintenanceId: String): MaintenanceRecord? =
        MockMaintenance.getMaintenanceById(maintenanceId)

    fun getNotificationById(notificationId: String): AppNotification? =
        MockNotifications.getNotificationById(notificationId)

    fun getUserById(userId: String): User? = MockUsers.getUserById(userId)

    fun getBookingsForAsset(assetId: String): List<Booking> =
        MockBookings.getBookingsForAsset(assetId)

    fun getBookingsForCurrentUser(): List<Booking> =
        MockBookings.getBookingsForUser(currentUser.id)

    fun getPendingApprovals(): List<Booking> = MockBookings.getPendingApprovals()

    fun getUpcomingBookings(): List<Booking> = MockBookings.getUpcomingBookings()

    fun getPastBookings(): List<Booking> = MockBookings.getPastBookings()

    fun getMaintenanceForAsset(assetId: String): List<MaintenanceRecord> =
        MockMaintenance.getMaintenanceForAsset(assetId)

    fun getMaintenanceHistory(maintenanceId: String): List<MaintenanceHistoryEntry> =
        MockMaintenance.getHistoryForMaintenance(maintenanceId)

    fun getAssetsByCategory(category: String): List<Asset> =
        MockAssets.getAssetsByCategory(category)

    fun getAssetsByStatus(status: OperationalStatus): List<Asset> =
        MockAssets.getAssetsByStatus(status)

    fun getMaintenanceByStatus(status: MaintenanceStatus): List<MaintenanceRecord> =
        MockMaintenance.records.filter { it.status == status }

    fun getUnreadNotificationCount(): Int = MockNotifications.getUnreadCount()

    val assetCategories: List<String> = listOf("All") + MockAssets.categories
}

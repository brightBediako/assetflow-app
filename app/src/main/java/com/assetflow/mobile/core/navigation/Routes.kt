package com.assetflow.mobile.core.navigation

object Routes {
    const val Splash = "splash"
    const val Login = "login"
    const val Register = "register"
    const val Dashboard = "dashboard"
    const val Assets = "assets"
    const val AssetDetails = "assets/{assetId}"
    const val CreateBooking = "bookings/create/{assetId}"
    const val Bookings = "bookings"
    const val BookingApprovals = "bookings/approvals"
    const val Maintenance = "maintenance"
    const val MaintenanceDetails = "maintenance/{maintenanceId}"
    const val Notifications = "notifications"
    const val Profile = "profile"
    const val Settings = "settings"

    fun assetDetails(assetId: String): String = "assets/$assetId"

    fun createBooking(assetId: String): String = "bookings/create/$assetId"

    fun maintenanceDetails(maintenanceId: String): String = "maintenance/$maintenanceId"
}

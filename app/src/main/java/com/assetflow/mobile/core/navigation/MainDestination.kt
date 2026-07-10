package com.assetflow.mobile.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.ui.graphics.vector.ImageVector

enum class MainDestination(
    val route: String,
    val label: String,
    val icon: ImageVector,
) {
    Dashboard(
        route = Routes.Dashboard,
        label = "Dashboard",
        icon = Icons.Outlined.Dashboard,
    ),
    Assets(
        route = Routes.Assets,
        label = "Assets",
        icon = Icons.Outlined.Inventory2,
    ),
    Bookings(
        route = Routes.Bookings,
        label = "Bookings",
        icon = Icons.Outlined.CalendarMonth,
    ),
    Maintenance(
        route = Routes.Maintenance,
        label = "Maintenance",
        icon = Icons.Outlined.Build,
    ),
    Notifications(
        route = Routes.Notifications,
        label = "Notifications",
        icon = Icons.Outlined.Notifications,
    ),
    ;

    companion object {
        fun fromRoute(route: String?): MainDestination? =
            entries.firstOrNull { it.route == route }
    }
}

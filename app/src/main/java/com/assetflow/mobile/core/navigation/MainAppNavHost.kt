package com.assetflow.mobile.core.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.assetflow.mobile.core.data.mock.MockAuthSession
import com.assetflow.mobile.core.data.mock.MockDataRepository
import com.assetflow.mobile.core.domain.model.displayLabel
import com.assetflow.mobile.core.ui.components.MainAppScaffold
import com.assetflow.mobile.core.ui.components.MainScaffoldContent
import com.assetflow.mobile.core.ui.components.PlaceholderScreen
import com.assetflow.mobile.features.assets.presentation.AssetDetailsRoute
import com.assetflow.mobile.features.assets.presentation.AssetListRoute
import com.assetflow.mobile.features.bookings.presentation.BookingApprovalRoute
import com.assetflow.mobile.features.bookings.presentation.BookingHistoryRoute
import com.assetflow.mobile.features.bookings.presentation.CreateBookingRoute
import com.assetflow.mobile.features.dashboard.presentation.DashboardRoute
import com.assetflow.mobile.features.maintenance.presentation.MaintenanceDetailsRoute
import com.assetflow.mobile.features.maintenance.presentation.MaintenanceListRoute

private fun bookingScreenTitle(route: String?): String = when {
    route == Routes.BookingApprovals -> "Pending Approvals"
    route?.startsWith("bookings/create/") == true -> "Create Booking"
    else -> MainDestination.Bookings.label
}

@Composable
fun MainAppNavHost(
    rootNavController: NavHostController,
    onRequireLogin: () -> Unit,
    modifier: Modifier = Modifier,
    mainNavController: NavHostController = rememberNavController(),
) {
    if (!MockAuthSession.isAuthenticated) {
        LaunchedEffect(Unit) {
            onRequireLogin()
        }
        return
    }

    val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val selectedDestination = MainDestination.fromRoute(currentRoute) ?: MainDestination.Dashboard
    val isAssetDetails = currentRoute?.startsWith("assets/") == true &&
        currentRoute != Routes.Assets
    val isBookingSubRoute = currentRoute?.startsWith("bookings/") == true
    val isMaintenanceDetails = currentRoute?.startsWith("maintenance/") == true &&
        currentRoute != Routes.Maintenance
    val showBackNavigation = isAssetDetails || isBookingSubRoute || isMaintenanceDetails
    val screenTitle = when {
        isAssetDetails -> "Asset Details"
        isMaintenanceDetails -> "Maintenance Details"
        isBookingSubRoute -> bookingScreenTitle(currentRoute)
        else -> selectedDestination.label
    }
    val unreadNotifications = MockDataRepository.getUnreadNotificationCount()

    val navigateToTab: (MainDestination) -> Unit = { destination ->
        mainNavController.navigate(destination.route) {
            popUpTo(mainNavController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    MainAppScaffold(
        modifier = modifier,
        screenTitle = screenTitle,
        organizationName = MockDataRepository.organization.name,
        userName = MockDataRepository.currentUser.fullName,
        selectedDestination = selectedDestination,
        notificationBadgeCount = unreadNotifications,
        onDestinationSelected = navigateToTab,
        onBackClick = if (showBackNavigation) {
            { mainNavController.popBackStack() }
        } else {
            null
        },
        onProfileClick = {
            rootNavController.navigate(Routes.Profile)
        },
    ) { paddingValues ->
        NavHost(
            navController = mainNavController,
            startDestination = Routes.Dashboard,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            composable(Routes.Dashboard) {
                DashboardRoute(
                    onViewAssetsClick = { navigateToTab(MainDestination.Assets) },
                    onBookAssetClick = { navigateToTab(MainDestination.Assets) },
                    onReportMaintenanceClick = { navigateToTab(MainDestination.Maintenance) },
                )
            }
            composable(Routes.Assets) {
                AssetListRoute(
                    onAssetClick = { assetId ->
                        mainNavController.navigate(Routes.assetDetails(assetId))
                    },
                )
            }
            composable(
                route = Routes.AssetDetails,
                arguments = listOf(
                    navArgument("assetId") { type = NavType.StringType },
                ),
            ) { backStackEntry ->
                val assetId = backStackEntry.arguments?.getString("assetId").orEmpty()
                AssetDetailsRoute(
                    assetId = assetId,
                    onBackClick = { mainNavController.popBackStack() },
                    onBookAssetClick = { selectedAssetId ->
                        mainNavController.navigate(Routes.createBooking(selectedAssetId))
                    },
                    onViewMaintenanceHistoryClick = {
                        navigateToTab(MainDestination.Maintenance)
                    },
                )
            }
            composable(Routes.Bookings) {
                BookingHistoryRoute(
                    onPendingApprovalsClick = {
                        mainNavController.navigate(Routes.BookingApprovals)
                    },
                    onBrowseAssetsClick = {
                        navigateToTab(MainDestination.Assets)
                    },
                )
            }
            composable(Routes.BookingApprovals) {
                BookingApprovalRoute()
            }
            composable(
                route = Routes.CreateBooking,
                arguments = listOf(
                    navArgument("assetId") { type = NavType.StringType },
                ),
            ) { backStackEntry ->
                val assetId = backStackEntry.arguments?.getString("assetId").orEmpty()
                CreateBookingRoute(
                    assetId = assetId,
                    onBackClick = { mainNavController.popBackStack() },
                    onBookingSubmitted = {
                        navigateToTab(MainDestination.Bookings)
                    },
                )
            }
            composable(Routes.Maintenance) {
                MaintenanceListRoute(
                    onMaintenanceClick = { maintenanceId ->
                        mainNavController.navigate(Routes.maintenanceDetails(maintenanceId))
                    },
                )
            }
            composable(
                route = Routes.MaintenanceDetails,
                arguments = listOf(
                    navArgument("maintenanceId") { type = NavType.StringType },
                ),
            ) { backStackEntry ->
                val maintenanceId = backStackEntry.arguments?.getString("maintenanceId").orEmpty()
                MaintenanceDetailsRoute(
                    maintenanceId = maintenanceId,
                    onBackClick = { mainNavController.popBackStack() },
                )
            }
            composable(Routes.Notifications) {
                NotificationsTabContent()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileShellScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val user = MockDataRepository.currentUser

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Profile") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
            )
        },
    ) { paddingValues ->
        MainScaffoldContent(paddingValues = paddingValues) {
            PlaceholderScreen(
                title = user.fullName,
                message = "${user.role.displayLabel()} · ${MockDataRepository.organization.name}\n${user.email}\n\nFull profile screen arrives in Phase 9.",
            )
        }
    }
}

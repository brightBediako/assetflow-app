package com.assetflow.mobile.core.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.assetflow.mobile.core.data.mock.MockDataRepository
import com.assetflow.mobile.core.ui.components.ComponentsPreviewScreen
import com.assetflow.mobile.core.ui.components.MainAppScaffold
import com.assetflow.mobile.core.ui.components.MainScaffoldContent
import com.assetflow.mobile.core.ui.components.PlaceholderScreen

private val PreviewOrganizationName: String
    get() = MockDataRepository.organization.name

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Routes.Splash,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.fillMaxSize(),
    ) {
        composable(Routes.Splash) {
            SplashRoute(
                onFinished = {
                    navController.navigate(Routes.Dashboard) {
                        popUpTo(Routes.Splash) { inclusive = true }
                    }
                },
            )
        }

        MainDestination.entries.forEach { destination ->
            composable(destination.route) {
                MainTabRoute(
                    destination = destination,
                    navController = navController,
                )
            }
        }

        composable(Routes.Profile) {
            PlaceholderScreen(
                title = "Profile",
                message = "Profile screen will be built in Phase 9.",
            )
        }
    }
}

@Composable
private fun MainTabRoute(
    destination: MainDestination,
    navController: NavHostController,
) {
    MainAppScaffold(
        screenTitle = destination.label,
        organizationName = PreviewOrganizationName,
        selectedDestination = destination,
        onDestinationSelected = { selected ->
            navController.navigate(selected.route) {
                popUpTo(Routes.Dashboard) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        onProfileClick = {
            navController.navigate(Routes.Profile)
        },
    ) { paddingValues ->
        MainScaffoldContent(paddingValues = paddingValues) {
            when (destination) {
                MainDestination.Dashboard -> ComponentsPreviewScreen()
                MainDestination.Assets -> PlaceholderScreen(title = "Assets")
                MainDestination.Bookings -> PlaceholderScreen(title = "Bookings")
                MainDestination.Maintenance -> PlaceholderScreen(title = "Maintenance")
                MainDestination.Notifications -> PlaceholderScreen(title = "Notifications")
            }
        }
    }
}

@Composable
private fun SplashRoute(
    onFinished: () -> Unit,
) {
    androidx.compose.runtime.LaunchedEffect(Unit) {
        onFinished()
    }

    Scaffold { padding ->
        Text(
            text = "AssetFlow",
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        )
    }
}

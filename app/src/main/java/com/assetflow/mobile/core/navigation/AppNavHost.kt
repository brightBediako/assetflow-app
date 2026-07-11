package com.assetflow.mobile.core.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.assetflow.mobile.core.data.mock.MockAuthSession
import com.assetflow.mobile.features.auth.presentation.LandingRoute
import com.assetflow.mobile.features.auth.presentation.LoginRoute
import com.assetflow.mobile.features.auth.presentation.RegisterRoute
import com.assetflow.mobile.features.profile.presentation.ProfileShellScreen
import com.assetflow.mobile.features.settings.presentation.SettingsShellScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Routes.Landing,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.fillMaxSize(),
    ) {
        composable(Routes.Landing) {
            LandingRoute(
                onLoginClick = {
                    navController.navigate(Routes.Login)
                },
                onCreateAccountClick = {
                    navController.navigate(Routes.Register)
                },
            )
        }

        composable(Routes.Login) {
            LoginRoute(
                onLoginSuccess = {
                    navController.navigate(Routes.Main) {
                        popUpTo(Routes.Landing) { inclusive = true }
                    }
                },
                onCreateAccountClick = {
                    navController.navigate(Routes.Register)
                },
            )
        }

        composable(Routes.Register) {
            RegisterRoute(
                onRegisterSuccess = {
                    navController.navigate(Routes.Main) {
                        popUpTo(Routes.Landing) { inclusive = true }
                    }
                },
                onBackToLoginClick = {
                    navController.popBackStack()
                },
            )
        }

        composable(Routes.Main) {
            MainAppNavHost(
                rootNavController = navController,
                onRequireLogin = {
                    navController.navigate(Routes.Landing) {
                        popUpTo(Routes.Main) { inclusive = true }
                    }
                },
            )
        }

        composable(Routes.Profile) {
            ProfileShellScreen(
                onBackClick = { navController.popBackStack() },
                onLogoutClick = {
                    MockAuthSession.signOut()
                    navController.navigate(Routes.Landing) {
                        popUpTo(Routes.Main) { inclusive = true }
                    }
                },
                onSettingsClick = {
                    navController.navigate(Routes.Settings)
                },
            )
        }

        composable(Routes.Settings) {
            SettingsShellScreen(
                onBackClick = { navController.popBackStack() },
            )
        }
    }
}

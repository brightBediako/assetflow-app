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
import com.assetflow.mobile.features.auth.presentation.LoginRoute
import com.assetflow.mobile.features.auth.presentation.RegisterRoute

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
                    navController.navigate(Routes.Login) {
                        popUpTo(Routes.Splash) { inclusive = true }
                    }
                },
            )
        }

        composable(Routes.Login) {
            LoginRoute(
                onLoginSuccess = {
                    navController.navigate(Routes.Main) {
                        popUpTo(Routes.Login) { inclusive = true }
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
                        popUpTo(Routes.Login) { inclusive = true }
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
                    navController.navigate(Routes.Login) {
                        popUpTo(Routes.Main) { inclusive = true }
                    }
                },
            )
        }

        composable(Routes.Profile) {
            ProfileShellScreen(
                onBackClick = { navController.popBackStack() },
            )
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

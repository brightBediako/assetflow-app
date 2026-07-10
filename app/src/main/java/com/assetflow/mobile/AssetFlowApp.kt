package com.assetflow.mobile

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.assetflow.mobile.core.data.mock.MockSettingsStore
import com.assetflow.mobile.core.domain.model.ThemePreference
import com.assetflow.mobile.core.navigation.AppNavHost
import com.assetflow.mobile.core.ui.theme.AssetFlowTheme

@Composable
fun AssetFlowApp() {
    val settings by MockSettingsStore::settings
    val darkTheme = when (settings.themePreference) {
        ThemePreference.System -> isSystemInDarkTheme()
        ThemePreference.Light -> false
        ThemePreference.Dark -> true
    }

    AssetFlowTheme(darkTheme = darkTheme) {
        Surface(modifier = Modifier.fillMaxSize()) {
            AppNavHost()
        }
    }
}

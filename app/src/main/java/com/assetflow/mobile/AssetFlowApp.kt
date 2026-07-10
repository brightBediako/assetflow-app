package com.assetflow.mobile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.assetflow.mobile.core.navigation.AppNavHost
import com.assetflow.mobile.core.ui.theme.AssetFlowTheme

@Composable
fun AssetFlowApp() {
    AssetFlowTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            AppNavHost()
        }
    }
}

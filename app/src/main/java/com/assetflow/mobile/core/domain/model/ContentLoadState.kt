package com.assetflow.mobile.core.domain.model

/**
 * Prototype content presentation states for major list screens.
 * Used to verify empty, loading, and error UI without a backend.
 */
enum class ContentLoadState {
    Normal,
    Loading,
    Empty,
    Error,
    NetworkUnavailable,
}

fun ContentLoadState.displayLabel(): String = when (this) {
    ContentLoadState.Normal -> "Normal"
    ContentLoadState.Loading -> "Loading"
    ContentLoadState.Empty -> "Empty"
    ContentLoadState.Error -> "Error"
    ContentLoadState.NetworkUnavailable -> "Offline"
}

package com.assetflow.mobile.core.data.mock

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.assetflow.mobile.core.domain.model.ContentLoadState

/**
 * Prototype-only override for support states on major list screens.
 * Controlled from Settings; does not persist.
 */
object MockContentStateStore {
    var contentState by mutableStateOf(ContentLoadState.Normal)
        private set

    fun setContentState(state: ContentLoadState) {
        contentState = state
    }

    fun reset() {
        contentState = ContentLoadState.Normal
    }
}

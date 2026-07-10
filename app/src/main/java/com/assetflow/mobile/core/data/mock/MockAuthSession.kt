package com.assetflow.mobile.core.data.mock

/**
 * UI prototype session flag. Replaced by real JWT storage after UI approval.
 */
object MockAuthSession {
    var isAuthenticated: Boolean = false
        private set

    fun signIn() {
        isAuthenticated = true
    }

    fun signOut() {
        isAuthenticated = false
    }
}

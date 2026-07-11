package com.assetflow.mobile.core.data.mock

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.assetflow.mobile.core.domain.model.User
import com.assetflow.mobile.core.domain.model.UserRole

/**
 * UI prototype session. Replaced by real JWT storage after UI approval.
 */
object MockAuthSession {
    var isAuthenticated by mutableStateOf(false)
        private set

    fun signIn(user: User) {
        MockUsers.setCurrentUser(user)
        isAuthenticated = true
    }

    fun signInRegisteredUser(
        fullName: String,
        email: String,
        organizationName: String,
        role: UserRole,
    ) {
        val existing = MockUsers.getUserByEmail(email)
        if (existing != null) {
            signIn(existing)
            return
        }

        val organization = MockUsers.ensureOrganization(organizationName)
        val user = User(
            id = "user-session",
            fullName = fullName.trim(),
            email = email.trim(),
            role = role,
            organizationId = organization.id,
        )
        MockUsers.setCurrentUser(user, organization)
        isAuthenticated = true
    }

    fun signOut() {
        isAuthenticated = false
    }
}

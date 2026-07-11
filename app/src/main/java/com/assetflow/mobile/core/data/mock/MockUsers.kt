package com.assetflow.mobile.core.data.mock

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.assetflow.mobile.core.domain.model.Organization
import com.assetflow.mobile.core.domain.model.User
import com.assetflow.mobile.core.domain.model.UserRole

object MockUsers {
    val codeCraft = Organization(
        id = "org-codecraft",
        name = "CodeCraft LTD",
    )

    val takoradiTechnicalUniversity = Organization(
        id = "org-ttu",
        name = "Takoradi Technical University",
    )

    val organizations = listOf(codeCraft, takoradiTechnicalUniversity)

    val brightBediako = User(
        id = "user-001",
        fullName = "Bright Bediako",
        email = "bright@gmail.com",
        role = UserRole.Admin,
        organizationId = codeCraft.id,
    )

    val nanaAma = User(
        id = "user-002",
        fullName = "Nana Ama",
        email = "ama@gmail.com",
        role = UserRole.Manager,
        organizationId = takoradiTechnicalUniversity.id,
    )

    val kwesiBoateng = User(
        id = "user-003",
        fullName = "Kwesi Boateng",
        email = "kwesi@gmail.com",
        role = UserRole.Staff,
        organizationId = takoradiTechnicalUniversity.id,
    )

    val staffUsers = listOf(brightBediako, nanaAma, kwesiBoateng)

    var currentUser by mutableStateOf(brightBediako)
        private set

    private var sessionOrganization by mutableStateOf<Organization?>(null)

    val organization: Organization
        get() = getOrganizationById(currentUser.organizationId)

    fun setCurrentUser(user: User, organizationOverride: Organization? = null) {
        if (organizationOverride != null &&
            organizations.none { it.id == organizationOverride.id }
        ) {
            sessionOrganization = organizationOverride
        }
        currentUser = user
    }

    fun getUserById(userId: String): User? =
        staffUsers.firstOrNull { it.id == userId }

    fun getUserByEmail(email: String): User? =
        staffUsers.firstOrNull { it.email.equals(email.trim(), ignoreCase = true) }

    fun getOrganizationById(organizationId: String): Organization =
        organizations.firstOrNull { it.id == organizationId }
            ?: sessionOrganization?.takeIf { it.id == organizationId }
            ?: codeCraft

    fun findOrganizationByName(name: String): Organization? =
        organizations.firstOrNull { it.name.equals(name.trim(), ignoreCase = true) }

    fun ensureOrganization(name: String): Organization {
        val trimmed = name.trim()
        findOrganizationByName(trimmed)?.let { return it }
        val created = Organization(id = "org-session", name = trimmed)
        sessionOrganization = created
        return created
    }
}

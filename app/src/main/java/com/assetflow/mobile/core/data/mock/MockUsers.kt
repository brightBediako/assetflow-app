package com.assetflow.mobile.core.data.mock

import com.assetflow.mobile.core.domain.model.Organization
import com.assetflow.mobile.core.domain.model.User
import com.assetflow.mobile.core.domain.model.UserRole

object MockUsers {
    const val ORGANIZATION_ID = "org-northbridge"

    val organization = Organization(
        id = ORGANIZATION_ID,
        name = "Northbridge University",
    )

    val currentUser = User(
        id = "user-001",
        fullName = "Ama Mensah",
        email = "ama.mensah@northbridge.edu",
        role = UserRole.Manager,
        organizationId = ORGANIZATION_ID,
    )

    val staffUsers = listOf(
        currentUser,
        User(
            id = "user-002",
            fullName = "Kwesi Boateng",
            email = "kwesi.boateng@northbridge.edu",
            role = UserRole.Staff,
            organizationId = ORGANIZATION_ID,
        ),
        User(
            id = "user-003",
            fullName = "Efua Adjei",
            email = "efua.adjei@northbridge.edu",
            role = UserRole.Admin,
            organizationId = ORGANIZATION_ID,
        ),
        User(
            id = "user-004",
            fullName = "Daniel Osei",
            email = "daniel.osei@northbridge.edu",
            role = UserRole.Staff,
            organizationId = ORGANIZATION_ID,
        ),
    )

    fun getUserById(userId: String): User? = staffUsers.firstOrNull { it.id == userId }
}

package com.assetflow.mobile.features.profile.presentation

import com.assetflow.mobile.core.data.mock.MockDataRepository
import com.assetflow.mobile.core.domain.model.UserRole
import com.assetflow.mobile.core.domain.model.displayLabel

data class ProfileUiState(
    val fullName: String,
    val email: String,
    val role: UserRole,
    val roleLabel: String,
    val organizationName: String,
    val userId: String,
    val upcomingBookingsCount: Int,
    val pastBookingsCount: Int,
    val canManageApprovals: Boolean,
    val pendingApprovalsCount: Int,
    val appVersion: String,
    val supportEmail: String,
)

fun loadProfileUiState(): ProfileUiState {
    val user = MockDataRepository.currentUser
    val organization = MockDataRepository.organization

    return ProfileUiState(
        fullName = user.fullName,
        email = user.email,
        role = user.role,
        roleLabel = user.role.displayLabel(),
        organizationName = organization.name,
        userId = user.id,
        upcomingBookingsCount = MockDataRepository.getUpcomingBookingsForCurrentUser().size,
        pastBookingsCount = MockDataRepository.getPastBookingsForCurrentUser().size,
        canManageApprovals = MockDataRepository.canManageApprovals(),
        pendingApprovalsCount = MockDataRepository.getPendingApprovals().size,
        appVersion = "1.0.0",
        supportEmail = "support@assetflow.app",
    )
}

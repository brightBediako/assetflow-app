package com.assetflow.mobile.core.domain.model

data class Organization(
    val id: String,
    val name: String,
)

data class User(
    val id: String,
    val fullName: String,
    val email: String,
    val role: UserRole,
    val organizationId: String,
)

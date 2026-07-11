package com.assetflow.mobile.core.domain.model

enum class UserRole {
    Admin,
    Staff,
    Manager,
}

fun UserRole.displayLabel(): String = when (this) {
    UserRole.Admin -> "System Admin"
    UserRole.Staff -> "Staff"
    UserRole.Manager -> "Manager"
}

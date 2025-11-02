package com.example.fincent.domain.model

data class User(
    val uid: String = "",
    val email: String = "",
    val displayName: String = "",
    val university: String = "",
    val course: String = "",
    val profilePhotoUrl: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val isEmailVerified: Boolean = false
)


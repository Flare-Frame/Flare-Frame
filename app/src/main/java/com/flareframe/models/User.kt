package com.flareframe.models

import com.google.firebase.Timestamp

data class User(
    val username: String = "",
    val password: String = "",
    val email: String = "",
    val bio: String? = "",
    val Pronouns: String? = "",
    val created_at: Timestamp = Timestamp.now(),
    val displayName: String = "",
    val isVerified: Boolean = false,
    val paswordHash: String = "",
    val followers: Int = 0,
    val following: Int = 0,

    )

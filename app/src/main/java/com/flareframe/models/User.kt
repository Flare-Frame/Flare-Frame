package com.flareframe.models

import com.google.firebase.Timestamp

data class User(
    val Username: String = "",

    val Email: String = "",
    val Bio: String? = "",
    val Pronouns: String? = "",
    val created_at: String = "",
    val DisplayName: String = "",
    val isVerified: Boolean = false,
    val PasswordHash: String = "",
    val Followers: Int = 0,
    val Following: Int = 0,

    )

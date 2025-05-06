package com.flareframe.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("Username")
    val username: String = "",

    @SerialName("UUID")
    val uuId: String = "",

    @SerialName("Deleted")
    val deleted: Boolean = false,

    @SerialName("Deleted_At")
    val deletedAt: LocalDateTime? = null,

    @SerialName("Visibility")
    val visibility: Boolean = false,

    @SerialName("ProfilePicUrl")
    val profilePicUrl: String = "",

    @SerialName("Email")
    val email: String = "",

    @SerialName("Bio")
    val bio: String? = "",

    @SerialName("Pronouns")
    val pronouns: String? = "",

    @SerialName("Created_at")
    val createdAt: LocalDateTime? = null,

    @SerialName("DisplayName")
    val displayName: String = "",

    @SerialName("isVerified")
    val isVerified: Boolean = false,



    )

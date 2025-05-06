package com.flareframe.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Post(

// we need to add a post views table, post shares, post saves, figure out algo to calculate
    @SerialName("PostId")
    val postId: String = "",

    @SerialName("Username")
    val username: String = "",

    @SerialName("Caption")
    val caption: String = "",

    @SerialName("Archived")
    val archived: Boolean = false,

    @SerialName("Created_at")
    val createdAt: LocalDateTime? =null,

    @SerialName("Archived_at")
    val archivedAt:LocalDateTime? =null,
)

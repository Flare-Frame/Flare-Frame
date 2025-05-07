package com.flareframe.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class Post(

// we need to add a post views table, post shares, post saves, figure out algo to calculate
    @SerialName("PostId")
    val postId: String,

    @SerialName("Username")
    val username: String,

    @SerialName("Caption")
    val caption: String,

    @SerialName("Archived")
    val archived: Boolean,


    @SerialName("Archived_at")
    val archivedAt: LocalDateTime?,
)

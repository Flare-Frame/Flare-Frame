package com.flareframe.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class PostFlame(

    @SerialName("PostFlameId")
    val postFlameId: String,

    @SerialName("Created_at")
    val createdAt: LocalDateTime?,

    @SerialName("PostId")
    val postId: String,

    @SerialName("Username")
    val username: String,
)

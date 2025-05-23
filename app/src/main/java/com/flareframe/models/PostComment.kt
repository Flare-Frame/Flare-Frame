package com.flareframe.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class PostComment(
    @SerialName("PostCommentId")
    val postCommentId: String,


    @SerialName("PostId")
    val postId: String,

    @SerialName("Username")
    val username: String,

    @SerialName("Comment")
    val comment: String,
)
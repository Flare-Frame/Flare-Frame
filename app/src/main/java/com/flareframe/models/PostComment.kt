package com.flareframe.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName

data class PostComment (
    @SerialName("PostCommentId")
    val postCommentId:String = "",

    @SerialName("Created_at")
    val createdAt: LocalDateTime? = null,

    @SerialName("PostId")
    val postId:String = "",

    @SerialName("Username")
    val username:String = "",

    @SerialName("Comment")
    val comment:String = "",
)
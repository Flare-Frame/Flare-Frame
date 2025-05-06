package com.flareframe.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Tags(
    @SerialName("TagId")
    val tagId: String = "",

    @SerialName("Tag")
    val tag: String = "",

    @SerialName("Created_at")
    val createdAt: String = "",

    @SerialName("PostId")
    val postId: String = "",
)
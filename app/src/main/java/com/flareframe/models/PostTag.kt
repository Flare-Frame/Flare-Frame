package com.flareframe.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class PostTag(

    @SerialName("PostId")
    val postId: String,
    @SerialName("TagId")
    val tagId: String,
)
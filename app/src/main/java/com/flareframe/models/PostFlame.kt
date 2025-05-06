package com.flareframe.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.Serial
@Serializable
data class PostFlame(

    @SerialName("PostFlameId")
    val postFlameId:String = "",

    @SerialName("Created_at")
    val createdAt: LocalDateTime? = null,

    @SerialName("PostId")
    val postId:String = "",

    @SerialName("Username")
    val username:String = "",
)

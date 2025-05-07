package com.flareframe.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class Follow(
    @SerialName("FollowerUsername")
    val followerUsername:String,

    @SerialName("FollowedUsername")
    val followedUsername:String,

    @SerialName("followed_at")
    val followedAt: LocalDateTime?=null,
)

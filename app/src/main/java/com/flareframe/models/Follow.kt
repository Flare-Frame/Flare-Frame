package com.flareframe.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Follow(
    @SerialName("FollowerUsername")
    val followerUsername:String = "",

    @SerialName("FollowedUsername")
    val followedUsername:String = "",

    @SerialName("followed_at")
    val followedAt:String = "",
)

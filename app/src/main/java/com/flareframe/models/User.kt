package com.flareframe.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class User(
    @SerialName("Username")
    val username: String ,

    @SerialName("UUID")
    val uuId: String ,

    @SerialName("Deleted")
    val deleted: Boolean,

    @SerialName("Deleted_At")
    val deletedAt: LocalDateTime?,

    @SerialName("Visibility")
    val visibility: Boolean,

    @SerialName("ProfilePicUrl")
    val profilePicUrl:String,

    @SerialName("Email")
    val email: String,

    @SerialName("Bio")
    val bio: String?,

    @SerialName("Pronouns")
    val pronouns: String?,

//    @SerialName("Created_at")              // MADE PROGRESS, DO THE CHECK IF USERNAME EXISTS BEFORE CREATING IN FIREBASE, ELSE RETURN AND SHOW MESSAGE
//    val createdAt: LocalDateTime?,

    @SerialName("DisplayName")
    val displayName: String?,

    @SerialName("isVerified")
    val isVerified: Boolean,



    )

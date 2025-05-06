package com.flareframe.ui.states

import kotlinx.serialization.SerialName

data class UserState(
    val username:String = "",
    val email:String = "",
    val displayName:String? = "",
  //  val visibility: Boolean = false, will add this to datastore, will review this
    val bio: String? = "",
    val pronouns: String? = "",
    val profilePicUrl: String = "",
    val isLoggedIn:Boolean  = true,
    val isLoading:Boolean = true,
    val errorMessage:String = ""
)
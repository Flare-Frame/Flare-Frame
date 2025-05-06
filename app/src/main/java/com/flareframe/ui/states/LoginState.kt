package com.flareframe.ui.states

data class LoginState(
    val username:String = "",
    val password: String = "",

    val email:String = "",
    val inProgress:Boolean = false,

    val errorMessage: String = ""
)

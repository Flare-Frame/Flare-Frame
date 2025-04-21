package com.flareframe.ui.states

data class UserUiState(
    val username:String = "",
    val password: String = "",

    val email:String = "",
    val inProgress:Boolean = false,
    val isLoggedin: Boolean = false,
    val errorMessage: String = ""
)

package com.flareframe.ui.states



data class RegisterUiState(

    val inProgress:Boolean = false,
    val isRegistered: Boolean = false,

    val usernameError:String = "",
    val confirmPasswordError:String = "",
    val emailError:String = "",
    val errorMessage: String = ""
)

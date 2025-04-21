package com.flareframe.ui.states

import android.R
import io.ktor.http.HttpMessage

data class RegisterUiState(
    val username:String = "",
    val password: String = "",
    val confirmPassword:String = "",
    val email:String = "",
    val inProgress:Boolean = false,
    val isRegistered: Boolean = false,
    val errorMessage: String = ""
)

package com.flareframe.ui.states

import android.R
import io.ktor.http.HttpMessage

data class RegisterUiState(

    val inProgress:Boolean = false,
    val isRegistered: Boolean = false,

    val usernameError:String = "",
    val confirmPasswordError:String = "",
    val emailError:String = "",
    val errorMessage: String = ""
)

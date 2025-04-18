package com.flareframe.viewmodels

import com.flareframe.ui.states.UserUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UserViewModel {
    val _uiState = MutableStateFlow(UserUiState())
    val uiState  = _uiState.asStateFlow()
    fun updateEmail(email:String){
        _uiState.update { currentState->
            currentState.copy(email = email)
        }
    }
    fun updateUsername(username:String){
        _uiState.update { currentState->
            currentState.copy(username = username)
        }
    }
}
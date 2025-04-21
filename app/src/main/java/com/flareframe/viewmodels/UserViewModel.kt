package com.flareframe.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.flareframe.repositories.AuthRepostitory
import com.flareframe.ui.states.UserUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
@HiltViewModel
public class UserViewModel @Inject constructor(private val authRepostitory: AuthRepostitory): ViewModel() {
    val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()
    fun onEmailValueChange(email: String) {
        _uiState.update { currentState ->
            currentState.copy(email = email)
        }
    }

    fun onPasswordValueChange(password: String) {
        _uiState.update { currentState ->
            currentState.copy(password = password)
        }
    }


    fun onLogin() {
        _uiState.update { currentState ->
            currentState.copy(inProgress = true)
        }
        authRepostitory.LogIn(
            email = _uiState.value.email,
            password = _uiState.value.password
        ) { task ->
            if (task.isSuccessful) {
                Log.d("login", "User has successfully logged in")
                _uiState.update { currentState ->
                    currentState.copy(isLoggedin = true, inProgress = false)
                }
            } else {
                Log.w("login", "Failed to log in to flare frame", task.exception)
                _uiState.update { currentState ->
                    currentState.copy(
                        email = "",
                        password = "",
                        inProgress = false,
                        isLoggedin = false,
                        errorMessage = "Incorrect username or password"
                    )
                }
            }

        }
    }

}
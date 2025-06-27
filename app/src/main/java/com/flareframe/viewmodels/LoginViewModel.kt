package com.flareframe.viewmodels

import android.util.Log
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flareframe.repositories.AuthRepository
import com.flareframe.repositories.UserRepository
import com.flareframe.ui.states.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    val userRepository: UserRepository,
) : ViewModel() {
    val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()
    val username = TextFieldState()
    val password=TextFieldState()
    val email=TextFieldState()



    // make sure to authenticate it exists with the username in supabase
    fun onLogin() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(inProgress = true)
            }
            val user = userRepository.fetchUserWithEmail(email = email.text.toString())
            if (user == null) {
                email.clearText()
                password.clearText()
                _uiState.update { currentState ->
                    currentState.copy(
                        inProgress = false,

                        errorMessage = "Incorrect username or password"
                    )
                }
                return@launch                            
            }
            authRepository.LogIn(
                email = email.text.toString(),
                password = password.text.toString()
            ) { task ->
                if (task.isSuccessful) {
                    Log.d("login", "User has successfully logged in")

                    _uiState.update { currentState ->
                        currentState.copy(inProgress = false)
                    }
                } else {
                    Log.w("login", "Failed to log in to flare frame", task.exception)
                    _uiState.update { currentState ->
                        currentState.copy(
                            inProgress = false,
                            errorMessage = "Incorrect username or password"
                        )
                    }
                    email.clearText()
                    password.clearText()
                }

            }
        }
    }

    fun resetState() {
        email.clearText()
        password.clearText()
        _uiState.update { currentState ->
            currentState.copy( errorMessage = "")
        }
    }
}
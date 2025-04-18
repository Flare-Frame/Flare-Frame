package com.flareframe.viewmodels

import com.flareframe.repositories.AuthRepostitory
import com.flareframe.ui.states.RegisterUiState
import com.flareframe.validation.inputValidation.Companion.validateEmail
import com.flareframe.validation.inputValidation.Companion.validatePassword
import com.flareframe.validation.inputValidation.Companion.validateUsername
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(db: AuthRepostitory) {
    val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun updateEmail(email: String) {
        _uiState.update { currentState ->
            currentState.copy(email = email)
        }
    }

    fun updateUsername(username: String) {
        _uiState.update { currentState ->
            currentState.copy(username = username)
        }
    }

    fun updatePassword(password: String) {
        _uiState.update { currentState ->
            currentState.copy(password = password)
        }
    }

    fun updateConfirmPassword(confirmPassword: String) {
        _uiState.update { currentState ->
            currentState.copy(confirmPassword = confirmPassword)
        }
    }


    fun onRegister(): Boolean {
        _uiState.update { currentState ->
            currentState.copy(inProgress = true)
        }
        val emailCheck =
            validateEmail(_uiState.value.email) // these were made via a companion object
        val passwordCheck = validatePassword(_uiState.value.password)
        val confirmPasswordCheck =
            validatePassword(_uiState.value.email)  // using same method to validate
        val usernameCheck = validateUsername(_uiState.value.username)
        var result = true
        if (!emailCheck || !passwordCheck || !confirmPasswordCheck || !usernameCheck) {
            result = false
            _uiState.update { currentState ->
                currentState.copy(errorMessage = "Enter valid data into all fields.")
                return result
            }
        }

        return result
    }
}
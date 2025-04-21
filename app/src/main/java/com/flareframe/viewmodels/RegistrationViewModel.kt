package com.flareframe.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flareframe.models.User
import com.flareframe.repositories.AuthRepostitory
import com.flareframe.repositories.UserRepository
import com.flareframe.services.TimstampUtils.convertFirebaseTimestampToTimestampZ
import com.flareframe.ui.states.RegisterUiState
import com.flareframe.validation.inputValidation.Companion.validateEmail
import com.flareframe.validation.inputValidation.Companion.validatePassword
import com.flareframe.validation.inputValidation.Companion.validateUsername
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val db: AuthRepostitory,
    private val supabase: UserRepository,
) : ViewModel() {
    private val apikey: String =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImRmenZxbnFyam91eHV6YWNuZ3dhIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDQ2NTg4NzUsImV4cCI6MjA2MDIzNDg3NX0.YlllghJ4GqEdSZpWtF8LJK3dU_vUHAquPPnXYfPCb9o"
    private val authKey: String =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImRmenZxbnFyam91eHV6YWNuZ3dhIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTc0NDY1ODg3NSwiZXhwIjoyMDYwMjM0ODc1fQ.abNjEqGt2PeCivMkN_EAyQRoCZs91ZMycybEIAafmV8"

    val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()
    private var fetchJob: Job? = null
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


    fun registerSupabase(email: String, passwordHash: String, username: String) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            val response = supabase.createUser(
                User(
                    created_at = convertFirebaseTimestampToTimestampZ(Timestamp.now()),
                    Email = email,
                    PasswordHash = passwordHash,
                    Username = username
                ), authorisation = authKey, apikey = apikey
            )
            if (!response.isSuccessful) {
                Log.w("Supa", response.raw().toString())
            }
        }
    }

    fun updateErrorMessage(errorMessage: String) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    username = "",
                    password = "",
                    confirmPassword = "",
                    email = "",
                    errorMessage = errorMessage,
                    inProgress = false,
                    isRegistered = false
                )
            }
        }
    }

    fun onRegister() {
        _uiState.update { it.copy(inProgress = true, errorMessage = "") }

        val email = _uiState.value.email.trim()
        val username = _uiState.value.username.trim()
        val password = _uiState.value.password
        val confirm = _uiState.value.confirmPassword


        if (email.isEmpty() || username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            updateErrorMessage("Please fill in all fields!")
            return
        }


        if (!validateEmail(email)) {
            updateErrorMessage("Invalid email address")
            return
        }


        if (!validateUsername(username)) {
            updateErrorMessage("Username must have at least: 6 characters, 1 uppercase letter, and may include '_' or '.'")
            return
        }


        if (!validatePassword(password)) {
            updateErrorMessage("Password must have at least: 8 characters, 1 uppercase letter, and one of !@#$%^&*")
            return
        }


        if (!validatePassword(confirm)) {
            updateErrorMessage("Confirm password must meet the same requirements as password")
            return
        }


        if (password != confirm) {
            updateErrorMessage("Passwords do not match!")
            return
        }


        db.signUp(email = email, password = password) { task ->
            if (task.isSuccessful) {
                Log.d("register", "$email has successfully registered.")
                registerSupabase(email, password, username)
                _uiState.update { it.copy(isRegistered = true, inProgress = false) }
            } else {
                val message = when (task.exception) {
                    is FirebaseAuthUserCollisionException -> "Email already exists"
                    else -> "Unable to register user: ${task.exception?.localizedMessage.orEmpty()}"
                }
                updateErrorMessage(message)
            }
        }
    }
}

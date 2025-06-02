package com.flareframe.viewmodels


import android.util.Log
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flareframe.models.User
import com.flareframe.repositories.AuthRepository
import com.flareframe.repositories.UserRepository
import com.flareframe.ui.states.RegisterUiState
import com.flareframe.validation.inputValidation.Companion.validateEmail
import com.flareframe.validation.inputValidation.Companion.validatePassword
import com.flareframe.validation.inputValidation.Companion.validateUsername
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
    private val db: AuthRepository,
    private val supabase: UserRepository,
) : ViewModel() {


    val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    val username = TextFieldState()
    val password = TextFieldState()
    val confirmPassword = TextFieldState()
    val email = TextFieldState()
    private var fetchJob: Job? = null






    fun registerSupabase(email: String, username: String, uuId: String) {


        viewModelScope.launch {

            val response = supabase.createUser(
                User(

                    email = email,
                    uuId = uuId,
                    username = username,
                    profilePicUrl = "https://dfzvqnqrjouxuzacngwa.supabase.co/storage/v1/object/public/profile-picture//DefaultPic.png",
                    isVerified = false,
                    deleted = false,
                    visibility = true,
                    deletedAt = null,
                    bio = "Add a bio :)",
                    pronouns = "",

                    displayName = "",
                )
            )
            if (!response.isSuccess) {
                Log.w("Supabase", response.toString())
            }
        }
    }

    fun updateErrorMessage(errorMessage: String) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            username.clearText()
            password.clearText()
            confirmPassword.clearText()
            email.clearText()
            _uiState.update { currentState ->
                currentState.copy(
                    errorMessage = errorMessage,
                    inProgress = false,
                    isRegistered = false
                )
            }
        }
    }

    fun onRegister() {
        viewModelScope.launch {
            _uiState.update { it.copy(inProgress = true, errorMessage = "") }

            val email = email.text.toString().trim()
            val username: String = username.text.toString().trim()
            val password = password.toString()
            val confirm = confirmPassword.toString()

            // 1) Your local validation checks…
            if (email.isEmpty() || username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                updateErrorMessage("Please fill in all fields!")
                return@launch
            }
            if (!validateEmail(email)) {
                updateErrorMessage("Invalid email address"); return@launch
            }
            if (!validateUsername(username)) {
                updateErrorMessage("Username must have …"); return@launch
            }
            if (!validatePassword(password)) {
                updateErrorMessage("Password must have …"); return@launch
            }
            if (password != confirm) {
                updateErrorMessage("Passwords do not match!"); return@launch
            }


            val existing = supabase.fetchUserWithUsername(username)
            if (existing != null) {
                updateErrorMessage("Username already exists")
                return@launch
            }


            db.signUp(email, password) { task ->
                if (task.isSuccessful) {
                    val uid = task.result.user?.uid.orEmpty()


                    registerSupabase(email, username, uid)
                    db.LogOut()
                    _uiState.update { it.copy(isRegistered = true, inProgress = false) }

                } else {
                    updateErrorMessage(
                        (task.exception as? FirebaseAuthUserCollisionException)
                            ?.message ?: "Unable to register user"
                    )
                }
            }
        }
    }


    fun resetState() {
        username.clearText()
        password.clearText()
        confirmPassword.clearText()
        email.clearText()
        _uiState.update {
            it.copy(
                isRegistered = false,
                errorMessage = "",
                inProgress = false
            )
        }
    }
}

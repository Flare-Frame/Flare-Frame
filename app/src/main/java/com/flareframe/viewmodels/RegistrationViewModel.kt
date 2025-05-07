package com.flareframe.viewmodels


import android.util.Log
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
import kotlinx.datetime.Clock
import java.util.Dictionary
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val db: AuthRepository,
    private val supabase: UserRepository,
) : ViewModel() {


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

                 val uuId:String = task.result.user?.uid.toString()
                 Log.d("register", "$email has successfully registered.")
                 registerSupabase(email,  username,uuId)
                 db.LogOut()
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

     fun resetState(){
        _uiState.update {
            it.copy(isRegistered = false, username = "", password = "", confirmPassword = "", email = "", errorMessage = "", inProgress = false)
        }
    }
}

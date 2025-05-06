package com.flareframe.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flareframe.repositories.AuthRepository
import com.flareframe.repositories.UserRepository
import com.flareframe.ui.states.UserState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val authRepository: AuthRepository,
    val userRepository: UserRepository,
) : ViewModel() {
    val _userState = MutableStateFlow(UserState())
    val userState: StateFlow<UserState> = _userState.asStateFlow()
    private val auth = FirebaseAuth.getInstance()
    private val authListener = FirebaseAuth.AuthStateListener { firebase ->
        viewModelScope.launch { // re-check on any auth change
            checkLoggedIn()
        }
    }

    init {

        auth.addAuthStateListener(authListener)
        viewModelScope.launch { checkLoggedIn() }
    }

    override fun onCleared() {
        super.onCleared()
        auth.removeAuthStateListener(authListener)
    }

    suspend fun checkLoggedIn() {
        var firebaseUser: FirebaseUser? = null

        firebaseUser = authRepository.getCurrentUser()

        if (firebaseUser == null) {
            _userState.update {
                it.copy(isLoggedIn = false, isLoading = false)
            }

            return
        }
        val uuId = firebaseUser.uid.toString()


        val supabaseUser = userRepository.fetchUserWithUUID(uuId)
        _userState.update {
            it.copy(
                isLoggedIn = true,
                isLoading = false,
                username = supabaseUser?.username.toString(),
                email = supabaseUser?.email.toString(),
                bio = supabaseUser?.bio,
                pronouns = supabaseUser?.pronouns,
                displayName = supabaseUser?.displayName,
                profilePicUrl = supabaseUser?.profilePicUrl.toString()
            )
        }
    }

}
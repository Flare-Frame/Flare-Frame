package com.flareframe.repositories

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    fun LogIn(email: String, password: String, callback: (Task<AuthResult>) -> Unit)
    fun signUp(email: String, password: String, callback: (Task<AuthResult>) -> Unit)
    fun LogOut()
    suspend fun getCurrentUser(): FirebaseUser?
}
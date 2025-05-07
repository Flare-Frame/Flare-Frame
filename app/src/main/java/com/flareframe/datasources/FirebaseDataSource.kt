package com.flareframe.datasources

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface FirebaseDataSource {
     fun createUserEmailAndPassword(
        email: String,
        password: String,
        callback: (Task<AuthResult>) -> Unit,
    )

     fun signInEmailAndPassword(
        email: String,
        password: String,
        callback: (Task<AuthResult>) -> Unit,
    )

     fun signOut()

      suspend fun getUser(): FirebaseUser?
}
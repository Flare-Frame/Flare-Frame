package com.flareframe.datasources

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseDataSourceImpl @Inject constructor(val auth: FirebaseAuth) : FirebaseDataSource {
    override fun createUserEmailAndPassword(
        email: String,
        password: String,
        callback: (Task<AuthResult>) -> Unit,
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Firebase", "User has been successfully added!")
                    callback(task)
                } else {
                    Log.w("Firebase", "Unable to create the user", task.exception)
                    callback(task)
                }
            }
    }

    override fun signInEmailAndPassword(
        email: String,
        password: String,
        callback: (Task<AuthResult>) -> Unit,
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Firebase", "Successfully signed in user with email $email")
                    callback(task)
                } else {
                    Log.w("Firebase", "Invalid username or password!", task.exception)
                    callback(task)
                }
            }
    }

    override fun signOut() {
        return auth.signOut()
    }

    override suspend fun getUser(): FirebaseUser? {
        var user: FirebaseUser? = auth.currentUser ?: return null
        return try {
            user?.reload()?.await()
            auth.currentUser
        } catch (e: Exception) {
            auth.signOut()
            null
        }
    }


}
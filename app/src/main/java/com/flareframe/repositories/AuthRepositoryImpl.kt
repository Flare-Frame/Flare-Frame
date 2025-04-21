package com.flareframe.repositories

import com.flareframe.datasources.FirebaseDataSource
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.firestoreSettings
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject


class AuthRepositoryImpl @Inject  constructor(private val firebaseDataSource: FirebaseDataSource):
    AuthRepostitory {
    override fun LogIn(email: String, password: String, callback: (Task<AuthResult>) -> Unit) {
       return firebaseDataSource.signInEmailAndPassword(email = email,password = password,callback)
    }

    override fun LogOut() {
        return firebaseDataSource.signOut()
    }

    override fun signUp(email: String, password: String, callback: (Task<AuthResult>) -> Unit) {
        return firebaseDataSource.createUserEmailAndPassword(email,password,callback)
    }
}
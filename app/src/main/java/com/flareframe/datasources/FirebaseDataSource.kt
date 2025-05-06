package com.flareframe.datasources

import android.R
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import javax.security.auth.callback.Callback

public interface FirebaseDataSource {
    public fun createUserEmailAndPassword(email:String, password: String,callback:(Task<AuthResult>)-> Unit)
    public fun signInEmailAndPassword(email: String,password: String,calback:(Task<AuthResult>)-> Unit)
    public fun signOut()

    public suspend fun getUser(): FirebaseUser?
}
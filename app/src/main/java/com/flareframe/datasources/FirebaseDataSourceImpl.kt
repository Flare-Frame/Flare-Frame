package com.flareframe.datasources

import android.util.Log
import androidx.compose.material3.rememberTopAppBarState
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class FirebaseDataSourceImpl @Inject constructor(val auth: FirebaseAuth): FirebaseDataSource {
    override fun createUserEmailAndPassword(
        email: String,
        password: String,
        callback: (Task<AuthResult>) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task->
                if(task.isSuccessful){
                    Log.d("Firebase","User has been successfully added!")
                    callback(task)
                }

                else{
                  Log.w("Firebase", "Unable to create the user",task.exception)
                    callback(task)
                }
            }
    }

    override fun signInEmailAndPassword(
        email: String,
        password: String,
        calback: (Task<AuthResult>) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task->
                if(task.isSuccessful){
                    Log.d("Firebase","Successfully signed in user with email $email")
                }
                else{
                    Log.w("Firebase","Invalid username or password!", task.exception)
                }
            }
    }

    override fun signOut() {
        auth.signOut()
    }
}
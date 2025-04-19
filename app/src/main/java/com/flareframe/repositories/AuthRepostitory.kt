package com.flareframe.repositories

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface AuthRepostitory {
    fun LogIn(email:String,password:String, callback: (Task<AuthResult>)-> Unit)
    fun signUp(email:String,password:String, callback: (Task<AuthResult>)-> Unit)
    fun LogOut()
}
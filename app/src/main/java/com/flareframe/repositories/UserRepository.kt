package com.flareframe.repositories

import com.flareframe.models.User

interface UserRepository {
    suspend fun createUser(user:User,authorisation:String, apikey: String)
}
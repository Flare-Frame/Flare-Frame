package com.flareframe.repositories

import com.flareframe.models.User
import retrofit2.Response

interface UserRepository {
    suspend fun createUser(user:User,authorisation:String, apikey: String): Response<User>
}
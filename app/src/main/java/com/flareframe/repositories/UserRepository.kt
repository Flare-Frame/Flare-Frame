package com.flareframe.repositories

import com.flareframe.models.User


interface UserRepository {
    suspend fun createUser(user:User):Result<User>
    suspend fun fetchUserWithUUID(uuId:String):User?
    suspend fun fetchUserWithUsername(username:String):User?
    suspend fun fetchUserWithDisplayName(displayName:String):User?
    suspend fun updateUsername(newUsername: String,currentUsername:String):Result<User>
    // add more on them
}
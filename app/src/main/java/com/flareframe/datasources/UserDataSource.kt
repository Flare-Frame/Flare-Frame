package com.flareframe.datasources

import com.flareframe.models.User

interface UserDataSource {
    suspend fun createUser(user: User): Result<User>
    suspend fun updateUsername(newUsername: String,currentUsername: String): Result<User>
    suspend fun fetchUserWithUUID(uuId: String): User?
    suspend fun fetchUserWithUsername(username: String): User?
    suspend fun fetchUserWithDisplayName(displayName: String): User?
}
package com.flareframe.repositories

import com.flareframe.datasources.UserRemoteDataSource
import com.flareframe.models.User
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor( private val userRemoteDataSource: UserRemoteDataSource): UserRepository {
    override suspend fun createUser(user: User): Result<User> {
        return userRemoteDataSource.createUser(user)   // do some business logic here
    }

    override suspend fun fetchUserWithUsername(username: String): User? {
        return userRemoteDataSource.fetchUserWithUsername(username)
    }

    override suspend fun fetchUserWithDisplayName(displayName: String): User? {
     return userRemoteDataSource.fetchUserWithDisplayName(displayName)
    }

    override suspend fun fetchUserWithUUID(uuId: String): User? {
        return userRemoteDataSource.fetchUserWithUUID(uuId)
    }
    override suspend fun updateUsername(newUsername: String,currentUsername:String): Result<User> {
        return userRemoteDataSource.updateUsername(newUsername,currentUsername)
    }


}
package com.flareframe.repositories

import com.flareframe.datasources.SupbaseDataSource
import com.flareframe.models.User
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val supabaseDataSource: SupbaseDataSource): UserRepository {
    override suspend fun createUser(user: User, authorisation: String, apikey: String) {
        return supabaseDataSource.createUser(user,apikey,authorisation)
    }
}
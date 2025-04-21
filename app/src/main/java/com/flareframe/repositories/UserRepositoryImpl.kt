package com.flareframe.repositories

import com.flareframe.datasources.SupabaseDataSource
import com.flareframe.models.User
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val supabaseDataSource: SupabaseDataSource): UserRepository {
    override suspend fun createUser(user: User, authorisation: String, apikey: String): Response<User> {
        return supabaseDataSource.createUser(user,apikey,authorisation)
    }
}
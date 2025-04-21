package com.flareframe.datasources

import com.flareframe.models.User
import com.flareframe.services.SupabaseApiService
import retrofit2.Response

import javax.inject.Inject

class SupabaseDataSource @Inject constructor(private val  apiService: SupabaseApiService) {

    suspend fun createUser(user: User, apikey:String, authorisation: String):Response<User>{
        return  apiService.createUser(user = user, apikey = apikey, authorisation = authorisation)
    }
}
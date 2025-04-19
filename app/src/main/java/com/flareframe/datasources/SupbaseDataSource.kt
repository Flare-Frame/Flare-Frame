package com.flareframe.datasources

import androidx.compose.ui.res.stringResource
import com.flareframe.models.User
import com.flareframe.services.SupabaseApiService
import javax.inject.Inject

class SupbaseDataSource @Inject constructor(private val  apiService: SupabaseApiService) {

    suspend fun createUser(user: User, apikey:String, authorisation: String){
        return  apiService.createUser(user = user, apikey = apikey, authorisation = authorisation)
    }
}
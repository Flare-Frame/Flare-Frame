package com.flareframe.services

import android.R
import android.content.res.Resources
import androidx.compose.ui.res.stringResource
import com.flareframe.models.User
import retrofit2.Response
import retrofit2.http.Body

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface SupabaseApiService {
    @GET("User")
    suspend fun getUsers(
        @Header("Authorisation") authorisation: String,
        @Header("apikey") apiKey: String ,
        @Query("select") select: String = "*",
    ): Response<List<User>>

    @POST("User")
    suspend fun createUser(
        @Header("Authorisation") authorisation: String,
        @Header("apikey") apikey:String,
        @Body user: User
    )

    @GET
    suspend fun  getUser(
        @Header("Authorisation") authorisation: String,
        @Header("apikey") apikey: String,
        @Query("select") select: String = "*"
    )
}
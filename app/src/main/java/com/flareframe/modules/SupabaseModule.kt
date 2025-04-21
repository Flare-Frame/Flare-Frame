package com.flareframe.modules


import com.flareframe.services.SupabaseApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import javax.inject.Singleton

private const val Base_URL = "https://dfzvqnqrjouxuzacngwa.supabase.co/rest/v1/"

@Module
@InstallIn(SingletonComponent::class)
object SupabaseModule {

    @Provides
    @Singleton
    fun returnRetrofit(): Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Base_URL)
            .build()
    }

    @Provides
    @Singleton
    fun returnSupabaseAPI(retrofit: Retrofit): SupabaseApiService{
        return retrofit.create(SupabaseApiService::class.java)
    }
}
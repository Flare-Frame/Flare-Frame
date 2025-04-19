package com.flareframe.modules


import com.flareframe.services.SupabaseApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val Base_URL = "https://dfzvqnqrjouxuzacngwa.supabase.co"

@Module
@InstallIn(SingletonComponent::class)
object SupbaseModule {

    @Provides
    fun returnRetrofit(): Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(Base_URL)
            .build()
    }

    @Provides
    fun returnSupabaseAPI(retrofit: Retrofit): SupabaseApiService{
        return retrofit.create(SupabaseApiService::class.java)
    }
}
package com.flareframe.modules


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.serialization.json.Json


import javax.inject.Singleton

private const val Base_URL = "https://dfzvqnqrjouxuzacngwa.supabase.co"
private const val key =
    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImRmenZxbnFyam91eHV6YWNuZ3dhIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDQ2NTg4NzUsImV4cCI6MjA2MDIzNDg3NX0.YlllghJ4GqEdSZpWtF8LJK3dU_vUHAquPPnXYfPCb9o"

@Module
@InstallIn(SingletonComponent::class)
object SupabaseModule {


    @Provides
    @Singleton
    fun returnSupabaseClient(): SupabaseClient {
        return createSupabaseClient(supabaseUrl = Base_URL, supabaseKey = key) {


            install(Postgrest) {
                defaultSchema = "public"
                Json { ignoreUnknownKeys = true }
            }



        }
    }
}
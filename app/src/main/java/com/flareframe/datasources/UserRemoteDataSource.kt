package com.flareframe.datasources


import android.util.Log
import com.flareframe.models.User
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(private val supabaseClient: SupabaseClient) :
    UserDataSource {

    override suspend fun createUser(user: User): Result<User> {
        return try {
            val response = supabaseClient.postgrest.from("User").insert(user).decodeSingle<User>()
            Result.success(response)
        } catch (e: Exception) {
            Log.d("User", "Unable to create user", e)
            Result.failure(e)
        }
    }

    override suspend fun updateUsername(
        newUsername: String,
        currentUsername: String,
    ): Result<User> {
        return try {
            val response = supabaseClient.postgrest.from("User").update({
                set("Username", newUsername)
            }) {
                eq("Username", currentUsername)
            }.decodeSingle<User>()
            Result.success(response)
        } catch (e: Exception) {
            Log.d("User", "Unable to update user", e)
            Result.failure(e)
        }
    }

    override suspend fun fetchUserWithUsername(username: String): User? {
        return try {
            supabaseClient.postgrest.from("User").select {

                eq("Username", username)

            }.decodeSingle<User>()

        } catch (e: Exception) {
            Log.w("User", "Unable to fetch user with username: $username")
            null
        }
    }

    override suspend fun fetchUserWithDisplayName(displayName: String): User? {
        return try {
            supabaseClient.postgrest.from("User").select {

                eq("DisplayName", displayName)

            }.decodeSingle<User>()

        } catch (e: Exception) {
            Log.w("User", "Unable to fetch user with username: $displayName")
            null
        }
    }

    override suspend fun fetchUserWithUUID(uuId: String): User? {
        return try {
            supabaseClient.postgrest.from("User").select {

                eq("UUID", uuId)

            }.decodeSingle<User>()

        } catch (e: Exception) {
            Log.w("User", "Unable to fetch user.",e)
            null
        }
    }


}
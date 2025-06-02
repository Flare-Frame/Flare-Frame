package com.flareframe.datasources

import android.util.Log
import androidx.annotation.experimental.Experimental
import androidx.compose.runtime.saveable.rememberSaveable
import com.flareframe.models.Post
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import okhttp3.Response
import javax.inject.Inject

class PostRemoteDataSource @Inject constructor(val supabaseClient: SupabaseClient): PostDataSource {
    override suspend fun createPost(post: Post): Result<Post> {
      return try {
          val response = supabaseClient.postgrest.from("Post").insert(post).decodeSingle<Post>()
          Result.success(response)
      }catch(e: Exception){
          Log.d("Post","Unable to upload post.")
          Result.failure(e)
      }
    }

    override suspend fun fetchPostsWithUsername(username: String): Result<List<Post>> {
         return try{
             val response =  supabaseClient.postgrest.from("Post").select("*").decodeList<Post>()
             Result.success(response)
         }catch (e: Exception){
             Log.d("Post", "Unable to retrieve any post.", e)
             Result.failure(e)
         }
    }

    override suspend fun fetchPostsWithTag(tag: String): Result<List<Post>> {
        TODO("Not yet implemented")
    }

    override suspend fun updatePostWithId(postId: String): Result<Post> {
        TODO("Not yet implemented")
    }
}
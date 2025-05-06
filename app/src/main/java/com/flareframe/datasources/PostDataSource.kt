package com.flareframe.datasources

import com.flareframe.models.Post

interface PostDataSource {
    suspend fun createPost(post:Post):Result<Post>
    suspend fun fetchPostsWithUsername(username:String):List<Post?>
    suspend fun fetchPostsWithTag(tag:String):List<Post?>
}
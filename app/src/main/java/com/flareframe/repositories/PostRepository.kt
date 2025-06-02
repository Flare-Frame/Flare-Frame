package com.flareframe.repositories

import com.flareframe.models.Post

interface PostRepository {
    suspend fun createPost(post:Post):Result<Post>
}
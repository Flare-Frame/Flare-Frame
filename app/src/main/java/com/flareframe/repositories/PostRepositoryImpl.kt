package com.flareframe.repositories

import com.flareframe.datasources.PostRemoteDataSource
import com.flareframe.models.Post
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(val postRemoteDataSource: PostRemoteDataSource):
    PostRepository {

    override suspend fun createPost(post:Post):Result<Post>{
        return postRemoteDataSource.createPost(post)
    }


}
package com.flareframe.modules

import com.flareframe.datasources.FirebaseDataSource
import com.flareframe.datasources.FirebaseDataSourceImpl
import com.flareframe.repositories.AuthRepositoryImpl
import com.flareframe.repositories.AuthRepostitory
import com.flareframe.repositories.UserRepository
import com.flareframe.repositories.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DatasourceModule {

    @Binds
    abstract fun bindFirebaseDataSource(firebaseDataSourceImpl: FirebaseDataSourceImpl): FirebaseDataSource

}
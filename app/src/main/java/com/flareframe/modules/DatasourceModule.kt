package com.flareframe.modules

import com.flareframe.datasources.FirebaseDataSource
import com.flareframe.datasources.FirebaseDataSourceImpl
import com.flareframe.repositories.AuthRepositoryImpl
import com.flareframe.repositories.AuthRepostitory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class DatasourceModule {

    @Binds
    abstract fun bindFirebaseDataSource(firebaseDataSourceImpl: FirebaseDataSourceImpl): FirebaseDataSource

    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepostitory
}
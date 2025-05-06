package com.flareframe.modules

import com.flareframe.datasources.FirebaseDataSource
import com.flareframe.datasources.FirebaseDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DatasourceModule {

    @Binds
    abstract fun bindFirebaseDataSource(firebaseDataSourceImpl: FirebaseDataSourceImpl): FirebaseDataSource
// add user, and all the other tables under models

}
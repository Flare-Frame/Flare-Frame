package com.flareframe.modules

import com.flareframe.datasources.FirebaseDataSource
import com.flareframe.datasources.FirebaseDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class DatasourceModule {

    @Binds
    abstract fun bindFirebaseDataSource(firebaseDataSourceImpl: FirebaseDataSourceImpl): FirebaseDataSource

}
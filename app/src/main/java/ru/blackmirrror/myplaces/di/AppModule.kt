package ru.blackmirrror.myplaces.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.blackmirrror.myplaces.api.ApiFactory
import ru.blackmirrror.myplaces.api.ApiService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return ApiFactory.create()
    }
}
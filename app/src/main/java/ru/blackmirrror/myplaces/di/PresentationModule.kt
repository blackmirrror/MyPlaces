package ru.blackmirrror.myplaces.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import ru.blackmirrror.myplaces.api.ApiService
import ru.blackmirrror.myplaces.data.repositories.AuthRepository
import ru.blackmirrror.myplaces.database.sharedPrefs.UserSharedPreferences

//@Module
//@InstallIn(ViewModelComponent::class)
//object PresentationModule {
//
//    @Provides
//    @ViewModelScoped
//    fun provideAuthRepository(
//        @ApplicationContext context: Context,
//        service: ApiService,
//        userPrefs: UserSharedPreferences
//    ): AuthRepository {
//        return AuthRepository(
//            context = context,
//            service = service,
//            userPrefs = userPrefs
//        )
//    }
//}
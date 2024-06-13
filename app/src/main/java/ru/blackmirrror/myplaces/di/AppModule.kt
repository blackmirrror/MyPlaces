package ru.blackmirrror.myplaces.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.blackmirrror.myplaces.api.ApiFactory
import ru.blackmirrror.myplaces.api.ApiService
import ru.blackmirrror.myplaces.auth.usecases.LoginUserUseCase
import ru.blackmirrror.myplaces.auth.usecases.RegisterUserUseCase
import ru.blackmirrror.myplaces.data.repositories.AuthRepository
import ru.blackmirrror.myplaces.database.sharedPrefs.UserSharedPreferences
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return ApiFactory.create()
    }

    @Provides
    @Singleton
    fun provideUserSharedPreferences(@ApplicationContext context: Context): UserSharedPreferences {
        return UserSharedPreferences(context)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        @ApplicationContext context: Context,
        service: ApiService,
        userPrefs: UserSharedPreferences
    ): AuthRepository {
        return AuthRepository(
            context = context,
            service = service,
            userPrefs = userPrefs
        )
    }

    @Provides
    fun provideLoginUserUseCase(
        authRepository: AuthRepository
    ): LoginUserUseCase {
        return LoginUserUseCase(authRepository)
    }

    @Provides
    fun provideRegisterUserUseCase(
        authRepository: AuthRepository
    ): RegisterUserUseCase {
        return RegisterUserUseCase(authRepository)
    }
}
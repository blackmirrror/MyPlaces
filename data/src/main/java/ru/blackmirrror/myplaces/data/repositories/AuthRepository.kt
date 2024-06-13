package ru.blackmirrror.myplaces.data.repositories

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import ru.blackmirrror.myplaces.api.ApiService
import ru.blackmirrror.myplaces.data.models.Conflict
import ru.blackmirrror.myplaces.data.models.ErrorCodes.CODE_CONFLICT
import ru.blackmirrror.myplaces.data.models.ErrorCodes.CODE_NOT_FOUND
import ru.blackmirrror.myplaces.data.models.NoInternet
import ru.blackmirrror.myplaces.data.models.NotFound
import ru.blackmirrror.myplaces.data.models.OtherError
import ru.blackmirrror.myplaces.data.models.ResultState
import ru.blackmirrror.myplaces.data.models.ServerError
import ru.blackmirrror.myplaces.data.models.UserRequest
import ru.blackmirrror.myplaces.data.models.UserResponse
import ru.blackmirrror.myplaces.data.models.UserState
import ru.blackmirrror.myplaces.data.models.toUserRequestDto
import ru.blackmirrror.myplaces.data.models.toUserResponse
import ru.blackmirrror.myplaces.data.utils.NetworkUtils
import ru.blackmirrror.myplaces.database.sharedPrefs.DefaultValues.DEFAULT_USER_ID
import ru.blackmirrror.myplaces.database.sharedPrefs.UserSharedPreferences
import java.net.SocketTimeoutException
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val context: Context,
    private val service: ApiService,
    private val userPrefs: UserSharedPreferences
) {
    fun register(user: UserRequest): Flow<UserState> {
        return handleErrors {
            flow {
                emit(UserState.None)
                if (NetworkUtils.isInternetConnected(context)) {
                    val res = service.registerUser(user.toUserRequestDto())
                    if (res.isSuccessful)
                        emitAll(login(user))
                    else if (res.code() == CODE_CONFLICT)
                        emit(UserState.NotAuthorized(Conflict))
                    else
                        emit(UserState.NotAuthorized(ServerError))
                } else {
                    emit(UserState.NotAuthorized(NoInternet))
                }
            }
        }
    }

    fun login(user: UserRequest): Flow<UserState> {
        return handleErrors {
            flow {
                emit(UserState.None)
                if (NetworkUtils.isInternetConnected(context)) {
                    val res = service.loginUser(user.toUserRequestDto())
                    if (res.isSuccessful) {
                        val body = res.body()
                        if (body != null) {
                            userPrefs.id = body.id
                            userPrefs.email = user.email
                            userPrefs.password = user.password
                            userPrefs.username = user.username
                            userPrefs.isGuest = false
                            emit(UserState.Authorized(body.toUserResponse()))
                        }
                    } else if (res.code() == CODE_NOT_FOUND)
                        emit(UserState.NotAuthorized(NotFound))
                    else
                        emit(UserState.NotAuthorized(ServerError))
                } else {
                    emit(loginLocal())
                }
            }
        }
    }

    fun autoLogin(): Flow<UserState> {
        return flow {
            if (userPrefs.isGuest == true)
                emit(UserState.Guest)
            else if (userPrefs.id != DEFAULT_USER_ID && userPrefs.email != null && userPrefs.password != null)
                emitAll(
                    login(
                        UserRequest(
                            email = userPrefs.email!!,
                            password = userPrefs.password!!
                        )
                    )
                )
            else
                emit(UserState.NotAuthorized(OtherError))
        }
    }

    private fun loginLocal(): UserState {
        return if (userPrefs.id != DEFAULT_USER_ID && userPrefs.username != null && userPrefs.password != null) {
            UserState.Authorized(
                UserResponse(
                    userPrefs.id,
                    userPrefs.username!!,
                    userPrefs.email!!
                )
            )
        } else
            UserState.NotAuthorized(NoInternet)
    }

    fun getCurrentUser(): UserResponse? {
        if (userPrefs.id != DEFAULT_USER_ID && userPrefs.email != null) {
            return UserResponse(userPrefs.id, userPrefs.username!!, userPrefs.email!!)
        }
        return null
    }

    fun isGuest(): Boolean {
        return userPrefs.isGuest ?: false
    }

    fun rememberAsGuest() {
        logout()
        userPrefs.isGuest = true
    }

    fun logout() {
        userPrefs.clearPreferences()
    }

    private fun <T> handleErrors(action: suspend () -> Flow<T>): Flow<T> {
        return flow {
            try {
                emitAll(action())
            } catch (e: Exception) {
                emit(UserState.NotAuthorized(ServerError) as T)
            }
        }
    }
}
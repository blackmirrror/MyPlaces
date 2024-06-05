package ru.blackmirrror.myplaces.data.models

import java.lang.Exception

sealed class UserState {
    data object None: UserState()
    data object Guest: UserState()
    data class Authorized(val user: UserResponse? = null): UserState()
    data class NotAuthorized(val error: Exception, val user: UserResponse? = null) : UserState()
}
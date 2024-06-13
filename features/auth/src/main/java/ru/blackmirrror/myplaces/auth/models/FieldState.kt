package ru.blackmirrror.myplaces.auth.models

sealed class FieldState(open val data: String? = null, val error: Exception): Exception() {
    class UsernameState(data: String? = null, error: Exception): FieldState(data , error)
    class EmailState(data: String? = null, error: Exception): FieldState(data , error)
    class PasswordState(data: String? = null, error: Exception): FieldState(data , error)
}
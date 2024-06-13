package ru.blackmirrror.myplaces.auth.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import ru.blackmirrror.myplaces.auth.models.EmptyField
import ru.blackmirrror.myplaces.auth.models.FieldState
import ru.blackmirrror.myplaces.auth.models.NotMatchPattern
import ru.blackmirrror.myplaces.auth.models.PasswordsDifferent
import ru.blackmirrror.myplaces.data.models.UserRequest
import ru.blackmirrror.myplaces.data.models.UserState
import ru.blackmirrror.myplaces.data.repositories.AuthRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        username: String,
        email: String,
        password: String,
        passwordAccess: String
    ): Flow<UserState> {
        return flow {
            if (username.isEmpty())
                emit(UserState.NotAuthorized(FieldState.UsernameState(username, EmptyField)))
            if (email.isEmpty())
                emit(UserState.NotAuthorized(FieldState.EmailState(email, EmptyField)))
            else if (!matchEmailPattern(email))
                emit(UserState.NotAuthorized(FieldState.EmailState(email, NotMatchPattern)))
            else if (password.isEmpty())
                emit(UserState.NotAuthorized(FieldState.PasswordState(password, EmptyField)))
            else if (password != passwordAccess)
                emit(UserState.NotAuthorized(FieldState.PasswordState(passwordAccess, PasswordsDifferent)))
            else
                emitAll(authRepository.register(UserRequest(username, email, password)))
        }
    }

    private fun matchEmailPattern(email: String): Boolean {
        val emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        return Regex(emailPattern).matches(email)
    }
}
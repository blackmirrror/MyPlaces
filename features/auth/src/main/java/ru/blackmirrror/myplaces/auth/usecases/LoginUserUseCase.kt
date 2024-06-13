package ru.blackmirrror.myplaces.auth.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import ru.blackmirrror.myplaces.auth.models.EmptyField
import ru.blackmirrror.myplaces.auth.models.FieldState
import ru.blackmirrror.myplaces.auth.models.NotMatchPattern
import ru.blackmirrror.myplaces.data.models.UserRequest
import ru.blackmirrror.myplaces.data.models.UserState
import ru.blackmirrror.myplaces.data.repositories.AuthRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Flow<UserState> {
        return flow {
            if (email.isEmpty())
                emit(UserState.NotAuthorized(FieldState.EmailState(email, EmptyField)))
            else if (!matchPattern(email))
                emit(UserState.NotAuthorized(FieldState.EmailState(email, NotMatchPattern)))
            else if (password.isEmpty())
                emit(UserState.NotAuthorized(FieldState.PasswordState(password, EmptyField)))
            else
                emitAll(authRepository.login(UserRequest(email = email, password = password)))
        }
    }

    private fun matchPattern(email: String): Boolean {
        val emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        return Regex(emailPattern).matches(email)
    }
}
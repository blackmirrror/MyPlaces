package ru.blackmirrror.myplaces.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.blackmirrror.myplaces.auth.usecases.LoginUserUseCase
import ru.blackmirrror.myplaces.auth.usecases.RegisterUserUseCase
import ru.blackmirrror.myplaces.data.models.UserState
import ru.blackmirrror.myplaces.data.repositories.AuthRepository
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _pageType = MutableStateFlow(LOGIN_PAGE)
    val pageType: StateFlow<Boolean> = _pageType

    private val _userState = MutableStateFlow<UserState>(UserState.None)
    val userState: StateFlow<UserState> = _userState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _userState.value = UserState.None
            delay(100)
            loginUserUseCase.invoke(
                email, password
            ).collect { userState ->
                _userState.value = userState
            }
        }
    }

    fun register(
        username: String,
        email: String,
        password: String,
        passwordAccess: String
    ) {
        viewModelScope.launch {
            _userState.value = UserState.None
            delay(100)
            registerUserUseCase.invoke(
                username, email, password, passwordAccess
            ).collect { userState ->
                _userState.value = userState
            }
        }
    }

    fun rememberAsGuest() {
        authRepository.rememberAsGuest()
    }

    fun changePageType() {
        _pageType.value = !_pageType.value
    }

    companion object {
        const val LOGIN_PAGE = true
        const val REGISTER_PAGE = false
    }

}
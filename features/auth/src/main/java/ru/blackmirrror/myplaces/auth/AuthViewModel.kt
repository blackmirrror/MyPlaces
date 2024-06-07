package ru.blackmirrror.myplaces.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.blackmirrror.myplaces.data.models.UserRequest
import ru.blackmirrror.myplaces.data.models.UserState
import ru.blackmirrror.myplaces.data.repositories.AuthRepository
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _pageType = MutableStateFlow(LOGIN_PAGE)
    val pageType: StateFlow<Boolean> = _pageType

    private val _userState = MutableStateFlow<UserState>(UserState.None)
    val userState: StateFlow<UserState> = _userState

    private val _shouldNavigateToPlaces = MutableStateFlow(false)
    val shouldNavigateToPlaces: StateFlow<Boolean> = _shouldNavigateToPlaces

    fun login(username: String, password: String) {
        viewModelScope.launch {
            authRepository.login(
                UserRequest(username, password)
            ).collect { userState ->
                _userState.value = userState
            }
        }
    }

    fun register(username: String, password: String) {
        viewModelScope.launch {
            authRepository.register(
                UserRequest(username, password)
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

    fun onLoginConfirmed() {
        _shouldNavigateToPlaces.value = true
    }

    fun resetNavigationFlag() {
        _shouldNavigateToPlaces.value = false
    }

    companion object {
        const val LOGIN_PAGE = true
        const val REGISTER_PAGE = false
    }

}
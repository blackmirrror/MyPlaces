package ru.blackmirrror.places

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.blackmirrror.myplaces.data.models.UserState
import ru.blackmirrror.myplaces.data.repositories.AuthRepository
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _userState = MutableStateFlow<UserState>(UserState.None)
    val userState: StateFlow<UserState> = _userState

    init {
        checkAuthentication()
    }

    private fun checkAuthentication() {
        viewModelScope.launch {
            authRepository.autoLogin().collect { userState ->
                _userState.value = userState
            }
        }
    }

    fun rememberAsGuest() {
        authRepository.rememberAsGuest()
    }
}
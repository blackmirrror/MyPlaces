package ru.blackmirrror.myplaces.common

sealed class Screen(val route: String) {
    data object AuthScreen: Screen("auth")
    data object PlacesScreen: Screen("places")
}
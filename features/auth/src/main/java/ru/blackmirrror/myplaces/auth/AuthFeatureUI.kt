package ru.blackmirrror.myplaces.auth

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun AuthFeature(navController: NavHostController) {
    AuthFeature(hiltViewModel<AuthViewModel>(), navController)
}

@Composable
internal fun AuthFeature(viewModel: AuthViewModel, navController: NavHostController) {
    Text(text = "Auth")
}
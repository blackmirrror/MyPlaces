package ru.blackmirrror.myplaces.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ru.blackmirrror.myplaces.auth.AuthViewModel.Companion.LOGIN_PAGE
import ru.blackmirrror.myplaces.common.Routes
import ru.blackmirrror.myplaces.data.models.UserState

@Composable
fun AuthFeature(navController: NavHostController) {
    AuthFeature(hiltViewModel<AuthViewModel>(), navController)
}

@Composable
internal fun AuthFeature(viewModel: AuthViewModel, navController: NavHostController) {
    NavigationHandler(viewModel, navController)
    CollectUserState(viewModel)

    val pageType by viewModel.pageType.collectAsState()

    LoginScreen(
        pageType = pageType,
        onLogin = { username, password ->
            if (pageType == LOGIN_PAGE)
                viewModel.login(username, password)
            else
                viewModel.register(username, password)
        },
        onRegister = {
            viewModel.changePageType()
        },
        onGuestLogin = {
            viewModel.rememberAsGuest()
            viewModel.onLoginConfirmed()
        }
    )
}

@Composable
fun CollectUserState(viewModel: AuthViewModel) {
    val userState by viewModel.userState.collectAsState()
    if (userState is UserState.Authorized) {
        viewModel.onLoginConfirmed()
    }
}

@Composable
fun NavigationHandler(viewModel: AuthViewModel, navController: NavHostController) {
    LaunchedEffect(viewModel.shouldNavigateToPlaces) {
        if (viewModel.shouldNavigateToPlaces.value) {
            navController.navigate(Routes.PLACES) {
                popUpTo(Routes.AUTH_FEATURE) { inclusive = true }
            }
            viewModel.resetNavigationFlag()
        }
    }
}

@Composable
fun LoginScreen(
    pageType: Boolean,
    onLogin: (String, String) -> Unit,
    onRegister: () -> Unit,
    onGuestLogin: () -> Unit
) {
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    val title =
        stringResource(if (pageType == LOGIN_PAGE) R.string.title_login else R.string.title_register)
    val link =
        stringResource(if (pageType == LOGIN_PAGE) R.string.link_register else R.string.link_enter)
    val buttonText =
        stringResource(if (pageType == LOGIN_PAGE) R.string.link_enter else R.string.link_register)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            //style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text(stringResource(R.string.hint_username)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text(stringResource(R.string.hint_password)) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        )

        Button(
            onClick = { onLogin(username.value, password.value) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text(buttonText)
        }

        Text(
            text = link,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable(onClick = onRegister)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.link_enter_as_guest),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable(onClick = onGuestLogin)
        )
    }
}
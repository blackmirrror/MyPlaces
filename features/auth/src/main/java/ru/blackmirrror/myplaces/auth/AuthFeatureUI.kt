package ru.blackmirrror.myplaces.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ru.blackmirrror.myplaces.auth.AuthViewModel.Companion.LOGIN_PAGE
import ru.blackmirrror.myplaces.auth.models.EmptyField
import ru.blackmirrror.myplaces.auth.models.FieldState
import ru.blackmirrror.myplaces.auth.models.NotMatchPattern
import ru.blackmirrror.myplaces.auth.models.PasswordsDifferent
import ru.blackmirrror.myplaces.common.Screen
import ru.blackmirrror.myplaces.common.ui.ErrorNotification
import ru.blackmirrror.myplaces.data.models.Conflict
import ru.blackmirrror.myplaces.data.models.NoInternet
import ru.blackmirrror.myplaces.data.models.NotFound
import ru.blackmirrror.myplaces.data.models.ServerError
import ru.blackmirrror.myplaces.data.models.UserState

@Composable
fun AuthScreen(navController: NavHostController) {
    AuthScreen(hiltViewModel<AuthViewModel>(), navController)
}

@Composable
internal fun AuthScreen(viewModel: AuthViewModel, navController: NavHostController) {
    val pageType by viewModel.pageType.collectAsState()

    LoginRegisterScreen(
        pageType = pageType,
        onLogin = { email, password ->
            viewModel.login(email, password)
        },
        onRegister = { username, email, password, passwordAccess ->
            viewModel.register(username, email, password, passwordAccess)
        },
        onChangePageType = {
            viewModel.changePageType()
        },
        onGuestLogin = {
            viewModel.rememberAsGuest()
        },
        navigateToPlaces = { navigateToPlaces(navController) },
        viewModel = viewModel
    )
}

fun navigateToPlaces(navController: NavHostController) {
    navController.navigate(Screen.PlacesScreen.route) {
        popUpTo(Screen.AuthScreen.route) { inclusive = true }
    }
}


@Composable
fun LoginRegisterScreen(
    pageType: Boolean,
    onLogin: (email: String, password: String) -> Unit,
    onRegister: (username: String, email: String, password: String, passwordAccess: String) -> Unit,
    onChangePageType: () -> Unit,
    onGuestLogin: () -> Unit,
    navigateToPlaces: () -> Unit,
    viewModel: AuthViewModel
) {
    val userState by viewModel.userState.collectAsState()

    var connectionVisibility by remember { mutableStateOf(false) }

    var usernameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var passwordAccessError by remember { mutableStateOf<String?>(null) }
    var notificationMessageError by remember { mutableStateOf("") }

    HandleUserState(
        userState = userState,
        navigateToPlaces = navigateToPlaces,
        setConnectionVisibility = { connectionVisibility = it },
        setNotificationMessageError = { notificationMessageError = it },
        setUsernameError = { usernameError = it },
        setEmailError = { emailError = it },
        setPasswordError = { passwordError = it },
        setPasswordAccessError = { passwordAccessError = it }
    )

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordAccess by remember { mutableStateOf("") }

    val title =
        stringResource(if (pageType == LOGIN_PAGE) R.string.title_login else R.string.title_register)
    val link =
        stringResource(if (pageType == LOGIN_PAGE) R.string.link_register else R.string.link_enter)
    val buttonText =
        stringResource(if (pageType == LOGIN_PAGE) R.string.link_enter else R.string.link_register)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        ErrorNotification(
            visibility = connectionVisibility,
            message = notificationMessageError,
            onRefresh = {
                if (pageType == LOGIN_PAGE) {
                    onLogin(email, password)
                } else {
                    onRegister(username, email, password, passwordAccess)
                }
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.title_welcome),
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
            Text(
                text = title,
                modifier = Modifier.padding(bottom = 16.dp),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            AnimatedVisibility(!pageType) {
                CustomOutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = stringResource(R.string.hint_username),
                    leadingIcon = painterResource(R.drawable.icon_account_circle_24),
                    isPassword = false,
                    error = usernameError
                )
            }
            CustomOutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = stringResource(R.string.hint_email),
                leadingIcon = painterResource(R.drawable.icon_email_24),
                isPassword = false,
                error = emailError
            )

            CustomOutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = stringResource(R.string.hint_password),
                leadingIcon = painterResource(R.drawable.icon_key_24),
                isPassword = true,
                error = passwordError
            )

            AnimatedVisibility(!pageType) {
                CustomOutlinedTextField(
                    value = passwordAccess,
                    onValueChange = { passwordAccess = it },
                    label = stringResource(R.string.hint_password_access),
                    leadingIcon = painterResource(R.drawable.icon_key_24),
                    isPassword = true,
                    error = passwordAccessError
                )
            }

            Button(
                onClick = {
                    if (pageType == LOGIN_PAGE) {
                        onLogin(email, password)
                    } else {
                        onRegister(username, email, password, passwordAccess)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp, top = 16.dp)
                    .height(50.dp)
            ) {
                Text(buttonText)
            }

            Text(
                text = link,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable(onClick = onChangePageType)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.link_enter_as_guest),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable(onClick = onGuestLogin)
            )
        }
    }
}

@Composable
fun HandleUserState(
    userState: UserState,
    navigateToPlaces: () -> Unit,
    setConnectionVisibility: (Boolean) -> Unit,
    setNotificationMessageError: (String) -> Unit,
    setUsernameError: (String?) -> Unit,
    setEmailError: (String?) -> Unit,
    setPasswordError: (String?) -> Unit,
    setPasswordAccessError: (String?) -> Unit
) {
    when (userState) {
        is UserState.Authorized -> navigateToPlaces()
        is UserState.Guest -> navigateToPlaces()
        is UserState.None -> {
            setUsernameError(null)
            setEmailError(null)
            setPasswordError(null)
            setPasswordAccessError(null)
            setConnectionVisibility(false)
        }

        is UserState.NotAuthorized -> {
            when (val fieldState = userState.error) {
                is FieldState.UsernameState -> {
                    when (fieldState.error) {
                        is EmptyField -> setUsernameError(stringResource(R.string.error_empty_field))
                    }
                }

                is FieldState.EmailState -> {
                    when (fieldState.error) {
                        is EmptyField -> setEmailError(stringResource(R.string.error_empty_field))
                        is NotMatchPattern -> setEmailError(stringResource(R.string.error_not_match_pattern))
                    }
                }

                is FieldState.PasswordState -> {
                    when (fieldState.error) {
                        is EmptyField -> setPasswordError(stringResource(R.string.error_empty_field))
                        is PasswordsDifferent -> setPasswordAccessError(stringResource(R.string.error_passwords_different))
                    }
                }

                is NotFound -> setPasswordError(stringResource(R.string.error_email_or_password_not_found))
                is Conflict -> setPasswordError(stringResource(R.string.error_passwords_different))

                is NoInternet -> {
                    setConnectionVisibility(true)
                    setNotificationMessageError(stringResource(R.string.error_connection))
                }

                is ServerError -> {
                    setConnectionVisibility(true)
                    setNotificationMessageError(stringResource(R.string.error_server))
                }
            }
        }
    }
}


@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    error: String? = null,
    label: String,
    leadingIcon: Painter,
    isPassword: Boolean,
    modifier: Modifier = Modifier
) {

    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .padding(8.dp)
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            leadingIcon = {
                Icon(
                    painter = leadingIcon,
                    contentDescription = null,
                    modifier = Modifier.size(AssistChipDefaults.IconSize)
                )
            },
            visualTransformation =
            if (!isPassword || passwordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = if (isPassword) {
                {
                    Icon(
                        painter = painterResource(
                            if (passwordVisible) R.drawable.icon_visibility_24
                            else R.drawable.icon_visibility_off_24
                        ),
                        contentDescription = null,
                        modifier = Modifier.clickable { passwordVisible = !passwordVisible }
                    )
                }
            } else null,

            modifier = modifier
                .fillMaxWidth(),
            colors = if (error != null) OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.error,
                unfocusedBorderColor = MaterialTheme.colorScheme.error,
                focusedLabelColor = MaterialTheme.colorScheme.error,
                cursorColor = MaterialTheme.colorScheme.error
            ) else OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )
        if (error != null) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

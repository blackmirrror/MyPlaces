package ru.blackmirrror.places

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ru.blackmirrror.myplaces.common.Screen
import ru.blackmirrror.myplaces.data.models.NoInternet
import ru.blackmirrror.myplaces.data.models.UserState
@Composable
fun PlacesFeature(navController: NavHostController) {
    PlacesFeature(hiltViewModel<PlacesViewModel>(), navController)
}

@Composable
internal fun PlacesFeature(viewModel: PlacesViewModel, navController: NavHostController) {
    CollectUserState(viewModel, navController)
}

@Composable
fun CollectUserState(viewModel: PlacesViewModel, navController: NavHostController) {
    val userState by viewModel.userState.collectAsState()
    if (userState is UserState.NotAuthorized) {
        when ((userState as UserState.NotAuthorized).error) {
            is NoInternet -> {}
        }
        AuthorizeDialog(viewModel, navController)
    }
}

@Composable
fun AuthorizeDialog(viewModel: PlacesViewModel, navController: NavHostController) {
    val openAlertDialog = remember { mutableStateOf(true) }

    when {
        openAlertDialog.value -> {
            AlertDialogExample(
                onDismissRequest = {
                    openAlertDialog.value = false
                    viewModel.rememberAsGuest()
                },
                onConfirmation = {
                    openAlertDialog.value = false
                    navController.navigate(Screen.AuthScreen.route) {
                        popUpTo(Screen.PlacesScreen.route) { inclusive = true }
                    }
                },
                dialogTitle = stringResource(R.string.dialog_title_enter),
                dialogText = stringResource(R.string.dialog_message_enter),
                icon = Icons.Default.Info
            )
        }
    }
}

@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(stringResource(R.string.dialog_confirm_enter))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(stringResource(R.string.dialog_cancel_enter))
            }
        }
    )
}
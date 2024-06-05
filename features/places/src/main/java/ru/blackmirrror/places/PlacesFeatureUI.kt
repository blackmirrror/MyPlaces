package ru.blackmirrror.places

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ru.blackmirrror.myplaces.common.Routes
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
        DialogExamples(viewModel, navController)
    }
}

@Composable
fun DialogExamples(viewModel: PlacesViewModel, navController: NavHostController) {
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
                    viewModel.onLoginConfirmed()
                },
                dialogTitle = "Вход в аккаунт",
                dialogText = "Вы можете залогиниться, чтобы иметь больше функционала в приложении",
                icon = Icons.Default.Info
            )
        }
    }

    LaunchedEffect(viewModel.shouldNavigateToAuth) {
        if (viewModel.shouldNavigateToAuth.value) {
            navController.navigate(Routes.AUTH_FEATURE) {
                popUpTo(Routes.PLACES) { inclusive = true }
            }
            viewModel.resetNavigationFlag()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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
                Text("Войти")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Остаться гостем")
            }
        }
    )
}
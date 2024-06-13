package ru.blackmirrror.myplaces

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.blackmirrror.myplaces.common.theme.MyPlacesTheme
import dagger.hilt.android.AndroidEntryPoint
import ru.blackmirrror.myplaces.auth.AuthScreen
import ru.blackmirrror.myplaces.common.Screen
import ru.blackmirrror.places.PlacesFeature

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyPlacesTheme {
                Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background))
                Navigation()
            }
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.PlacesScreen.route) {
        composable(Screen.AuthScreen.route) { AuthScreen(navController) }
        composable(Screen.PlacesScreen.route) { PlacesFeature(navController) }
    }
}
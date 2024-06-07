package ru.blackmirrror.myplaces

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.blackmirrror.myplaces.auth.AuthFeature
import ru.blackmirrror.myplaces.common.Routes
import ru.blackmirrror.myplaces.ui.theme.MyPlacesTheme
import ru.blackmirrror.places.PlacesFeature

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyPlacesTheme {
                Navigation()
            }
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.AUTH_FEATURE) {
        composable(Routes.AUTH_FEATURE) { AuthFeature(navController) }
        composable(Routes.PLACES) { PlacesFeature(navController) }
    }
}
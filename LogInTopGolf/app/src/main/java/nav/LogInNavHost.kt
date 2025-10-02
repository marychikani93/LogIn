package nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ui.components.LogInScreen
import ui.components.WelcomeScreen

@Composable
fun MyApp() {
    val navController = rememberNavController() //manage the back stack and display appropriate screens
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LogInScreen(
                onNavigateToWelcomeScreen = { username ->
                    navController.navigate("welcomeScreen/$username")
                }
            )
        }
        composable("welcomeScreen/{username}") { backStackEntry -> //specify the argument in the rout string
            val username = backStackEntry.arguments?.getString("username")
            WelcomeScreen(username = username, onBackPressed = {
                navController.navigate("login") {
                    popUpTo("login") {
                        inclusive = true //remove all destinations
                    }
                    launchSingleTop = true // prevents multiple Login Screen on the back stack
                }
            })
        }
    }
}
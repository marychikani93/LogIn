package ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import data.LogInSideEffect
import ui.LogInViewModel

@Composable
fun LogInScreen(
    onNavigateToWelcomeScreen: (String) -> Unit,
    logInViewModel: LogInViewModel = viewModel() //retrieve the instance of the viewmodel
) {

    //added key to identify the effect instance and prevent to be recomposed if not needed
    LaunchedEffect(key1 = Unit) { //run coroutines to handle side effect event
        logInViewModel.loginEffect.collect { effect -> //collecting the one time event
            when (effect) {
                is LogInSideEffect.NavigateToWelcomeScreen -> {
                    onNavigateToWelcomeScreen(effect.username)
                }
            }
        }
    }

    LogInForm(logInViewModel)
}
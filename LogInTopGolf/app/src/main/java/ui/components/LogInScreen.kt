package ui.components

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import data.LogInSideEffect
import ui.LogInViewModel

@Composable
fun LogInScreen(
    onNavigateToWelcomeScreen: (String) -> Unit,
    logInViewModel: LogInViewModel = viewModel()
) {
    LaunchedEffect(key1 = Unit) {
        logInViewModel.loginEffect.collect { effect ->
            when (effect) {
                is LogInSideEffect.NavigateToWelcomeScreen -> {
                    onNavigateToWelcomeScreen(effect.username)
                }
            }
        }
    }

    Surface {
        LogInForm(logInViewModel)
    }
}
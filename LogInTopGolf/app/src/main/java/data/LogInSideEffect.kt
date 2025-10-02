package data

//Side effect - to any change in the app  state outside the scope of a composable function
sealed class LogInSideEffect {
    data class NavigateToWelcomeScreen(val username: String) : LogInSideEffect()
}
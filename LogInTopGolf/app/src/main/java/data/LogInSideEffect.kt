package data

//Shared flow for one time events
sealed class LogInSideEffect {
    data class NavigateToWelcomeScreen(val username: String) : LogInSideEffect()
}
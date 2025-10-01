package ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.LogInState
import data.LogInSideEffect
import data.LoginUiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LogInViewModel : ViewModel() {

    private val _logInEffect = Channel<LogInSideEffect>()
    val loginEffect = _logInEffect.receiveAsFlow()

    private val _logInState = MutableStateFlow(LogInState())
    val logInState: StateFlow<LogInState> = _logInState

    fun onLoginState(action: LoginUiEvent) {
        when (action) {
            is LoginUiEvent.UpdatePassword -> {
                _logInState.update {
                    it.copy(password = action.password.orEmpty())
                }
            }

            is LoginUiEvent.UpdateUsername -> {
                _logInState.update {
                    it.copy(username = action.username.orEmpty())
                }
            }

            is LoginUiEvent.LogIn -> onClick()
        }
    }

    private fun clearCredentials() {
        _logInState.update {
            it.copy(
                username = "",
                password = "",
                isLoading = false,
                errorMessage = null,
            )
        }
    }

    private fun onClick() {
        viewModelScope.launch {
            _logInState.update {
                it.copy(isLoading = true)
            }
            try {
                if (logInState.value.isNotEmpty() &&
                    logInState.value.password.uppercase() == "PASSWORD"
                ) {
                    _logInState.update {
                        it.copy(isLoading = false)
                    }
                    _logInEffect.send(LogInSideEffect.NavigateToWelcomeScreen(logInState.value.username))
                    clearCredentials()
                } else {
                    _logInState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Your username or password are incorrect",
                        )
                    }
                }
            } catch (e: Exception) {
                _logInState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Not able to Log In, please try again",
                    )
                }
            }
        }
    }

}
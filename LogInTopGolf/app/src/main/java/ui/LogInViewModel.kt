package ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import com.example.logintopgolf.R
import data.LogInState
import data.LogInSideEffect
import data.LoginUiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LogInViewModel(application: Application) : AndroidViewModel(application) {

    //To handle side-effect that should be consumed once by the UI
    private val _logInEffect = Channel<LogInSideEffect>()
    val loginEffect = _logInEffect.receiveAsFlow() //expose as a flow for consumption

    //backing property to avoid state updates from other classes
    private val _logInState = MutableStateFlow(LogInState())
    //UI collects the data from this state and get any update
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

    private fun onClick() {
        viewModelScope.launch {
            _logInState.update {
                it.copy(isLoading = true)
            }
            try {
                if (logInState.value.isNotEmpty() &&
                    logInState.value.password.uppercase() == PASSWORD_VALUE
                ) {
                    _logInState.update {
                        it.copy(isLoading = false)
                    }
                    //sending the event to the channel within a coroutine scope
                    _logInEffect.send(LogInSideEffect.NavigateToWelcomeScreen(logInState.value.username))
                } else {
                    _logInState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = application.getString(R.string.error_message1),
                        )
                    }
                }
            } catch (e: Exception) {
                _logInState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: application.getString(R.string.error_message2),
                    )
                }
            }
        }
    }

    companion object {
        const val PASSWORD_VALUE = "PASSWORD" //declare a compile-time constant
    }
}
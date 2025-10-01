package data

import androidx.compose.runtime.Immutable

//UI Events
@Immutable
sealed class LoginUiEvent {
    data class UpdateUsername(val username: String?) : LoginUiEvent()
    data class UpdatePassword(val password: String?) : LoginUiEvent()
    object LogIn : LoginUiEvent()
}
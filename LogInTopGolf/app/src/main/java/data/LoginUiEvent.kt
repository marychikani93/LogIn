package data

import androidx.compose.runtime.Immutable

//UI Events, represents user actions to trigger state changes
@Immutable
sealed class LoginUiEvent {
    data class UpdateUsername(val username: String?) : LoginUiEvent()
    data class UpdatePassword(val password: String?) : LoginUiEvent()
    object LogIn : LoginUiEvent()
}
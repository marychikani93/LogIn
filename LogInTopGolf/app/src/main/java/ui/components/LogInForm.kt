package ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import data.LoginUiEvent
import ui.LogInViewModel

@Composable
fun LogInForm(logInViewModel: LogInViewModel) {
    val state by logInViewModel.logInState.collectAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        UserNameField(
            onChange = { logInViewModel.onLoginState(LoginUiEvent.UpdateUsername(it)) },
            modifier = Modifier.fillMaxWidth()
        )
        PasswordField(
            onChange = { logInViewModel.onLoginState(LoginUiEvent.UpdatePassword(it)) },
            onClick = { logInViewModel.onLoginState(LoginUiEvent.LogIn) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            enabled = state.isNotEmpty(),
            onClick = {
                logInViewModel.onLoginState(LoginUiEvent.LogIn)
            }) {
            Text("LOG IN")
        }

        if (state.isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
        }

        state.errorMessage?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )

        }
    }
}

@Composable
fun UserNameField(
    onChange: (String) -> Unit,
    modifier: Modifier,
) {
    var text by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onChange(text)
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "UserNameField"
            )
        },
        modifier = modifier.padding(2.dp),
        placeholder = { Text("Insert your username") },
        label = { Text("Username: ") },
        singleLine = true,
    )
}

@Composable
fun PasswordField(
    onChange: (String) -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    var passText by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        value = passText,
        onValueChange = {
            passText = it
            onChange(passText)
        },
        keyboardActions = KeyboardActions(
            onDone = { onClick() }
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Key,
                contentDescription = "UserNameField"
            )
        },
        modifier = modifier.padding(2.dp),
        placeholder = { Text("Insert your password") },
        label = { Text("Password: ") },
        singleLine = true,
    )
}
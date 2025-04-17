package com.fps.demoapp.ui.screen.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fps.demoapp.ui.viewModel.LoginViewModel


@Composable
fun MainScreen(
    loginViewModel: LoginViewModel,
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val loginUiState by loginViewModel.uiState.collectAsStateWithLifecycle()
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Welcome ",
                style = MaterialTheme.typography.bodyLarge)

            Text(
                text = loginUiState.username,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(text = " !",
                style = MaterialTheme.typography.bodyLarge)
        }

        Button(
            onClick = { logout(loginViewModel, onBackButtonClick) },
            modifier = Modifier.widthIn(min = 240.dp)
        ) {

            Text(
                text = "Logout",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

private fun logout(
    loginViewModel: LoginViewModel,
    onBackButtonClick: () -> Unit
) {
    loginViewModel.logout()
    onBackButtonClick()
}
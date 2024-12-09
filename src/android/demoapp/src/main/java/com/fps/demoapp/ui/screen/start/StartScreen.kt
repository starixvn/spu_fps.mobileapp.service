package com.fps.demoapp.ui.screen.start

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fps.demoapp.R
import com.fps.demoapp.ui.theme.DemoAppTheme
import com.fps.demoapp.ui.viewModel.EnvironmentConfig
import com.fps.demoapp.ui.viewModel.LoginViewModel

@Composable
fun StartScreen(
    loginViewModel: LoginViewModel,
    onLoginButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val loginUiState by loginViewModel.uiState.collectAsStateWithLifecycle()
    val localFocusManager = LocalFocusManager.current

    val isDropDownExpanded = remember {
        mutableStateOf(false)
    }

    val itemPosition = remember {
        mutableIntStateOf(0)
    }

    Column(
        modifier = modifier.pointerInput(Unit) {
            detectTapGestures(onTap = {
                localFocusManager.clearFocus()
            })
        },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        StartScreenImage()

        Text(text = "FPS Mobile Device Identifier",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center)

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                isDropDownExpanded.value = true
            }
        ) {
            Text(text = "${EnvironmentConfig.entries.first{it.url == loginViewModel.getEnv()}.title}: ${loginViewModel.getEnv()}")
            DropdownMenu(
                expanded = isDropDownExpanded.value,
                onDismissRequest = {
                    isDropDownExpanded.value = false
                }) {
                EnvironmentConfig.entries.forEachIndexed { index, env ->
                    DropdownMenuItem(
                        text = {Text(text=env.title)},
                        onClick = {
                            isDropDownExpanded.value = false
                            itemPosition.intValue = index
                            loginViewModel.updateEnv(env.url)
                        })

                }

            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = loginUiState.subscriber,
            onValueChange = { value -> loginViewModel.updateSubscriber(value)},
            label = { Text("Subscriber") },
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = loginUiState.username,
            onValueChange = { value -> loginViewModel.updateUserName(value)},
            label = { Text("Username") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { login(context, loginViewModel, onLoginButtonClicked) },
            modifier = Modifier.widthIn(min = 240.dp)) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.labelLarge)
        }
    }
}

private fun login(
    context: Context,
    loginViewModel: LoginViewModel,
    onLoginButtonClicked: () -> Unit
) {
    loginViewModel.login(context)
    onLoginButtonClicked()
}

@Composable
fun StartScreenImage() {
    val image = painterResource(id = R.drawable.fingerprint)
    Image(painter = image, contentDescription = null)
}

@Composable
@Preview
fun StartScreenPreview() {
    DemoAppTheme {
        StartScreen(loginViewModel = viewModel(), onLoginButtonClicked={})
    }
}
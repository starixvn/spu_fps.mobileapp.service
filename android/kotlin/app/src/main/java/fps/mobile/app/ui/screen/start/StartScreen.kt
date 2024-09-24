package fps.mobile.app.ui.screen.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddemo.ui.theme.FPSDemoTheme
import fps.mobile.app.R
import fps.mobile.app.ui.viewModel.LoginViewModel

@Composable
fun StartScreen(
    loginViewModel: LoginViewModel,
    onLoginButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val loginUiState by loginViewModel.uiState.collectAsStateWithLifecycle()
    var passwordVisibility by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        StartScreenImage()

        Text(text = "FPS Mobile Device Identifier",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = loginUiState.username,
            onValueChange = { value -> loginViewModel.updateUserName(value)},
            label = { Text("Username") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = loginUiState.password,
            onValueChange = { value -> loginViewModel.updatePassword(value)},
            label = { Text("Password") },
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Visibility Icon"
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = if (passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { login(loginViewModel, onLoginButtonClicked) },
            modifier = Modifier.widthIn(min = 240.dp)) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.labelLarge)
        }
    }
}

private fun login(
    loginViewModel: LoginViewModel,
    onLoginButtonClicked: () -> Unit) {

    if(loginViewModel.login())
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
    FPSDemoTheme {
        StartScreen(loginViewModel = viewModel(), onLoginButtonClicked={})
    }
}
package fps.mobile.app.ui.screen.deviceInfo

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import fps.mobile.app.ui.viewModel.LoginViewModel

@Composable
fun DeviceInfoScreen(
    loginViewModel: LoginViewModel,
    modifier: Modifier = Modifier,
) {
    val loginUiState by loginViewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier,
    ) {
        Text(text = "Device Information ${loginUiState.username}")
    }
}
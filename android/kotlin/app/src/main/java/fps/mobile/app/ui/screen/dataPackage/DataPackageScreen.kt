package fps.mobile.app.ui.screen.dataPackage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fps.mobile.app.data.DataLoader
import fps.mobile.app.ui.viewModel.LoginViewModel

@Composable
fun DataPackageScreen(
    loginViewModel: LoginViewModel,
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val loginUiState by loginViewModel.uiState.collectAsStateWithLifecycle()
    val dataPackage: String = DataLoader.getDataPackage(loginUiState.username)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Sending this package to FPS",
            style = MaterialTheme.typography.titleLarge)

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = dataPackage)
        }

        Button(
            onClick = onBackButtonClick,
            modifier = Modifier.widthIn(min = 240.dp)
        ) {

            Text(
                text = "Back to begin",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}
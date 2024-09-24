package fps.mobile.app.ui.screen.deviceInfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import fps.mobile.app.data.InfoModel
import fps.mobile.app.ui.viewModel.LoginViewModel

@Composable
fun DeviceInfoScreen(
    loginViewModel: LoginViewModel,
    onNextButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val loginUiState by loginViewModel.uiState.collectAsStateWithLifecycle()
    val getDeviceInfoGroups = DataLoader.getDeviceInfoGroups()

    Column(
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ){
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

            Text(text = "This is your device information",
                style = MaterialTheme.typography.titleMedium)

            getDeviceInfoGroups.forEach { group ->
                Spacer(modifier = Modifier.height(20.dp))

                DeviceInfoCard(
                    group = group,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
        ) {
            Button(
                onClick = onNextButtonClicked,
                modifier = Modifier.widthIn(min = 240.dp)) {
                Text(
                    text = "Next",
                    style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

@Composable
private fun DeviceInfoCard(
    group: String,
    modifier: Modifier = Modifier
) {
    var deviceInfoList = DataLoader.getDeviceInfoByGroup(group).map {
        InfoModel(it.name.split('|').last(), it.value)
    }
    if(deviceInfoList.size > 8)
        deviceInfoList = deviceInfoList.slice(IntRange(0, 8))

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
    ) {
        Text(text = group,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(bottom = 12.dp))

        deviceInfoList.forEach {
            Card(
                modifier = modifier
                    .padding(8.dp)
                    .heightIn(max = 400.dp)
            ) {
                Column (
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(text = it.getName(),
                        style = MaterialTheme.typography.titleMedium)

                    Text(text = it.getValue(),
                        style = MaterialTheme.typography.bodyMedium)
                }
            }

        }
    }

}
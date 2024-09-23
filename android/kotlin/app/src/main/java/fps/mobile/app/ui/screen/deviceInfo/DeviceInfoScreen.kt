package fps.mobile.app.ui.screen.deviceInfo

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DeviceInfoScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(text = "Device Information")
    }
}
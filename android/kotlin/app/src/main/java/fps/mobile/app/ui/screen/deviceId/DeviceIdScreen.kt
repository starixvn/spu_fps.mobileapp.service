package fps.mobile.app.ui.screen.deviceId

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import fps.mobile.app.data.DeviceInfoModel
import fps.mobile.client.DeviceIdentify

@Composable
fun DeviceIdScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val deviceInfoList = DeviceIdentify.getDeviceIds()

    val newDeviceInfoList: List<DeviceInfoModel> = deviceInfoList.map {
        DeviceInfoModel(it.name, it.value)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        newDeviceInfoList.forEach{
            Column {
                Text(
                    text = it.getPropertyName(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold)

                TextButton(
                    onClick = {
                        val clipboardManager =
                            context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                        val clipData: ClipData = ClipData.newPlainText("text", it.getPropertyValue())

                        clipboardManager.setPrimaryClip(clipData)

                        Toast.makeText(context, "Copied to Clipboard", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Text(
                        text = it.getPropertyValue(),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }

                HorizontalDivider(
                    modifier = modifier
                        .height(2.dp)
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }


    }
}
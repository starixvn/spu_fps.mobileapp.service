package fps.mobile.app.ui.screen.fingerprint

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
import androidx.navigation.NavHostController
import fps.mobile.app.FPSDemoScreen
import fps.mobile.app.data.DataLoader
import fps.mobile.app.data.InfoModel
import fps.mobile.app.ui.components.InfoComponent
import fps.mobile.client.DeviceIdentify

@Composable
fun FingerprintScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val deviceInfoList = DataLoader.getFingerprints()

    val newDeviceInfoList: List<InfoModel> = deviceInfoList.map {
        InfoModel(it.name, it.value)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
    ) {
        newDeviceInfoList.forEach{
            InfoComponent(info = it, onNameClick = { navController.navigate(FPSDemoScreen.DeviceInfo.route.replace("{fingerprint}",it.getName())) })
        }
    }
}
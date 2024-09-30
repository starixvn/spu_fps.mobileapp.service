package fps.mobile.app.ui.screen.fingerprint

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fps.mobile.app.data.DataLoader
import fps.mobile.app.data.InfoModel
import fps.mobile.app.ui.components.InfoComponent

@Composable
fun FingerprintScreen(
    onFingerprintClick: (fingerPrint: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val deviceInfoList = DataLoader.getFingerprints()

    val newDeviceInfoList: List<InfoModel> = deviceInfoList.map {
        InfoModel(it.name, it.value)
    }

    Column(
        modifier = modifier
    ) {
        newDeviceInfoList.forEach{
            InfoComponent(info = it, onNameClick = { onFingerprintClick(it.getName()) })
        }
    }
}
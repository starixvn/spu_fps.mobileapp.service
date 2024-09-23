package fps.mobile.app.ui.screen.fingerprint

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import fps.mobile.app.data.DataLoader
import fps.mobile.app.data.InfoModel
import fps.mobile.app.ui.components.InfoComponent

@Composable
fun FingerprintScreen(
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
            InfoComponent(info = it, onNameClick = {  })
        }
    }
}
package fps.mobile.app.ui.screen.deviceInfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddemo.ui.theme.FPSDemoTheme
import fps.mobile.app.constants.DEVICE_STATE_DEVICE_INFO
import fps.mobile.app.constants.HARDWARE_DEVICE_INFO
import fps.mobile.app.constants.INSTALLED_APPS_DEVICE_INFO
import fps.mobile.app.constants.OS_DEVICE_INFO
import fps.mobile.app.data.DataLoader
import fps.mobile.app.data.InfoModel
import fps.mobile.app.ui.components.InfoComponent
import fps.mobile.client.DeviceIdentify
import fps.mobile.client.DeviceInfo
import kotlinx.coroutines.launch

@Composable
fun DeviceInfoScreen(
    fingerprint: String ?,
    modifier: Modifier = Modifier,
) {
    if(fingerprint == null)
        return

    var deviceInfoList = DataLoader.getDeviceInfo()

    deviceInfoList = when(fingerprint) {
        "Hardware Fingerprint" -> deviceInfoList.filter { info -> HARDWARE_DEVICE_INFO.any { info.name.contains(it) } } as ArrayList<DeviceInfo>
        "OS Fingerprint" -> deviceInfoList.filter { info -> OS_DEVICE_INFO.any { info.name.contains(it) } } as ArrayList<DeviceInfo>
        "Installed Apps FP" -> deviceInfoList.filter { info -> INSTALLED_APPS_DEVICE_INFO.any { info.name.contains(it) } } as ArrayList<DeviceInfo>
        "Device State FP" -> deviceInfoList.filter { info -> DEVICE_STATE_DEVICE_INFO.any { info.name.contains(it) } } as ArrayList<DeviceInfo>
        else -> deviceInfoList
    }

    val deviceInfoGroup = deviceInfoList.map { it.name.split('|').first() }.distinct()

    val pagerState = rememberPagerState(pageCount = { deviceInfoGroup.size })
    val scope = rememberCoroutineScope()



    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = fingerprint,
                style = MaterialTheme.typography.displaySmall
            )

            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground,
                edgePadding = 0.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                deviceInfoGroup.forEachIndexed { index, tab ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    ) {
                        Row (
                            modifier = Modifier.padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = tab,
                                style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }

            HorizontalPager(
                state = pagerState
            ) {
                Column {
                    val newDeviceInfoList: List<InfoModel> = deviceInfoList.filter { it.name.contains(deviceInfoGroup[pagerState.settledPage]) }.map {
                        InfoModel(it.name.split('|').last(), it.value)
                    }

                    newDeviceInfoList.forEach {
                        InfoComponent(info = it, onNameClick = { })
                    }
                }

            }
        }

    }

}

@Composable
@Preview
fun DeviceInfoScreenPreview(){
    FPSDemoTheme {
        DeviceInfoScreen(fingerprint = "Hardware")
    }
}
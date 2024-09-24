package fps.mobile.app.ui.screen.fingerprint

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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.navArgument
import fps.mobile.app.data.DataLoader
import fps.mobile.app.data.InfoModel
import fps.mobile.app.ui.components.InfoComponent
import kotlinx.coroutines.launch

val fingerprintDetailArgument = listOf(
    navArgument("fingerprint"){
        type = NavType.StringType
        nullable = true
    }
)

@Composable
fun FingerprintDetailScreen(
    fingerprint: String ?,
    modifier: Modifier = Modifier,
) {
    if(fingerprint == null)
        return

    val deviceInfoList = DataLoader.getDeviceInfo(fingerprint)

    val deviceInfoGroup = DataLoader.getDeviceInfoGroups(deviceInfoList)

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
                                pagerState.scrollToPage(index)
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
                state = pagerState,
                userScrollEnabled = false
            ) { page ->
                Column {
                    val newDeviceInfoList: List<InfoModel> = deviceInfoList.filter { it.name.contains(deviceInfoGroup[page]) }.map {
                        InfoModel(it.name.split('|').last(), it.value)
                    }

                    newDeviceInfoList.forEach {
                        InfoComponent(info = it, onNameClick = { } )
                    }
                }

            }
        }
    }
}
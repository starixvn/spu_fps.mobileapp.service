package fps.mobile.app.ui.screen.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddemo.ui.theme.FPSDemoTheme
import fps.mobile.app.ui.screen.deviceId.DeviceIdScreen
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    onButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    val pagerState = rememberPagerState(pageCount = { MainScreenTab.entries.size })
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground
        ) {
            MainScreenTab.entries.forEachIndexed { index, tab ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                ) {

                    Row (
                        modifier = modifier.padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        tab.icon(
                            modifier
                                .padding(end = 8.dp)
                                .size(24.dp)
                        )

                        Text(text = tab.title,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.W500)
                    }
                }

            }
        }

        HorizontalPager(
            state = pagerState
        ) { page ->
            when(page) {
                MainScreenTab.DeviceId.ordinal -> {
                    DeviceIdScreen()
                }
                else -> {
                    Text(text = "Fingerprint")
                }
            }
            
        }
    }
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Button(
            onClick = onButtonClicked,
            modifier = modifier.widthIn(min = 240.dp)) {

            Text(
                text = "Login",
                style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Composable
@Preview
fun MainScreenPreview() {
    FPSDemoTheme {
        MainScreen(onButtonClicked = { })
    }
}
package fps.mobile.app.ui.screen.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

enum class MainScreenTab(
    val title: String,
    val icon: @Composable (modifier: Modifier) -> Unit)
{
    DeviceId(
        title = "Device ID",
        icon = {modifier ->
            Icon(
                modifier = modifier,
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "Device ID icon"
            )
        }
    ),
    Fingerprint(
        title = "Fingerprint",
        icon = {modifier ->
            Icon(
                modifier = modifier,
                painter = painterResource(id = fps.mobile.app.R.drawable.ic_fingerprint_orange),
                contentDescription = "Fingerprint icon"
            )
        }
    )
}
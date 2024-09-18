package fps.mobile.app.ui.screen.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddemo.ui.theme.FPSDemoTheme
import fps.mobile.app.R

@Composable
fun StartScreen(
    onButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            StartScreenImage()
        }

        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(text = "FPS Mobile Device Identifier",
                fontSize = 40.sp,
                lineHeight = 60.sp,
                textAlign = TextAlign.Center)
        }

        Spacer(modifier = modifier.height(20.dp))

        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = onButtonClicked,
                modifier = modifier.widthIn(min = 240.dp)) {
                Text(
                    text = "Get started",
                    fontSize = 20.sp,
                    lineHeight = 32.sp)
            }
        }

    }

}

@Composable
fun StartScreenImage() {
    val image = painterResource(id = R.drawable.fingerprint)
    Image(painter = image, contentDescription = null)
}

@Composable
@Preview
fun StartScreenPreview() {
    FPSDemoTheme {
        StartScreen(onButtonClicked={})
    }
}
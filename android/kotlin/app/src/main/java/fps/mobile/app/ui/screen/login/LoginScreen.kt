package fps.mobile.app.ui.screen.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login name")
        TextField(
            value = "",
            onValueChange = { }
        )
        Spacer(modifier = Modifier)
        Text(text = "Password")
        TextField(
            value = "",
            onValueChange = { }
        )

        Button(
            onClick = {  },
            modifier = Modifier.widthIn(min = 240.dp)) {

            Text(
                text = "Login",
                style = MaterialTheme.typography.labelLarge)
        }
    }
}
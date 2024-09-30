package fps.mobile.app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import fps.mobile.app.data.InfoModel

@Composable
fun InfoComponent(
    info: InfoModel,
    onNameClick:() -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
    ) {
        TextButton(
            onClick = onNameClick
        ) {
            Text(
                text = info.getName(),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        CopyableText(
            value = info.getValue(),
            style = MaterialTheme.typography.bodyMedium
        )

        HorizontalDivider(
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
package fps.mobile.app.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import fps.mobile.app.data.InfoModel

@Composable
fun InfoComponent(
    info: InfoModel,
    onNameClick:() -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

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

        TextButton(
            onClick = {
                val clipboardManager =
                    context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val clipData: ClipData = ClipData.newPlainText("text", info.getValue())

                clipboardManager.setPrimaryClip(clipData)

                Toast.makeText(context, "Copied to Clipboard", Toast.LENGTH_SHORT).show()
            },
            shape = RoundedCornerShape(0)
        ) {
            Text(
                text = info.getValue(),
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        HorizontalDivider(
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
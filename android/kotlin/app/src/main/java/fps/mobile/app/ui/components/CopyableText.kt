package fps.mobile.app.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.widget.Toast
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle

@Composable
fun CopyableText(
    value: String,
    style: TextStyle =  LocalTextStyle.current
) {
    val context = LocalContext.current

    TextButton(
        onClick = {
            val clipboardManager =
                context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clipData: ClipData = ClipData.newPlainText("text", value)

            clipboardManager.setPrimaryClip(clipData)

            Toast.makeText(context, "Copied to Clipboard", Toast.LENGTH_SHORT).show()
        },
        shape = RoundedCornerShape(0)
    ) {
        Text(
            text = value,
            style = style,
        )
    }
}
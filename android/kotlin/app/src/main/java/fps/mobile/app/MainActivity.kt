package fps.mobile.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.androiddemo.ui.theme.FPSDemoTheme
import fps.mobile.client.DeviceIdentify

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DeviceIdentify(context = this)
        enableEdgeToEdge()

        setContent {
            FPSDemoTheme {
                FPSDemoApp()
            }
        }
    }
}
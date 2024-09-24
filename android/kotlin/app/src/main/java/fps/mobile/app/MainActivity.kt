package fps.mobile.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.androiddemo.ui.theme.FPSDemoTheme
import fps.mobile.app.data.DataLoader

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataLoader(this)
        enableEdgeToEdge()

        setContent {
            FPSDemoTheme {
                FPSDemoApp()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        // TODO("Implement fetch tracking data again based on time paused")
    }

    override fun onPause() {
        super.onPause()

        // TODO("Implement time counter on pausing")
    }

    override fun onStop() {
        super.onStop()

        // TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()

        // TODO("Not yet implemented")
    }
}
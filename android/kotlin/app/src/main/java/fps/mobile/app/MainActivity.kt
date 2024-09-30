package fps.mobile.app

import android.bluetooth.BluetoothClass.Device
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.androiddemo.ui.theme.FPSDemoTheme
import fps.mobile.app.data.DataLoader
import fps.mobile.app.ui.viewModel.LoginViewModel
import fps.mobile.client.DeviceIdentify

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            FPSDemoTheme {
                FPSDemoApp()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        Log.d("MainActivity", "onStart Called")

        val model: LoginViewModel by viewModels()

        Log.d("MainActivity", "onStart isAuthenticated: ${model.isAuthenticated()}")
        if(model.isAuthenticated()) {
            DeviceIdentify.onStartOrRestart(this)
            DataLoader(this)
        }
    }

    override fun onStop() {
        super.onStop()

        Log.d("MainActivity", "onStop Called")
        DeviceIdentify.onStop()
    }
}
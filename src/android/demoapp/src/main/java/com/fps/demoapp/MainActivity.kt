package com.fps.demoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.fps.android.sdk.FPS
import com.fps.demoapp.ui.theme.DemoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DemoAppTheme {
                DemoApp()
            }
        }
    }

    override fun onStop() {
        super.onStop()

        FPS.onResetFPSLastLiveTime()
    }
}
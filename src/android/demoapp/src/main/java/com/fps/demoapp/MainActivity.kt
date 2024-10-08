package com.fps.demoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.fps.android.sdk.FPS
import com.fps.demoapp.ui.theme.DemoAppTheme
import com.fps.demoapp.ui.viewModel.LoginViewModel

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

    override fun onStart() {
        super.onStart()

        val model: LoginViewModel by viewModels()
        if(model.isAuthenticated()) {
            val username = model.getUsername()
            val fpsTrans = FPS.onCheckFPSTrans(this, username, this.getString(R.string.subscriber_name), "login")
            // TODO("Send this trans to app service and forward it to FPS API")

            model.updateFpsTrans(fpsTrans)
        }
    }

    override fun onStop() {
        super.onStop()

        FPS.onResetFPSLastLiveTime()
    }
}
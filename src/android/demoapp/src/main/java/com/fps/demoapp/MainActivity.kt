package com.fps.demoapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.fps.android.sdk.FPS
import com.fps.demoapp.services.FpsService
import com.fps.demoapp.ui.theme.DemoAppTheme
import com.fps.demoapp.ui.viewModel.LoginViewModel
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setLanguage()

        setContent {
            DemoAppTheme {
                DemoApp()
            }
        }
    }

    private fun setLanguage() {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val currentLanguage = sharedPreferences.getString("CURRENT_LANGUAGE", null) ?: "en"
        Locale.setDefault(Locale(currentLanguage))
    }

    override fun onStart() {
        super.onStart()

        val model: LoginViewModel by viewModels()
        if(model.isAuthenticated()) {
            val username = model.getUsername()
            val subscriber = model.getSubscriber()

            val fpsService = FpsService(model.getEnv(),"/FPS/MobileCheckTransaction")

            fpsService.checkTransaction(this, subscriber, username, "resume app", true)
        }
    }

    override fun onStop() {
        super.onStop()

        FPS.onResetFPSLastLiveTime()
    }
}
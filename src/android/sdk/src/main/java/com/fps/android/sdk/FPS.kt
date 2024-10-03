package com.fps.android.sdk

import android.content.Context
import com.fps.android.core.FPSModule

class FPS {
    companion object {
        fun login(
            context: Context,
            userName: String,
            subscriberName: String,
            ipV4: String = ""
        ): String? {

            return FPSModule.login(context, userName, subscriberName, ipV4)
        }

        fun onStop() {
            FPSModule.onStop()
        }

        fun onStartOrRestart(
            context: Context,
            userName: String,
            subscriberName: String,
            ipV4: String = ""
        ) {
            FPSModule.onStartOrRestart(context, userName, subscriberName, ipV4)
        }
    }
}
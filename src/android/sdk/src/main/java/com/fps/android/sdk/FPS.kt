package com.fps.android.sdk

import android.content.Context
import com.fps.android.core.FPSModule

class FPS {
    companion object {
        fun login(
            context: Context,
            userName: String,
            subscriberName: String
        ): String? {

            return FPSModule.login(context, userName, subscriberName)
        }

        fun onStop() {
            FPSModule.onStop()
        }

        fun onStartOrRestart(
            context: Context,
            userName: String,
            subscriberName: String
        ) : String? {
            return FPSModule.onStartOrRestart(context, userName, subscriberName)
        }
    }
}
package com.fps.android.sdk

import android.content.Context
import com.fps.android.core.FPSModule

class FPS {
    companion object {

        fun onLogin(
            context: Context,
            subscriberName: String,
            userName: String
        ): String? {
            return FPSModule.onLogin(context, subscriberName, userName)
        }

        fun onCheckFPSTrans(
            context: Context,
            subscriberName: String,
            userName: String
        ): String? {
            return FPSModule.onCheckFPSTrans(context, subscriberName, userName)
        }

        fun onResetFPSLastLiveTime() {
            FPSModule.onResetFPSLastLiveTime()
        }
    }
}
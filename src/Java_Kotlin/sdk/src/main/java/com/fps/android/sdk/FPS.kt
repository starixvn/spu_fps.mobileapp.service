package com.fps.android.sdk

import android.content.Context
import com.fps.android.core.FPSModule

class FPS {
    companion object {

        fun onCheckFPSTrans(
            context: Context,
            subscriberName: String,
            userName: String,
            action: String,
            isOnStart: Boolean = false
        ): String? {
            return FPSModule.onCheckFPSTrans(context, subscriberName, userName, action, isOnStart)
        }

        fun onResetFPSLastLiveTime() {
            FPSModule.onResetFPSLastLiveTime()
        }
    }
}
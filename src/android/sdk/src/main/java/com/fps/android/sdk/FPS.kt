package com.fps.android.sdk

import android.content.Context
import com.fps.android.core.FPSModule

class FPS {
    companion object {
        fun onCheckFPSTrans(
            context: Context,
            userName: String,
            subscriberName: String,
            action: String
        ): String? {

            return FPSModule.onCheckFPSTrans(context, userName, subscriberName, action)
        }

        fun onResetFPSLastLiveTime() {
            FPSModule.onResetFPSLastLiveTime()
        }
    }
}
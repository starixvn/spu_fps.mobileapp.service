package com.fps.android.sdk

import com.fps.android.core.DeviceIdentify

class FpsSdk {
    fun login(): String {
        return DeviceIdentify.log()
    }
}
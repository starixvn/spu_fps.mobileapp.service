package com.fps.android.sdk

import com.fps.android.core.DeviceIdFactory

class FpsSdk {
    fun login(): String {
        return DeviceIdFactory.log()
    }
}
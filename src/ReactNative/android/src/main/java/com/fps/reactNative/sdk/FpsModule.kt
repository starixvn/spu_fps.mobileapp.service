package com.fps.reactNative.sdk

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.annotations.ReactModule

@ReactModule(name = FpsModule.NAME)
class FpsModule(reactContext: ReactApplicationContext) :
  NativeFpsSpec(reactContext) {

  override fun getName(): String {
    return NAME
  }

  // Example method
  // See https://reactnative.dev/docs/native-modules-android
  override fun multiply(a: Double, b: Double): Double {
    return a * b
  }

  override fun onCheckFPSTrans(
    subscriberName: String,
    userName: String,
    action: String,
    isOnStart: Boolean
  ): String? {
    // Implement your logic here
    return null
  }

  override fun onResetFPSLastLiveTime() {
    // Implement your logic here
  }
  
  companion object {
    const val NAME = "Fps"
  }
}

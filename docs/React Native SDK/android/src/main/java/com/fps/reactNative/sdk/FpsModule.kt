package com.fps.reactNative.sdk

import android.content.Context
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.annotations.ReactModule
import com.fps.reactNative.core.FPSModule

@ReactModule(name = FpsModule.NAME)
class FpsModule(reactContext: ReactApplicationContext) :
  NativeFpsSpec(reactContext) {

  private lateinit var applicationContext : Context

  override fun getName(): String {
    return NAME
  }

  override fun onCheckFPSTrans(
    subscriberName: String,
    userName: String,
    action: String,
    isOnStart: Boolean
  ): String? {
    
    if (!this::applicationContext.isInitialized) {
      applicationContext = reactApplicationContext.applicationContext
    }
    return FPSModule.onCheckFPSTrans(applicationContext, subscriberName, userName, action, isOnStart);
  }

  override fun onResetFPSLastLiveTime() {
    FPSModule.onResetFPSLastLiveTime()
  }
  
  companion object {
    const val NAME = "Fps"
  }
}

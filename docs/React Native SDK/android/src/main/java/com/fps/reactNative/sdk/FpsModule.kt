package com.fps.reactNative.sdk

import android.content.Context
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import com.fps.reactNative.core.FPSModule

class FpsModule(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

  private lateinit var applicationContext : Context
  
  override fun getName(): String {
    return NAME
  }

  @ReactMethod
  fun onCheckFPSTrans(
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

  @ReactMethod
  fun onResetFPSLastLiveTime() {
    FPSModule.onResetFPSLastLiveTime()
  }

  companion object {
    const val NAME = "Fps"
  }
}


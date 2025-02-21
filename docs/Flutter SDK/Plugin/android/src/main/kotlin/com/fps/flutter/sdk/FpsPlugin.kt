package com.fps.flutter.sdk

import android.content.Context
import com.fps.flutter.core.FPSModule
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** FpsPlugin */
class FpsPlugin: FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel
  private lateinit var applicationContext : Context

  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "fps")
    channel.setMethodCallHandler(this)
    applicationContext = flutterPluginBinding.applicationContext
  }

  override fun onMethodCall(call: MethodCall, result: Result) {
    if (call.method == "onCheckFPSTrans") {
      val subscriberName = call.argument<String>("subscriberName")!!
      val userName = call.argument<String>("userName")!!
      val action = call.argument<String>("action")!!
      val isOnStart = call.argument<Boolean>("isOnStart")!!
      result.success(FPSModule.onCheckFPSTrans(applicationContext, subscriberName, userName, action, isOnStart))
    }
    else if (call.method == "onResetFPSLastLiveTime") {
      FPSModule.onResetFPSLastLiveTime()
      result.success(null)
    } else {
      result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }
}

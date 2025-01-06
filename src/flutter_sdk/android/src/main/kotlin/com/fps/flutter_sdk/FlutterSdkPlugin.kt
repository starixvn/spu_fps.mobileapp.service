package com.fps.flutter_sdk

import android.content.Context
import androidx.annotation.NonNull
import com.fps.android.core.FPSModule

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** FlutterSdkPlugin */
class FlutterSdkPlugin: FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel

  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_sdk")
    channel.setMethodCallHandler(this)
  }

  override fun onMethodCall(call: MethodCall, result: Result) {
    if (call.method == "getPlatformVersion") {
        result.success("Android ${android.os.Build.VERSION.RELEASE}")
    }
    else if (call.method == "onCheckFPSTrans") {
        val context: Context = call.argument<Context>("context")!!
        val subscriberName = call.argument<String>("subscriberName")!!
        val userName = call.argument<String>("userName")!!
        val action = call.argument<String>("action")!!
        val isOnStart = call.argument<Boolean>("isOnStart")!!
        result.success(FPSModule.onCheckFPSTrans(context, subscriberName, userName, action, isOnStart))
    }
    else if (call.method == "onResetFPSLastLiveTime") {
        result.success(FPSModule.onResetFPSLastLiveTime())
    }
    else {
        result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }
}

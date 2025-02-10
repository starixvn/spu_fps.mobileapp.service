import Flutter
import UIKit

public class FpsPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "flutter_sdk", binaryMessenger: registrar.messenger())
    let instance = FlutterSdkPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    if call.method == "onCheckFPSTrans" {
        guard let args = call.arguments as? [String: Any],
              let subscriberName = args["subscriberName"] as? String,
              let userName = args["userName"] as? String,
              let action = args["action"] as? String,
              let isOnStart = args["isOnStart"] as? Bool else {
            result(FlutterError(code: "INVALID_ARGUMENTS", message: "Invalid arguments", details: nil))
            return
        }

        result(FPSModule.onCheckFPSTrans(subscriberName: subscriberName, userName: userName, action: action, isOnStart: isOnStart))
    } else if call.method == "onResetFPSLastLiveTime" {
        FPSModule.onResetFPSLastLiveTime()
        result(nil)
    } else {
        result(FlutterMethodNotImplemented)
    }
  }
}

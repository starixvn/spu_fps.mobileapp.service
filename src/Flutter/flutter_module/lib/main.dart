import 'package:flutter/services.dart';
import 'package:fps/fps.dart';

void main() {
  const MethodChannel channel = MethodChannel('my_sdk_channel');
  final fpsSDK = Fps();

  channel.setMethodCallHandler((MethodCall call) async {
    if (call.method == 'onResetFPSLastLiveTime') {
      return fpsSDK.onResetFPSLastLiveTime();
    }
    throw MissingPluginException();
  });
}



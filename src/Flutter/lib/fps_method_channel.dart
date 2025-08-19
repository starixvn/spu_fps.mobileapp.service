import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'fps_platform_interface.dart';

/// An implementation of [FpsPlatform] that uses method channels.
class MethodChannelFps extends FpsPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('fps');

  @override
  Future<String?> onCheckFPSTrans(
      String subscriberName,
      String userName,
      String action,
      bool isOnStart) async {
    final fpsTrans = await methodChannel.invokeMethod<String>('onCheckFPSTrans',{
      'subscriberName': subscriberName,
      'userName': userName,
      'action': action,
      'isOnStart': isOnStart
    });
    return fpsTrans;
  }

  @override
  Future onResetFPSLastLiveTime() async {
    await methodChannel.invokeMethod<String>('onResetFPSLastLiveTime');
  }
}

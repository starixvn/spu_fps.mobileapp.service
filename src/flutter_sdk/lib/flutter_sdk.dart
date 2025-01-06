
import 'flutter_sdk_platform_interface.dart';

class FlutterSdk {
  Future<String?> getPlatformVersion() {
    return FlutterSdkPlatform.instance.getPlatformVersion();
  }

  Future<String?> onCheckFPSTrans(
      String subscriberName,
      String userName,
      String action,
      {bool isOnStart = false}) {

    return FlutterSdkPlatform.instance.onCheckFPSTrans();
  }

  Future onResetFPSLastLiveTime() {
    return FlutterSdkPlatform.instance.onResetFPSLastLiveTime();
  }

}
